package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.EmptySlotResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectResponseDTO;
import com.springboot.meetMyLecturer.entity.*;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.ResponseDTO.UserRegisterResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.UserProfileDTO;
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
    public UserRepository userRepository;

    @Autowired
    EmptySlotRepository emptySlotRepository;

    @Autowired
    MajorRepository majorRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    SubjectLecturerStudentRepository subjectLecturerStudentRepository;

    @Autowired
    ModelMapper modelMapper;


    // register user DONE
    @Override
    public UserRegisterResponseDTO registerUser(String userName) {

        String[] parts = userName.split(" ");
        String lastName = parts[parts.length - 1];

        StringBuilder result = new StringBuilder();
        result.append(lastName.toLowerCase());
        for (int i = 0; i < parts.length - 1; i++) {
            result.append(parts[i].substring(0, 1).toUpperCase());
        }

        User user = new User();
        user.setUserName(userName);
        user.setUnique(result.toString());
        userRepository.save(user);

        return modelMapper.map(user, UserRegisterResponseDTO.class);
    }

    // view profile user DONE
    @Override
    public UserProfileDTO viewProfileUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", String.valueOf(userId))
        );
        return modelMapper.map(user, UserProfileDTO.class);
    }

    //update profile for user DONE
    @Override
    public UserProfileDTO updateProfile(Long userId, UserRegister userRegister) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("Student","id",String.valueOf(userId))
        );

        user.setUserName(userRegister.getUserName());
        userRepository.save(user);
        return modelMapper.map(user,UserProfileDTO.class);
    }

    //get all users DONE
    @Override
    public List<UserProfileDTO> getAllUsers(){
        List<User> userList = userRepository.findUserNotAdmin();
        if(userList.isEmpty()){
            throw new RuntimeException("There are no users");
        }
        return  userList.stream().map(user -> modelMapper.map(user, UserProfileDTO.class)).toList();
    }

    //view profile by userId for admin DONE
    @Override
    public UserProfileDTO viewProfileByUserId(Long userId) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", String.valueOf(userId))
        ));
        return modelMapper.map(user, UserProfileDTO.class);
    }

    //view empty slot by lecturerId for student DONE
    @Override
    public List<EmptySlotResponseDTO> viewEmptySlot(Long lecturerId) {
        List<EmptySlot> emptySlotList = emptySlotRepository.findEmptySlotByLecturer_UserId(lecturerId);

        return emptySlotList.stream().map(
                emptySlot -> modelMapper.map(emptySlot, EmptySlotResponseDTO.class)).toList();
    }


    //delete user for admin DONE
    @Override
    public String deleteUser(Long userId) {
        userRepository.deleteById(userId);
        return "This user has been deleted!";
    }

    //update subject for student
    @Override
    public LecturerSubjectResponseDTO updateSubjectsForStudent(SubjectLecturerStudentId subjectLecturerStudentId) {
        Subject subject = subjectRepository.findById(subjectLecturerStudentId.getSubjectId()).orElseThrow(
                ()-> new ResourceNotFoundException("Subject","id",subjectLecturerStudentId.getSubjectId())
        );
        User lecturer = userRepository.findById(subjectLecturerStudentId.getLecturerId()).orElseThrow(
                ()-> new ResourceNotFoundException("Lecturer","id",String.valueOf(subjectLecturerStudentId.getLecturerId()))
        );
        User student = userRepository.findById(subjectLecturerStudentId.getStudentId()).orElseThrow(
                ()->new ResourceNotFoundException("Student","id",String.valueOf(subjectLecturerStudentId.getStudentId()))
        );

        SubjectLecturerStudent subjectLecturerStudent =  subjectLecturerStudentRepository
                .searchSubjectLecturerStudentBySubjectLecturerStudentId(subjectLecturerStudentId);

        if(subjectLecturerStudent == null){
            throw new RuntimeException("You do not have this subject with this lecturer");
        }

        return getLecturerSubjectResponseDTO(subjectLecturerStudentId, subject, lecturer, student, subjectLecturerStudent);
    }

    //insert subject for student
    @Override
    public LecturerSubjectResponseDTO insertSubjectsForStudent(SubjectLecturerStudentId subjectLecturerStudentId) {
        Subject subject = subjectRepository.findById(subjectLecturerStudentId.getSubjectId()).orElseThrow(
                ()-> new ResourceNotFoundException("Subject","id",subjectLecturerStudentId.getSubjectId())
        );
        User lecturer = userRepository.findById(subjectLecturerStudentId.getLecturerId()).orElseThrow(
                ()-> new ResourceNotFoundException("Lecturer","id",String.valueOf(subjectLecturerStudentId.getLecturerId()))
        );
        User student = userRepository.findById(subjectLecturerStudentId.getStudentId()).orElseThrow(
                ()->new ResourceNotFoundException("Student","id",String.valueOf(subjectLecturerStudentId.getStudentId()))
        );

        SubjectLecturerStudent subjectLecturerStudentDB =  subjectLecturerStudentRepository
                .searchSubjectLecturerStudentBySubjectLecturerStudentId(subjectLecturerStudentId);

        if(subjectLecturerStudentDB != null){
            throw new RuntimeException("You already have this subject with this lecturer");
        }
        SubjectLecturerStudent subjectLecturerStudent = new SubjectLecturerStudent();

        return getLecturerSubjectResponseDTO(subjectLecturerStudentId, subject, lecturer, student, subjectLecturerStudent);
    }

    // delete subject for student
    @Override
    public String deleteSubjectsForStudent(SubjectLecturerStudentId subjectLecturerStudentId) {
        Subject subject = subjectRepository.findById(subjectLecturerStudentId.getSubjectId()).orElseThrow(
                ()-> new ResourceNotFoundException("Subject","id",subjectLecturerStudentId.getSubjectId())
        );
        User lecturer = userRepository.findById(subjectLecturerStudentId.getLecturerId()).orElseThrow(
                ()-> new ResourceNotFoundException("Lecturer","id",String.valueOf(subjectLecturerStudentId.getLecturerId()))
        );
        User student = userRepository.findById(subjectLecturerStudentId.getStudentId()).orElseThrow(
                ()->new ResourceNotFoundException("Student","id",String.valueOf(subjectLecturerStudentId.getStudentId()))
        );

        SubjectLecturerStudent subjectLecturerStudentDB =  subjectLecturerStudentRepository
                .searchSubjectLecturerStudentBySubjectLecturerStudentId(subjectLecturerStudentId);

        if(subjectLecturerStudentDB == null){
            throw new RuntimeException("You do not have this subject with this lecturer");
        }

        subjectLecturerStudentRepository.delete(subjectLecturerStudentDB);


        return "This subject with this lecturer has been deleted!";
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


