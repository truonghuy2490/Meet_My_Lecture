package com.springboot.meetMyLecturer.modelDTO;

import com.springboot.meetMyLecturer.ResponseDTO.UserRegisterResponseDTO;
import com.springboot.meetMyLecturer.entity.SlotTime;
import lombok.Data;

@Data
public class TeachingScheduleDTO {

    private int teachingScheduleId;

    private String dateOfWeek;

    private UserRegisterResponseDTO lecturer;

    private SubjectDTO subject;

    private int roomId;

    private String meetingURL;

    private int slotTimeId;
}
