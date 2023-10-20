package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.UserRegisterResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.UserProfileDTO;
import com.springboot.meetMyLecturer.modelDTO.UserRegister;

import java.util.List;

public interface UserService {

    UserRegisterResponseDTO registerUser(Long roleId, UserRegister userRegister);

    UserProfileDTO viewProfileUser(Long userId);

    UserProfileDTO updateProfileForStudent(Long studentId, UserRegister userRegister);

    List<String> getAllUsers();

    UserProfileDTO viewProfileByUserId(Long userId);

    String deleteUser(Long userId);

}
