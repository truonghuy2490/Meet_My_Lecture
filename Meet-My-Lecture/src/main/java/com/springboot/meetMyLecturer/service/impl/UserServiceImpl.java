package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.entity.*;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import com.springboot.meetMyLecturer.ResponseDTO.UserProfileDTO;
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


    // login first time
    @Override
    public UserDTO registerUser(Long roleId, User userRegister) {
        Role role = roleRepository.findById(roleId).orElseThrow();
        userRegister.setRole(role);

        User user = userRepository.save(userRegister);

        return modelMapper.map(user,UserDTO.class);
    }

    @Override
    public List<UserDTO> getUserByEmptySlotId(Long slotId) {
        return null;
    }


    // view profile user DONE
    @Override
    public UserProfileDTO viewProfileUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", String.valueOf(userId))
        );
        UserProfileDTO userDTO = modelMapper.map(user, UserProfileDTO.class);

        return userDTO;
    }

    //update profile for student
    @Override
    public UserProfileDTO updateProfileForStudent(Long studentId, Long majorId, UserDTO userDTO, String subjectId, Long lecturerId) {
        User student = userRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student", "id", String.valueOf(studentId))
        );
        Major major = majorRepository.findById(majorId).orElseThrow(
                () -> new ResourceNotFoundException("Major", "id", String.valueOf(majorId))
        );

        User lecturer = userRepository.findById(lecturerId).orElseThrow(
                () -> new ResourceNotFoundException("Lecturer", "id", String.valueOf(lecturerId))
        );

        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new ResourceNotFoundException("Subject","id",subjectId)
        );

        SubjectLecturerStudent subjectLecturerStudent = new SubjectLecturerStudent();
        subjectLecturerStudent.setStudentId(studentId);
        subjectLecturerStudent.setLecturerId(lecturerId);
        subjectLecturerStudent.setSubjectId(subjectId);

        subjectLecturerStudentRepository.save(subjectLecturerStudent);



        student.setMajor(major);
        student.setEmail(userDTO.getEmail());
        student.setUserName(userDTO.getUserName());

        User userUpdate = userRepository.save(student);

        UserProfileDTO userProfileDTO = modelMapper.map(userUpdate,UserProfileDTO.class);

        return userProfileDTO ;
    }

    //get all users
    @Override
    public List<String> getAllUsers(){
        List<String> userList = userRepository.findUserNotAdmin();
        if(userList.isEmpty()){
            throw new RuntimeException("There are no users");
        }
        return  userList;
    }

    //view profile by userId for admin
    @Override
    public UserProfileDTO viewProfileByUserId(Long userId) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", String.valueOf(userId))
        ));

        UserProfileDTO userProfileDTO = modelMapper.map(user, UserProfileDTO.class);

        return userProfileDTO;
    }

    //delete user for admin
    @Override
    public String deleteUser(Long userId) {
        userRepository.deleteById(userId);
        return "This user has been deleted!";
    }

}


