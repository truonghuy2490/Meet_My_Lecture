package com.springboot.meetMyLecturer.modelDTO;

import com.springboot.meetMyLecturer.ResponseDTO.UserRegisterResponseDTO;
import com.springboot.meetMyLecturer.entity.Slot;
import lombok.Data;

@Data
public class TeachingScheduleDTO {

    private int teachingScheduleId;

    private String dateOfWeek;

    private UserRegisterResponseDTO lecturer;

    private SubjectDTO subject;

    private int roomId;

    private String meetingURL;

    private Slot slot;
}
