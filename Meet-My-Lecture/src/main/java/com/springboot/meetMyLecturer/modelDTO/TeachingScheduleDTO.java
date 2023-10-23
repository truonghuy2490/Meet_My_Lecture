package com.springboot.meetMyLecturer.modelDTO;

import com.springboot.meetMyLecturer.ResponseDTO.UserRegisterResponseDTO;
import com.springboot.meetMyLecturer.entity.SlotTime;
import lombok.Data;

@Data
public class TeachingScheduleDTO {

    private Long teachingScheduleId;

    private String dateOfWeek;

    private UserRegisterResponseDTO lecturer;

    private SubjectDTO subject;

    private String roomId;

    private String meetingURL;

    private int slotTimeId;
}
