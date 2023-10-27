package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.SemesterResponseDTO;
import com.springboot.meetMyLecturer.constant.Constant;
import com.springboot.meetMyLecturer.entity.Semester;
import com.springboot.meetMyLecturer.repository.SemesterRepository;
import com.springboot.meetMyLecturer.service.SemesterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SemesterServiceImpl implements SemesterService {
    @Autowired
    SemesterRepository semesterRepository;

    @Autowired
    ModelMapper modelMapper;

    //get all semesters DONE-DONE
    @Override
    public List<SemesterResponseDTO> getAllSemesters() {
        List<Semester> semesterList = semesterRepository.findSemestersByStatus(Constant.OPEN);
        if(semesterList.isEmpty()){
            throw new RuntimeException("There are no semesters.");
        }
        return semesterList.stream().map(semester -> modelMapper.map(semester, SemesterResponseDTO.class)).collect(Collectors.toList());
    }
}
