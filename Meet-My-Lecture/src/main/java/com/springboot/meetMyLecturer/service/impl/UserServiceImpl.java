package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.*;
import com.springboot.meetMyLecturer.constant.Constant;
import com.springboot.meetMyLecturer.entity.*;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.UserRegister;
import com.springboot.meetMyLecturer.repository.*;
import com.springboot.meetMyLecturer.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipal;
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
                emptySlot -> modelMapper.map(emptySlot, EmptySlotResponseDTO.class)).toList();
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


    //update subject for student DONE - DONE
    @Override
    public LecturerSubjectResponseDTO updateSubjectsForStudent(SubjectLecturerStudentId subjectLecturerStudentId) {
        Subject subject = subjectRepository.findSubjectBySubjectIdAndStatus(subjectLecturerStudentId.getSubjectId(), Constant.OPEN);
        if(subject == null) throw new RuntimeException("This subject is not existed.");

        User lecturer = userRepository.findUserByUserIdAndStatus(subjectLecturerStudentId.getLecturerId(), Constant.OPEN);
        if(lecturer == null) throw new RuntimeException("This lecturer is not existed.");

        User student = userRepository.findUserByUserIdAndStatus(subjectLecturerStudentId.getStudentId(), Constant.OPEN);
        if(student == null) throw new RuntimeException("This student is not existed.");

        SubjectLecturerStudent subjectLecturerStudent =  subjectLecturerStudentRepository
                .searchSubjectLecturerStudentBySubjectLecturerStudentId(subjectLecturerStudentId);

        if(subjectLecturerStudent == null){
            throw new RuntimeException("You do not have this subject with this lecturer");
        }

        return getLecturerSubjectResponseDTO(subjectLecturerStudentId, subject, lecturer, student, subjectLecturerStudent);
    }

    //insert subject for student DONE-DONE
    @Override
    public LecturerSubjectResponseDTO insertSubjectsForStudent(SubjectLecturerStudentId subjectLecturerStudentId) {
        Subject subject = subjectRepository.findSubjectBySubjectIdAndStatus(subjectLecturerStudentId.getSubjectId(), Constant.OPEN);
        if(subject == null) throw new RuntimeException("This subject is not existed.");

        User lecturer = userRepository.findUserByUserIdAndStatus(subjectLecturerStudentId.getLecturerId(), Constant.OPEN);
        if(lecturer == null) throw new RuntimeException("This lecturer is not existed.");

        User student = userRepository.findUserByUserIdAndStatus(subjectLecturerStudentId.getStudentId(), Constant.OPEN);
        if(student == null) throw new RuntimeException("This student is not existed.");

        SubjectLecturerStudent subjectLecturerStudentDB =  subjectLecturerStudentRepository
                .searchSubjectLecturerStudentBySubjectLecturerStudentId(subjectLecturerStudentId);

        if(subjectLecturerStudentDB != null){
            throw new RuntimeException("You already have this subject with this lecturer");
        }
        SubjectLecturerStudent subjectLecturerStudent = new SubjectLecturerStudent();

        return getLecturerSubjectResponseDTO(subjectLecturerStudentId, subject, lecturer, student, subjectLecturerStudent);
    }

    // delete subject for student DONE-DONE
    @Override
    public String deleteSubjectsForStudent(SubjectLecturerStudentId subjectLecturerStudentId) {
        Subject subject = subjectRepository.findSubjectBySubjectIdAndStatus(subjectLecturerStudentId.getSubjectId(), Constant.OPEN);
        if(subject == null) throw new RuntimeException("This subject is not existed.");

        User lecturer = userRepository.findUserByUserIdAndStatus(subjectLecturerStudentId.getLecturerId(), Constant.OPEN);
        if(lecturer == null) throw new RuntimeException("This lecturer is not existed.");

        User student = userRepository.findUserByUserIdAndStatus(subjectLecturerStudentId.getStudentId(), Constant.OPEN);
        if(student == null) throw new RuntimeException("This student is not existed.");


        SubjectLecturerStudent subjectLecturerStudentDB =  subjectLecturerStudentRepository
                .searchSubjectLecturerStudentBySubjectLecturerStudentId(subjectLecturerStudentId);

        if(subjectLecturerStudentDB == null){
            throw new RuntimeException("You do not have this subject with this lecturer");
        }

        subjectLecturerStudentDB.setStatus(Constant.CLOSED);
        subjectLecturerStudentRepository.save(subjectLecturerStudentDB);

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

    @Override
    public Long getUserId(String email) {
        return userRepository.findByEmail(email);
    }


    private LecturerSubjectResponseDTO getLecturerSubjectResponseDTO(SubjectLecturerStudentId subjectLecturerStudentId, Subject subject, User lecturer, User student, SubjectLecturerStudent subjectLecturerStudent) {
        subjectLecturerStudent.setSubject(subject);
        subjectLecturerStudent.setLecturer(lecturer);
        subjectLecturerStudent.setStudent(student);
        subjectLecturerStudent.setSubjectLecturerStudentId(subjectLecturerStudentId);
        subjectLecturerStudentRepository.save(subjectLecturerStudent);

        LecturerSubjectResponseDTO lecturerSubjectResponseDTO = modelMapper
                .map(subjectLecturerStudent, LecturerSubjectResponseDTO.class);

        lecturerSubjectResponseDTO.setUnique(lecturer.getUnique());
        return lecturerSubjectResponseDTO;
    }


}


