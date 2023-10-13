package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO registerUser(int roleId, User user);

    List<UserDTO> searchLecturers(String name);

    List<UserDTO> getUserByEmptySlotId(Long slotId);

}
