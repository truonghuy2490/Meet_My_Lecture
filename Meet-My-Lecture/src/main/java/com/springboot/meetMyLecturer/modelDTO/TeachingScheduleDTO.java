package com.springboot.meetMyLecturer.modelDTO;

import com.springboot.meetMyLecturer.ResponseDTO.UserRegisterResponseDTO;
import com.springboot.meetMyLecturer.entity.SlotTime;
import lombok.Data;

import java.sql.Date;

@Data
public class TeachingScheduleDTO {

    private Date date;

    private Long lecturerId;

    private String subjectId;

    private String roomId;

    private String meetingURL;

    private int slotTimeId;

    private String status;
}
