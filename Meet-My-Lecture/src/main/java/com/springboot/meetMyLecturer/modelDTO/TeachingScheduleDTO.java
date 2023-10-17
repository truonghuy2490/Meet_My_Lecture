package com.springboot.meetMyLecturer.modelDTO;

import com.springboot.meetMyLecturer.entity.Slot;
import com.springboot.meetMyLecturer.entity.Subject;
import lombok.Data;

import java.sql.Time;

@Data
public class TeachingScheduleDTO {
    private int id;

    private String dateOfWeek;

    private UserDTO lecture;

    private SubjectDTO subject;

    private int room;

    private String meetingURL;

    private Slot slot;
}
