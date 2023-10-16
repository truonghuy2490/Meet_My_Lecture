package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import com.springboot.meetMyLecturer.modelDTO.UserProfileDTO;

import java.util.List;

public interface UserService {

    UserDTO registerUser(Long roleId, User user);

    List<UserDTO> getUserByEmptySlotId(Long slotId);

    UserProfileDTO viewProfile(Long userId);

    UserProfileDTO updateProfile(Long userId, Long majorId, User user);

    List<String> getAllUsers();

    UserProfileDTO viewProfileByEmail(String email);

    String deleteUser(Long userId);



}
