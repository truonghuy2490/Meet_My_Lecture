package com.springboot.meetMyLecturer.service.impl;

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
    public MajorRepository majorRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    SubjectLecturerStudentRepository subjectLecturerStudentRepository;

    @Autowired
    ModelMapper modelMapper;


    // register DONE
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
//        user.setNickName(result.toString());
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

    //update profile for student DONE
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
    public List<String> getAllUsers(){
        List<String> userList = userRepository.findUserNotAdmin();
        if(userList.isEmpty()){
            throw new RuntimeException("There are no users");
        }
        return  userList;
    }

    //view profile by userId for admin DONE
    @Override
    public UserProfileDTO viewProfileByUserId(Long userId) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", String.valueOf(userId))
        ));
        return modelMapper.map(user, UserProfileDTO.class);
    }

    //delete user for admin DONE
    @Override
    public String deleteUser(Long userId) {
        userRepository.deleteById(userId);
        return "This user has been deleted!";
    }

}


