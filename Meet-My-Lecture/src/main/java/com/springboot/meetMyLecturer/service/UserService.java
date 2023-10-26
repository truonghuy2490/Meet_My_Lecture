package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.*;
import com.springboot.meetMyLecturer.entity.SubjectLecturerStudentId;
import com.springboot.meetMyLecturer.modelDTO.UserRegister;

import java.util.List;

public interface UserService {

    UserProfileDTO viewProfileUser(Long userId);

    UserProfileDTO updateProfile(Long userId, UserRegister userRegister);

    List<UserProfileForAdminDTO> getAllUsers();

    List<EmptySlotResponseDTO> viewEmptySlot(Long lecturerId);

    List<EmptySlotResponseDTO> viewEmptySlotForAdmin(Long lecturerId);

    UserProfileForAdminDTO viewProfileByUserId(Long userId);

    UserProfileForAdminDTO updateUserStatus(Long userId, String status);

    LecturerSubjectResponseDTO updateSubjectsForStudent(SubjectLecturerStudentId subjectLecturerStudentId);

    LecturerSubjectResponseDTO insertSubjectsForStudent(SubjectLecturerStudentId subjectLecturerStudentId);

    String deleteSubjectsForStudent(SubjectLecturerStudentId subjectLecturerStudentId);

    List<MajorResponseDTO> getAllMajors();

    List<EmptySlotResponseForSemesterDTO> getEmptySlotsInSemester(Long userId, Long semesterId);
}
