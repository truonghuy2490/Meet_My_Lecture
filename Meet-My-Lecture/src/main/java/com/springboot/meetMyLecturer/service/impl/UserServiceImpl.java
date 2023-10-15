package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.entity.Major;
import com.springboot.meetMyLecturer.entity.Role;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.MajorProfileDTO;
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


    @Override
    public UserDTO registerUser(int roleId, User userRegister) {
        Role role = roleRepository.findRoleByRoleId(roleId);
        userRegister.setRole(role);

        User user = userRepository.save(userRegister);

        return modelMapper.map(user,UserDTO.class);
    }

    @Override
    public List<UserDTO> getUserByEmptySlotId(int slotId) {
        return null;
    }

    @Override
    public UserProfileDTO viewProfile(long userId) {
        User user = userRepository.findUserByUserId(userId);

        UserProfileDTO userDTO = modelMapper.map(user, UserProfileDTO.class);
        MajorProfileDTO majorProfileDTO = modelMapper.map(user.getMajor(),MajorProfileDTO.class);
        userDTO.setMajor(majorProfileDTO);
        return userDTO;
    }

    @Override
    public UserProfileDTO updateProfile(long userId, int majorId, User userUpdate) {
        User user = userRepository.findUserByUserId(userId);
        Major major = majorRepository.findMajorByMajorId(majorId);

        user.setMajor(userUpdate.getMajor());
        user.setEmail(userUpdate.getEmail());
        user.setUserName(userUpdate.getUserName());
        user.setMajor(major);

        User userUpdated = userRepository.save(user);
        MajorProfileDTO majorProfileDTO = modelMapper.map(userUpdated.getMajor(),MajorProfileDTO.class);

        UserProfileDTO userProfileDTO = modelMapper.map(userUpdated,UserProfileDTO.class);
        userProfileDTO.setMajor(majorProfileDTO);



        return userProfileDTO ;
    }


}


