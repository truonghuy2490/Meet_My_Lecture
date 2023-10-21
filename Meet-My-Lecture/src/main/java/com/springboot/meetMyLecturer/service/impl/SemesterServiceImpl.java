package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.entity.Semester;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.SemesterDTO;
import com.springboot.meetMyLecturer.repository.SemesterRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.SemesterService;
import io.swagger.v3.oas.annotations.servers.Server;
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
    @Autowired
    UserRepository userRepository;
    @Override
    public List<SemesterDTO> getAllSemester() {
        List<Semester> semesters = semesterRepository.findAll();

        return semesters.stream().map(
                semester -> modelMapper.map(semester, SemesterDTO.class)
        ).collect(Collectors.toList());
    }

    @Override
    public SemesterDTO createSemester(Long adminId, SemesterDTO semesterDTO) {
        Semester semester = modelMapper.map(semesterDTO, Semester.class);

        User admin = userRepository.findById(adminId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", String.valueOf(adminId))
        );
        semester.setUser(admin);

        semesterRepository.save(semester);
        SemesterDTO responseSemester = modelMapper.map(semester, SemesterDTO.class);
        responseSemester.setAdminId(semester.getUser().getUserId());
        return responseSemester;
    }
}
