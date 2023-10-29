package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.*;
import com.springboot.meetMyLecturer.entity.SubjectLecturerStudentId;
import com.springboot.meetMyLecturer.modelDTO.SubjectLecturerStudentDTO;
import com.springboot.meetMyLecturer.modelDTO.UserRegister;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.List;

public interface UserService {

    UserProfileDTO viewProfileUser(Long userId);

    UserProfileDTO updateProfile(Long userId, UserRegister userRegister);

    List<UserProfileForAdminDTO> getAllUsers();

    List<EmptySlotResponseDTO> viewEmptySlot(Long lecturerId);

    List<EmptySlotResponseDTO> viewEmptySlotForAdmin(Long lecturerId);

    UserProfileForAdminDTO viewProfileByUserId(Long userId);

    UserProfileForAdminDTO updateUserStatus(Long userId, String status);

    LecturerSubjectResponseDTO updateSubjectsForStudent(SubjectLecturerStudentDTO subjectLecturerStudent);

    LecturerSubjectResponseDTO insertSubjectsForStudent(SubjectLecturerStudentId subjectLecturerStudentId);

    String deleteSubjectsForStudent(SubjectLecturerStudentId subjectLecturerStudentId);

    List<MajorResponseDTO> getAllMajors();

    List<EmptySlotResponseForSemesterDTO> getEmptySlotsInSemester(Long userId, Long semesterId);

    UserRoleResponseDTO getUserId(String email);
}
