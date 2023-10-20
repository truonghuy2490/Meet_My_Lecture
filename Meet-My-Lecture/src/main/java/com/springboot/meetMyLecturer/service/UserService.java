package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import com.springboot.meetMyLecturer.ResponseDTO.UserProfileDTO;

import java.util.List;

public interface UserService {

    UserDTO registerUser(Long roleId, User user);

    List<UserDTO> getUserByEmptySlotId(Long slotId);

    UserProfileDTO viewProfileUser(Long userId);

    UserProfileDTO updateProfileForStudent(Long studentId, Long majorId, UserDTO userDTO, String subjectId, Long lecturerId);

    List<String> getAllUsers();

    UserProfileDTO viewProfileByUserId(Long userId);

    String deleteUser(Long userId);



}
