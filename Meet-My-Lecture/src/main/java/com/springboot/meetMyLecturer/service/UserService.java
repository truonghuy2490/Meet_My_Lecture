package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.*;
import com.springboot.meetMyLecturer.entity.SubjectLecturerStudentId;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.SlotResponse;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.SubjectResponse;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.UserResponse;
import com.springboot.meetMyLecturer.modelDTO.SubjectLecturerStudentDTO;
import com.springboot.meetMyLecturer.modelDTO.UserRegister;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.List;
import java.util.Set;

public interface UserService {

    UserProfileDTO viewProfileUser(Long userId);

    UserProfileDTO updateProfile(Long userId, UserRegister userRegister);

//    List<UserProfileForAdminDTO> getAllUsers();

    List<EmptySlotResponseDTO> viewEmptySlot(Long lecturerId);

    SlotResponse viewEmptySlotForAdmin(int pageNo, int pageSize, String sortBy, String sortDir,
                                       Long lecturerId,
                                       String filter);

    UserProfileForAdminDTO viewProfileByUserId(Long userId);

    UserProfileForAdminDTO updateUserStatus(Long userId, String status);

    List<LecturerSubjectResponseDTO> updateSubjectsForStudent(Set<SubjectLecturerStudentDTO> subjectLecturerStudent);

    List <LecturerSubjectResponseDTO> insertSubjectsForStudent(Set<SubjectLecturerStudentId> subjectLecturerStudentId);

    String deleteSubjectsForStudent(SubjectLecturerStudentId subjectLecturerStudentId);

    // paging
    List<MajorResponseDTO> getAllMajors();

    List<EmptySlotResponseForSemesterDTO> getEmptySlotsInSemester(Long userId, Long semesterId);

    UserRoleResponseDTO getUserId(String email);

    UserResponse getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir, String status);

    List<UserProfileForAdminDTO> searchUser(String keyword);
}
