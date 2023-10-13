package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.entity.Role;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.map.MapDTO;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import com.springboot.meetMyLecturer.repository.RoleRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceIml implements UserService {

    @Autowired
    public RoleRepository roleRepository;
    @Autowired
    public UserRepository userRepository;

    @Autowired
    public MapDTO mapDTO;

    @Override
    public UserDTO registerUser(int roleId, User userRegister) {
        Role role = roleRepository.findRoleByRoleId(roleId);
        userRegister.setRole(role);

        User user = userRepository.save(userRegister);

        return mapDTO.mapToDTO(user);
    }
}
