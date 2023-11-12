package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.*;
import com.springboot.meetMyLecturer.constant.Constant;
import com.springboot.meetMyLecturer.entity.*;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.RoomResponse;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.SlotResponse;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.UserResponse;
import com.springboot.meetMyLecturer.modelDTO.RoomDTO;
import com.springboot.meetMyLecturer.modelDTO.SubjectLecturerStudentDTO;
import com.springboot.meetMyLecturer.modelDTO.UserRegister;
import com.springboot.meetMyLecturer.repository.*;
import com.springboot.meetMyLecturer.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmptySlotRepository emptySlotRepository;

    @Autowired
    LecturerSubjectRepository lecturerSubjectRepository;

    @Autowired
    MajorRepository majorRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    SemesterRepository semesterRepository;

    @Autowired
    SubjectLecturerStudentRepository subjectLecturerStudentRepository;

    @Autowired
    ModelMapper modelMapper;


    // view profile user DONE - DONE
    @Override
    public UserProfileDTO viewProfileUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", String.valueOf(userId))
        );
        return modelMapper.map(user, UserProfileDTO.class);
    }

    //update profile for user DONE - DONE
    @Override
    public UserProfileDTO updateProfile(Long userId, UserRegister userRegister) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("Student","id",String.valueOf(userId))
        );

        user.setUserName(userRegister.getUserName());
        userRepository.save(user);
        return modelMapper.map(user,UserProfileDTO.class);
    }

    //view profile by userId for admin DONE-DONE
    @Override
    public UserProfileForAdminDTO viewProfileByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", String.valueOf(userId))
        );
        UserProfileForAdminDTO userProfileForAdminDTO = modelMapper.map(user, UserProfileForAdminDTO.class);
        userProfileForAdminDTO.setMajorName(user.getMajor().getMajorName());
        return userProfileForAdminDTO;
    }

    //view empty slot by lecturerId for student and lecturer DONE-DONE
    @Override
    public List<EmptySlotResponseDTO> viewEmptySlot(Long lecturerId) {
       User user = userRepository.findUserByUserIdAndStatus(lecturerId, Constant.OPEN);
       if (user == null) throw new RuntimeException("This lecturer is not existed.");

       List<EmptySlot> emptySlotList = emptySlotRepository.findEmptySlotsByLecturer_UserId(lecturerId);

        return emptySlotList.stream().map(
                emptySlot -> {
                    EmptySlotResponseDTO emptySlotResponseDTO = modelMapper.map(emptySlot, EmptySlotResponseDTO.class);
                    if(emptySlot.getCode() != null){
                        emptySlotResponseDTO.setMode("PRIVATE");
                    }else{
                        emptySlotResponseDTO.setMode("PUBLIC");
                    }
                    return emptySlotResponseDTO;
                }).toList();
    }

    //view empty slots for admin DONE-DONE
    @Override
    public SlotResponse viewEmptySlotForAdmin(int pageNo, int pageSize, String sortBy, String sortDir,
                                              Long lecturerId,
                                              String status
    )
    {
        User user = userRepository.findById(lecturerId).orElseThrow(
                ()-> new ResourceNotFoundException("Lecturer","id",String.valueOf(lecturerId))
        );

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        // CREATE PAGEABLE INSTANCE
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<EmptySlot> slots = null;
        // SAVE TO REPO
        if(status.equalsIgnoreCase(Constant.OPEN) || status.equalsIgnoreCase(Constant.BOOKED)){
            // get by user id n status
            slots = emptySlotRepository.findEmptySlotByLecturer_UserIdAndStatus(lecturerId, status, pageable);

        } else if (status.isEmpty()) {
            // get by user id
            slots = emptySlotRepository.findEmptySlotByLecturer_UserId(pageable, lecturerId);
        }else{
            throw new RuntimeException("Status not valid!");
        }

        if(slots.isEmpty()){
            throw new RuntimeException("There are no slot.");
        }

        // get content for page object
        List<EmptySlot> slotList = slots.getContent();

        List<EmptySlotResponseDTO> content = slotList.stream().map(
                slot -> modelMapper.map(slot, EmptySlotResponseDTO.class)
        ).collect(Collectors.toList());

        SlotResponse slotResponse = new SlotResponse();
        slotResponse.setContent(content);
        slotResponse.setTotalPage(slots.getTotalPages());
        slotResponse.setTotalElement(slots.getTotalElements());
        slotResponse.setPageNo(slots.getNumber());
        slotResponse.setPageSize(slots.getSize());
        slotResponse.setLast(slots.isLast());

        return slotResponse;
    }


    //update user status for admin DONE-DONE
    @Override
    public UserProfileForAdminDTO updateUserStatus(Long userId, String status) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("User","id",String.valueOf(userId))
        );

        if(status.equalsIgnoreCase(Constant.OPEN) ||
        status.equalsIgnoreCase(Constant.CLOSED) ||
        status.equalsIgnoreCase(Constant.BANNED)){
            user.setStatus(status.toUpperCase());
        }else{
            throw new RuntimeException("Status is not valid!");
        }
        userRepository.save(user);
        return modelMapper.map(user, UserProfileForAdminDTO.class);
    }


    //update subject for student DONE-DONE
    @Override
    public List<LecturerSubjectResponseDTO> updateSubjectsForStudent(Set<SubjectLecturerStudentDTO> subjectLecturerStudentDTO){
        List<LecturerSubjectResponseDTO> list = subjectLecturerStudentDTO.stream().map(
                sls -> {
                    SubjectLecturerStudent subjectLecturerStudent = getSubjectLecturerStudentOrThrowException(sls.getSubjectLecturerStudentId());

                    SubjectLecturerStudentId subjectLecturerStudentIdNew = getSubjectLecturerStudentId(sls);

                    Subject subject = getSubjectOrThrowException(subjectLecturerStudentIdNew.getSubjectId());
                    User lecturer = getUserOrThrowException(subjectLecturerStudentIdNew.getLecturerId());
                    User student = getUserOrThrowException(sls.getSubjectLecturerStudentId().getStudentId());

                    LecturerSubjectId lecturerSubjectId = new LecturerSubjectId();
                    lecturerSubjectId.setSubjectId(subject.getSubjectId());
                    lecturerSubjectId.setLecturerId(lecturer.getUserId());

                    LecturerSubject lecturerSubject = lecturerSubjectRepository.findLecturerSubjectByLecturerSubjectId(lecturerSubjectId);
                    if(lecturerSubject == null) throw new RuntimeException("This lecturer does not teach this subject.");

                    subjectLecturerStudentRepository.delete(subjectLecturerStudent);
                    return getLecturerSubjectResponseDTO(subjectLecturerStudentIdNew, subject, lecturer, student, subjectLecturerStudent);

                }
        ).collect(Collectors.toList());

        return list;
    }

    //insert subject for student DONE-DONE
    @Override
    public  List <LecturerSubjectResponseDTO> insertSubjectsForStudent(Set<SubjectLecturerStudentId> subjectLecturerStudentId) {
        List <LecturerSubjectResponseDTO> list = subjectLecturerStudentId.stream().map(
                sls -> {
                    Subject subject = getSubjectOrThrowException(sls.getSubjectId());
                    User lecturer = getUserOrThrowException(sls.getLecturerId());
                    User student = getUserOrThrowException(sls.getStudentId());

                    if (subjectLecturerStudentRepository
                            .searchSubjectLecturerStudentBySubjectLecturerStudentIdAndStatus(sls, Constant.OPEN) != null) {
                        throw new RuntimeException("You already have this subject with this lecturer");
                    }

                    LecturerSubjectId lecturerSubjectId = new LecturerSubjectId();
                    lecturerSubjectId.setSubjectId(subject.getSubjectId());
                    lecturerSubjectId.setLecturerId(lecturer.getUserId());

                    LecturerSubject lecturerSubject = lecturerSubjectRepository.findLecturerSubjectByLecturerSubjectId(lecturerSubjectId);
                    if(lecturerSubject == null) throw new RuntimeException("This lecturer does not teach this subject.");

                    SubjectLecturerStudent subjectLecturerStudentDB = subjectLecturerStudentRepository
                            .searchSubjectLecturerStudentBySubjectLecturerStudentIdAndStatus(sls, Constant.CLOSED);

                    if(subjectLecturerStudentDB != null){
                        subjectLecturerStudentDB.setStatus(Constant.OPEN);
                        subjectLecturerStudentRepository.save(subjectLecturerStudentDB);
                    }


                    SubjectLecturerStudent subjectLecturerStudent = new SubjectLecturerStudent();
                    subjectLecturerStudent.setStatus(Constant.OPEN);

                    return getLecturerSubjectResponseDTO(sls, subject, lecturer, student, subjectLecturerStudent);
                }
        ).collect(Collectors.toList());

        return list;
    }

    //delete subject for student DONE-DONE
    @Override
    public String deleteSubjectsForStudent(SubjectLecturerStudentId subjectLecturerStudentId) {

        SubjectLecturerStudent subjectLecturerStudent = getSubjectLecturerStudentOrThrowException(subjectLecturerStudentId);

        subjectLecturerStudent.setStatus(Constant.CLOSED);
        subjectLecturerStudentRepository.save(subjectLecturerStudent);

        return "This subject with this lecturer has been deleted!";
    }

    // get all majors for user DONE-DONE
    @Override
    public List<MajorResponseDTO> getAllMajors() {
        List<Major> majorList = majorRepository.findMajorsByStatus(Constant.OPEN);
        if(majorList.isEmpty()) throw new RuntimeException("There are no majors");

        return majorList.stream().map
                (major -> modelMapper.map(major, MajorResponseDTO.class)).toList();
    }

    //get empty slot by semester for user DONE-DONE
    @Override
    public List<EmptySlotResponseForSemesterDTO> getEmptySlotsInSemester(Long userId, Long semesterId) {

        User user = userRepository.findUserByUserIdAndStatus(userId, Constant.OPEN);
        if(user == null) throw new RuntimeException("This user is not existed.");

        Semester semester = semesterRepository.findSemesterBySemesterIdAndStatus(semesterId, Constant.OPEN);
        if (semester == null) throw new RuntimeException("This semester is not existed.");

        List<EmptySlot> emptySlotList = emptySlotRepository.findEmptySlotsBySemester(semesterId, userId);

        return emptySlotList.stream().map(
                emptySlot -> {
                    EmptySlotResponseForSemesterDTO emptySlotResponseForSemesterDTO = modelMapper.map(emptySlot, EmptySlotResponseForSemesterDTO.class);
                    emptySlotResponseForSemesterDTO.setSemesterId(semester.getSemesterId());
                    return emptySlotResponseForSemesterDTO;
                }
        ).toList();
    }

    //get userId DONE-DONE
    @Override
    public UserRoleResponseDTO getUserId(String email) {
        User user = userRepository.findUserByEmail(email);
        return modelMapper.map(user, UserRoleResponseDTO.class);
    }

    @Override
    public UserResponse getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir, String status) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        // CREATE PAGEABLE INSTANCE
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        List<User> listUserClosed = userRepository.findUserByStatus(Constant.CLOSED);
        List<User> listUserOpen = userRepository.findUserByStatus(Constant.OPEN);

        Page<User> listUser;
        if(status.equalsIgnoreCase(Constant.OPEN) ||
                status.equalsIgnoreCase(Constant.CLOSED) ||
                status.equalsIgnoreCase(Constant.BANNED)){
            listUser = userRepository.findUserByStatus(status, pageable);
        }else if(status.isEmpty()){
            listUser = userRepository.findAll(pageable);
        }else{
            throw new RuntimeException("Status is not valid!");
        }

        // get content for page object
        List<User> listOfUsers = listUser.getContent();

        List<UserProfileForAdminDTO> content = listOfUsers.stream().map(
                user -> modelMapper.map(user, UserProfileForAdminDTO.class)
        ).collect(Collectors.toList());

        UserResponse userResponse = new UserResponse();
        userResponse.setContent(content);
        userResponse.setTotalPage(listUser.getTotalPages());
        userResponse.setTotalElement(listUser.getTotalElements());
        userResponse.setTotalCLOSE(listUserClosed.size());
        userResponse.setTotalOPEN(listUserOpen.size());
        userResponse.setPageNo(listUser.getNumber());
        userResponse.setPageSize(listUser.getSize());
        userResponse.setLast(listUser.isLast());

        return userResponse;
    }

    //search user by name, unique, email for admin
    @Override
    public List<UserProfileForAdminDTO> searchUser(String keyword) {
        List<User> list;

        if(keyword != null){
            list = userRepository.findUsersByNameOrUniqueOrEmail(keyword);
        }else{
            return null;
        }

        return list.stream().map(
                u -> {
                    UserProfileForAdminDTO userProfileForAdminDTO = modelMapper.map(u, UserProfileForAdminDTO.class);
                    userProfileForAdminDTO.setMajorName(u.getMajor().getMajorName());
                    return userProfileForAdminDTO;
                }
        ).collect(Collectors.toList());
    }


    private LecturerSubjectResponseDTO getLecturerSubjectResponseDTO(SubjectLecturerStudentId subjectLecturerStudentId, Subject subject, User lecturer, User student, SubjectLecturerStudent subjectLecturerStudent) {
        subjectLecturerStudent.setSubject(subject);
        subjectLecturerStudent.setLecturer(lecturer);
        subjectLecturerStudent.setStudent(student);
        subjectLecturerStudent.setSubjectLecturerStudentId(subjectLecturerStudentId);
        subjectLecturerStudent.setStatus(Constant.OPEN);
        subjectLecturerStudentRepository.save(subjectLecturerStudent);

        LecturerSubjectResponseDTO lecturerSubjectResponseDTO = modelMapper
                .map(subjectLecturerStudent, LecturerSubjectResponseDTO.class);

        lecturerSubjectResponseDTO.setUnique(lecturer.getUnique());
        return lecturerSubjectResponseDTO;
    }

    private User getUserOrThrowException(Long userId) {
        User user = userRepository.findUserByUserIdAndStatus(userId, Constant.OPEN);
        if (user == null) {
            throw new ResourceNotFoundException("User","id", String.valueOf(userId));
        }
        return user;
    }

    private SubjectLecturerStudent getSubjectLecturerStudentOrThrowException(SubjectLecturerStudentId subjectLecturerStudentId) {
        SubjectLecturerStudent subjectLecturerStudentDB = subjectLecturerStudentRepository.searchSubjectLecturerStudentBySubjectLecturerStudentIdAndStatus(subjectLecturerStudentId, Constant.OPEN);
        if (subjectLecturerStudentDB == null) {
            throw new RuntimeException("You do not have this subject with this lecturer");
        }
        return subjectLecturerStudentDB;
    }

    private Subject getSubjectOrThrowException(String subjectId) {
        Subject subject = subjectRepository.findSubjectBySubjectIdAndStatus(subjectId, Constant.OPEN);
        if (subject == null) {
            throw new RuntimeException("This subject is not existed.");
        }
        return subject;
    }

    private static SubjectLecturerStudentId getSubjectLecturerStudentId(SubjectLecturerStudentDTO subjectLecturerStudentDTO) {
        SubjectLecturerStudentId subjectLecturerStudentIdNew = new SubjectLecturerStudentId();
        subjectLecturerStudentIdNew.setStudentId(subjectLecturerStudentDTO.getSubjectLecturerStudentId().getStudentId());
        subjectLecturerStudentIdNew.setSubjectId(subjectLecturerStudentDTO.getSubjectIdNew());
        subjectLecturerStudentIdNew.setLecturerId(subjectLecturerStudentDTO.getLecturerIdNew());

        if(subjectLecturerStudentDTO.getSubjectLecturerStudentId().equals(subjectLecturerStudentIdNew)){
            throw new RuntimeException("Duplicate subject and lecturer.");
        }
        return subjectLecturerStudentIdNew;
    }



}


