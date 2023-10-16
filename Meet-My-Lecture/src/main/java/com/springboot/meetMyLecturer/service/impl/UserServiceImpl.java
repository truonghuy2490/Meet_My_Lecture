package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.entity.Major;
import com.springboot.meetMyLecturer.entity.Role;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.MajorProfileDTO;
import com.springboot.meetMyLecturer.modelDTO.RoleDTO;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import com.springboot.meetMyLecturer.modelDTO.UserProfileDTO;
import com.springboot.meetMyLecturer.repository.MajorRepository;
import com.springboot.meetMyLecturer.repository.RoleRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public RoleRepository roleRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public MajorRepository majorRepository;

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


    // view profile
    @Override
    public UserProfileDTO viewProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", String.valueOf(userId))
        );

        UserProfileDTO userDTO = modelMapper.map(user, UserProfileDTO.class);
        MajorProfileDTO majorProfileDTO = modelMapper.map(user.getMajor(),MajorProfileDTO.class);
        RoleDTO roleDTO = modelMapper.map(user.getRole(), RoleDTO.class);
        userDTO.setRole(roleDTO);
        userDTO.setMajor(majorProfileDTO);
        return userDTO;
    }

    //update profile
    @Override
    public UserProfileDTO updateProfile(Long userId, Long majorId, User userUpdate) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", String.valueOf(userId))
        );
        Major major = majorRepository.findById(majorId).orElseThrow(
                () -> new ResourceNotFoundException("Major", "id", String.valueOf(majorId))
        );

        user.setMajor(userUpdate.getMajor());
        user.setEmail(userUpdate.getEmail());
        user.setUserName(userUpdate.getUserName());
        user.setMajor(major);

        User userUpdated = userRepository.save(user);
        MajorProfileDTO majorProfileDTO = modelMapper.map(userUpdated.getMajor(),MajorProfileDTO.class);

        UserProfileDTO userProfileDTO = modelMapper.map(userUpdated,UserProfileDTO.class);
        RoleDTO roleDTO = modelMapper.map(userUpdated.getRole(),RoleDTO.class);
        userProfileDTO.setRole(roleDTO);
        userProfileDTO.setMajor(majorProfileDTO);
        return userProfileDTO ;
    }

    //get all users
    @Override
    public List<String> getAllUsers() {
        List<String> userList = userRepository.findUserNotAdmin();
        if(userList.isEmpty()){
            throw new RuntimeException("There are no users");
        }
        return  userList;
    }

    //view profile by email for admin
    @Override
    public UserProfileDTO viewProfileByEmail(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findUserByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User", "email", email)
        ));

        UserProfileDTO userProfileDTO = modelMapper.map(user, UserProfileDTO.class);
        MajorProfileDTO majorProfileDTO = modelMapper.map(user.get().getMajor(),MajorProfileDTO.class);
        RoleDTO roleDTO = modelMapper.map(user.get().getRole(), RoleDTO.class);
        userProfileDTO.setRole(roleDTO);
        userProfileDTO.setMajor(majorProfileDTO);

        return userProfileDTO;
    }

    //delete user for admin
    @Override
    public String deleteUser(Long userId) {
        userRepository.deleteById(userId);
        return "This user has been deleted!";
    }

}


