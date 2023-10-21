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
    public UserRegisterResponseDTO registerUser(Long roleId, UserRegister userRegister) {
        Role role = roleRepository.findById(roleId).orElseThrow(
                ()-> new ResourceNotFoundException("Role","id",String.valueOf(roleId))
        );

        String[] parts = userRegister.getUserName().split(" ");
        String lastName = parts[parts.length - 1];

        StringBuilder result = new StringBuilder();
        result.append(lastName.toLowerCase());
        for (int i = 0; i < parts.length - 1; i++) {
            result.append(parts[i].substring(0, 1).toUpperCase());
        }

        User user = modelMapper.map(userRegister,User.class);
        user.setUnique(result.toString());
        user.setRole(role);
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
    public UserProfileDTO updateProfileForStudent(Long studentId, UserRegister userRegister) {
        User student = userRepository.findById(studentId).orElseThrow(
                ()-> new ResourceNotFoundException("Student","id",String.valueOf(studentId))
        );

        student.setUserName(userRegister.getUserName());
        userRepository.save(student);
        return modelMapper.map(student,UserProfileDTO.class);
    }

    //get all users DONE
    @Override
    public List<UserProfileDTO> getAllUsers(){
        List<User> userList = userRepository.findUserNotAdmin();
        if(userList.isEmpty()){
            throw new RuntimeException("There are no users");
        }

        return  userList.stream().map(user -> {return modelMapper.map(user, UserProfileDTO.class);
                }).toList();
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

    @Override
    public LecturerSubjectResponseDTO updateSubjectsForStudent(String subjectId, Long lecturerId, Long studentId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                ()-> new ResourceNotFoundException("Subject","id",subjectId)
        );
        User lecturer = userRepository.findById(lecturerId).orElseThrow(
                ()-> new ResourceNotFoundException("Lecturer","id",String.valueOf(lecturerId))
        );
        User student = userRepository.findById(studentId).orElseThrow(
                ()->new ResourceNotFoundException("Student","id",String.valueOf(studentId))
        );

        SubjectLecturerStudent subjectLecturerStudent = new SubjectLecturerStudent();

        subjectLecturerStudent.setSubject(subject);
        subjectLecturerStudent.setLecturer(lecturer);
        subjectLecturerStudent.setStudent(student);

        subjectLecturerStudentRepository.save(subjectLecturerStudent);

        LecturerSubjectResponseDTO lecturerSubjectResponseDTO = new LecturerSubjectResponseDTO();

        lecturerSubjectResponseDTO.setSubjectId(subjectId);
        lecturerSubjectResponseDTO.setLecturerName(lecturer.getUserName());
        lecturerSubjectResponseDTO.setLecturerId(lecturerId);

        return lecturerSubjectResponseDTO;
    }


}


