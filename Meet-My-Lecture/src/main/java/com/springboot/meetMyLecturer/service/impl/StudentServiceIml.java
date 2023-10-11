package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.entity.Subject;
import com.springboot.meetMyLecturer.repository.SubjectRepository;
import com.springboot.meetMyLecturer.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceIml implements StudentService {

    @Autowired
    SubjectRepository subjectRepository;


    @Override
    public List<Subject> searchSubject(String keyword) {
        return subjectRepository.findSubjectBySubjectIdOrSubjectNameContains(keyword);
    }
}
