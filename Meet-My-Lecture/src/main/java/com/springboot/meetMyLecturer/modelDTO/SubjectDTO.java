package com.springboot.meetMyLecturer.modelDTO;

import com.springboot.meetMyLecturer.entity.User;
import lombok.Data;

import java.util.Set;

@Data
public class SubjectDTO {
    private String subjectId;
    private String subjectName;
}
