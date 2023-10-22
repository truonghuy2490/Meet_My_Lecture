package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.EmptySlotResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.UserRegisterResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.UserProfileDTO;
import com.springboot.meetMyLecturer.entity.SubjectLecturerStudentId;
import com.springboot.meetMyLecturer.modelDTO.UserRegister;

import java.util.List;

public interface UserService {

    UserRegisterResponseDTO registerUser(String userName);

    UserProfileDTO viewProfileUser(Long userId);

    UserProfileDTO updateProfile(Long userId, UserRegister userRegister);

    List<UserProfileDTO> getAllUsers();

    List<EmptySlotResponseDTO> viewEmptySlot(Long lecturerId);

    UserProfileDTO viewProfileByUserId(Long userId);

    String deleteUser(Long userId);

    LecturerSubjectResponseDTO updateSubjectsForStudent(SubjectLecturerStudentId subjectLecturerStudentId);

    LecturerSubjectResponseDTO insertSubjectsForStudent(SubjectLecturerStudentId subjectLecturerStudentId);

    String deleteSubjectsForStudent(SubjectLecturerStudentId subjectLecturerStudentId);
}
