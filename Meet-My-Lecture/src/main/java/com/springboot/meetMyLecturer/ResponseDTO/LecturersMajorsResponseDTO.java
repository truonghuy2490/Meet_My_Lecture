package com.springboot.meetMyLecturer.ResponseDTO;

import lombok.Data;

import java.util.Set;

@Data
public class LecturersMajorsResponseDTO {
    Set<UserProfileDTO> lecturerSet;
    Set<MajorResponseDTO> majorSet;
    String subjectName;
    String subjectId;
}
