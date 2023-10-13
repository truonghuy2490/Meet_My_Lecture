package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.entity.Subject;

import java.util.List;

public interface StudentService {
    List<Subject> searchSubject (String keyword);

}
