package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.EmptySlotResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.UserRegisterResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.UserProfileDTO;
import com.springboot.meetMyLecturer.modelDTO.UserRegister;

import java.util.List;

public interface UserService {

    UserRegisterResponseDTO registerUser(Long roleId, UserRegister userRegister);

    UserProfileDTO viewProfileUser(Long userId);

    UserProfileDTO updateProfileForStudent(Long studentId, UserRegister userRegister);

    List<UserProfileDTO> getAllUsers();

    List<EmptySlotResponseDTO> viewEmptySlot(Long lecturerId);

    UserProfileDTO viewProfileByUserId(Long userId);

    String deleteUser(Long userId);

    LecturerSubjectResponseDTO updateSubjectsForStudent(String subjectId, Long lecturerId, Long studentId);

}
