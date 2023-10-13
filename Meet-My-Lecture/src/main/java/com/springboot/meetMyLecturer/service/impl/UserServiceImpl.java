package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.entity.Role;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import com.springboot.meetMyLecturer.repository.RoleRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public RoleRepository roleRepository;
    @Autowired
    public UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public UserDTO registerUser(int roleId, User userRegister) {
        Role role = roleRepository.findRoleByRoleId(roleId);
        userRegister.setRole(role);
        User user = userRepository.save(userRegister);

        return modelMapper.map(user, UserDTO.class);
    }
}
