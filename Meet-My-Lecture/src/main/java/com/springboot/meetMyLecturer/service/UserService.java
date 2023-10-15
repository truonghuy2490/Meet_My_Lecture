package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import com.springboot.meetMyLecturer.modelDTO.UserProfileDTO;

import java.util.List;

public interface UserService {

    UserDTO registerUser(int roleId, User user);

    List<UserDTO> getUserByEmptySlotId(int slotId);

    UserProfileDTO viewProfile(long userId);

    UserProfileDTO updateProfile(long userId, int majorId, User user);



}
