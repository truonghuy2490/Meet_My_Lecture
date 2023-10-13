package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.entity.Role;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.map.MapDTO;
import com.springboot.meetMyLecturer.modelDTO.EmptySlotDTO;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import com.springboot.meetMyLecturer.repository.RoleRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceIml implements UserService {

    @Autowired
    ModelMapper modelMapper;

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

    @Override
    public List<UserDTO> searchLecturers(String name) {
        List<User> lecturerList = userRepository.findUserByUserName(name);
        return lecturerList.stream().map(user -> mapDTO.mapToDTO(user)).collect(Collectors.toList());
        }




    @Override
    public List<UserDTO> getUserByEmptySlotId(Long slotId) {
//        // retrieve comments by postId
//        List<User> users = userRepository.findUserByEmptySlot_SlotId(slotId);
//
//        // convert list of comment entities to list of comment dto's
//        return users.stream().map(user -> mapToDTO(user)).collect(Collectors.toList());
        return null;
    }

    // convert entity to DTO
    public UserDTO mapToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
    // convert DTO to entity
    public User mapToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

}
