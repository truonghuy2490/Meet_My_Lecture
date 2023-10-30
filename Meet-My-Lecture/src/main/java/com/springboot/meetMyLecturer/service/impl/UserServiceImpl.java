package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.*;
import com.springboot.meetMyLecturer.constant.Constant;
import com.springboot.meetMyLecturer.entity.*;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.SubjectLecturerStudentDTO;
import com.springboot.meetMyLecturer.modelDTO.UserRegister;
import com.springboot.meetMyLecturer.repository.*;
import com.springboot.meetMyLecturer.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


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

    //get all users DONE-DONE
    @Override
    public List<UserProfileForAdminDTO> getAllUsers(){
        List<User> userList = userRepository.findUserNotAdmin();
        if(userList.isEmpty()){
            throw new RuntimeException("There are no users");
        }
        return  userList.stream().map(user -> modelMapper.map(user, UserProfileForAdminDTO.class)).toList();
    }

    //view profile by userId for admin DONE-DONE
    @Override
    public UserProfileForAdminDTO viewProfileByUserId(Long userId) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", String.valueOf(userId))
        ));
        return modelMapper.map(user, UserProfileForAdminDTO.class);
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
    public List<EmptySlotResponseDTO> viewEmptySlotForAdmin(Long lecturerId) {
        User user = userRepository.findById(lecturerId).orElseThrow(
                ()-> new ResourceNotFoundException("Lecturer","id",String.valueOf(lecturerId))
        );

        List<EmptySlot> emptySlotList = emptySlotRepository.findEmptySlotsByLecturer_UserId(lecturerId);

        return emptySlotList.stream().map(
                emptySlot -> modelMapper.map(emptySlot, EmptySlotResponseDTO.class)).toList();
    }


    //update user status for admin DONE-DONE
    @Override
    public UserProfileForAdminDTO updateUserStatus(Long userId, String status) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("User","id",String.valueOf(userId))
        );

        String statusDB = status.toUpperCase();

        if(statusDB.equals(Constant.CLOSED)){
            user.setStatus(Constant.CLOSED);
        }else if(statusDB.equals(Constant.OPEN)){
            user.setStatus(Constant.OPEN);
        }else if(user.getStatus().equals(Constant.BANNED)){
            user.setAbsentCount(0);
            user.setStatus(Constant.OPEN);
        }


        if (!statusDB.equals(user.getStatus())) {
            userRepository.save(user);
        }

        return modelMapper.map(user, UserProfileForAdminDTO.class);
    }


    //update subject for student DONE-DONE
    @Override
    public LecturerSubjectResponseDTO updateSubjectsForStudent(SubjectLecturerStudentDTO subjectLecturerStudentDTO){
        SubjectLecturerStudent subjectLecturerStudent = getSubjectLecturerStudentOrThrowException(subjectLecturerStudentDTO.getSubjectLecturerStudentId());

        SubjectLecturerStudentId subjectLecturerStudentIdNew = getSubjectLecturerStudentId(subjectLecturerStudentDTO);

        Subject subject = getSubjectOrThrowException(subjectLecturerStudentIdNew.getSubjectId());
        User lecturer = getUserOrThrowException(subjectLecturerStudentIdNew.getLecturerId());
        User student = getUserOrThrowException(subjectLecturerStudentDTO.getSubjectLecturerStudentId().getStudentId());

        LecturerSubjectId lecturerSubjectId = new LecturerSubjectId();
        lecturerSubjectId.setSubjectId(subject.getSubjectId());
        lecturerSubjectId.setLecturerId(lecturer.getUserId());

        LecturerSubject lecturerSubject = lecturerSubjectRepository.findLecturerSubjectByLecturerSubjectId(lecturerSubjectId);
        if(lecturerSubject == null) throw new RuntimeException("This lecturer does not teach this subject.");

        subjectLecturerStudentRepository.delete(subjectLecturerStudent);

        return getLecturerSubjectResponseDTO(subjectLecturerStudentIdNew, subject, lecturer, student, subjectLecturerStudent);
    }

    //insert subject for student DONE-DONE
    @Override
    public LecturerSubjectResponseDTO insertSubjectsForStudent(SubjectLecturerStudentId subjectLecturerStudentId) {
        Subject subject = getSubjectOrThrowException(subjectLecturerStudentId.getSubjectId());
        User lecturer = getUserOrThrowException(subjectLecturerStudentId.getLecturerId());
        User student = getUserOrThrowException(subjectLecturerStudentId.getStudentId());

        if (subjectLecturerStudentRepository.searchSubjectLecturerStudentBySubjectLecturerStudentIdAndStatus(subjectLecturerStudentId, Constant.OPEN) != null) {
            throw new RuntimeException("You already have this subject with this lecturer");
        }

        LecturerSubjectId lecturerSubjectId = new LecturerSubjectId();
        lecturerSubjectId.setSubjectId(subject.getSubjectId());
        lecturerSubjectId.setLecturerId(lecturer.getUserId());

        LecturerSubject lecturerSubject = lecturerSubjectRepository.findLecturerSubjectByLecturerSubjectId(lecturerSubjectId);
        if(lecturerSubject == null) throw new RuntimeException("This lecturer does not teach this subject.");


        SubjectLecturerStudent subjectLecturerStudentDB = subjectLecturerStudentRepository.searchSubjectLecturerStudentBySubjectLecturerStudentIdAndStatus(subjectLecturerStudentId, Constant.CLOSED);

        if(subjectLecturerStudentDB != null){
            subjectLecturerStudentDB.setStatus(Constant.OPEN);
            subjectLecturerStudentRepository.save(subjectLecturerStudentDB);
        }


        SubjectLecturerStudent subjectLecturerStudent = new SubjectLecturerStudent();
        subjectLecturerStudent.setStatus(Constant.OPEN);

        return getLecturerSubjectResponseDTO(subjectLecturerStudentId, subject, lecturer, student, subjectLecturerStudent);
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
        Long userId = userRepository.findUserIdByEmail(email);
        String roleName = userRepository.findRoleOfUser(userId);

        UserRoleResponseDTO user = new UserRoleResponseDTO();

        user.setUserId(userId);
        user.setRoleName(roleName);

        return user;
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


