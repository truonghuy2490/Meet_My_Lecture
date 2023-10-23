package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.entity.Semester;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.SemesterDTO;
import com.springboot.meetMyLecturer.repository.SemesterRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.SemesterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        semester.setAdmin(admin);

        semesterRepository.save(semester);
        SemesterDTO responseSemester = modelMapper.map(semester, SemesterDTO.class);
        return responseSemester;
    }

    @Override
    public void deleteSemester(Long semesterId, Long adminId) {
        User admin = userRepository.findById(adminId).orElseThrow(
                ()-> new ResourceNotFoundException("Admin", "id", String.valueOf(adminId))
        );

        Semester semester = semesterRepository.findById(semesterId).orElseThrow(
                () -> new ResourceNotFoundException("Semester", "id", String.valueOf(semesterId))
        );
        semesterRepository.delete(semester);

    }

    @Override
    public SemesterDTO updateSemester(SemesterDTO semesterDTO, Long adminId, Long semesterId) {
        User admin = userRepository.findById(adminId).orElseThrow(
                ()-> new ResourceNotFoundException("Admin", "id", String.valueOf(adminId))
        );

        Semester semester = semesterRepository.findById(semesterId).orElseThrow(
                () -> new ResourceNotFoundException("Semester", "id", String.valueOf(semesterId))
        );

        semester.setSemesterName(semesterDTO.getSemesterName());
        semester.setDateEnd(semesterDTO.getDateEnd());
        semester.setDateStart(semesterDTO.getDateStart());

        // check year if belong to [dateStar, dateEnd]
        semester.setYear(semesterDTO.getYear());
        if (semesterDTO.getYear() < semesterDTO.getDateStart().getYear() ||
                semesterDTO.getYear() > semesterDTO.getDateEnd().getYear()) {
            throw new IllegalArgumentException("The year is not within the valid date range.");
        }

        semesterRepository.save(semester);
        return modelMapper.map(semester, SemesterDTO.class);
    }
}
