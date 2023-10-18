package com.springboot.meetMyLecturer.modelDTO;

import com.springboot.meetMyLecturer.entity.Slot;
import com.springboot.meetMyLecturer.entity.Subject;
import lombok.Data;

import java.sql.Time;

@Data
public class TeachingScheduleDTO {

    private int teachingScheduleId;

    private String dateOfWeek;

    private UserDTO lecturer;

    private SubjectDTO subject;

    private int roomId;

    private String meetingURL;

    private Slot slot;
}
