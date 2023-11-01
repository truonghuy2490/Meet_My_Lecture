package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.SemesterResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectSemesterResponseDTO;
import com.springboot.meetMyLecturer.constant.Constant;
import com.springboot.meetMyLecturer.entity.*;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.SemesterDTO;
import com.springboot.meetMyLecturer.modelDTO.SubjectSemesterDTO;
import com.springboot.meetMyLecturer.repository.SemesterRepository;
import com.springboot.meetMyLecturer.repository.SubjectRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.SemesterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SemesterServiceImpl implements SemesterService {
    @Autowired
    SemesterRepository semesterRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SubjectRepository subjectRepository;

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

    //get all semesters for admin DONE-DONE
    @Override
    public List<SemesterResponseDTO> getAllSemestersForAdmin() {
        List<Semester> semesterList = semesterRepository.findAll();
        if(semesterList.isEmpty()){
            throw new RuntimeException("There are no semesters.");
        }
        return semesterList.stream().map(semester -> modelMapper.map(semester, SemesterResponseDTO.class)).collect(Collectors.toList());
    }

    //create semester for admin DONE-DONE
    @Override
    public SemesterResponseDTO createSemester(Long adminId, SemesterDTO semesterDTO) {
        User admin = userRepository.findById(adminId).orElseThrow(
                ()-> new ResourceNotFoundException("User","id",String.valueOf(adminId))
        );

        String dateStart = String.valueOf(semesterDTO.getDateStart());

        String[] parts = dateStart.split("-");
        int yearCheck = Integer.parseInt(parts[0]);

        if(yearCheck != semesterDTO.getYear()){
            throw new RuntimeException("Year in date and year you input are not the same.");
        }

        Semester semester = modelMapper.map(semesterDTO, Semester.class);
        semester.setStatus(Constant.OPEN);
        semester.setAdmin(admin);
        semesterRepository.save(semester);

        return modelMapper.map(semester, SemesterResponseDTO.class);
    }

    //edit semester for admin DONE-DONE
    @Override
    public SemesterResponseDTO editSemester(Long adminId, Long semesterId, SemesterDTO semesterDTO) {
        User admin = userRepository.findById(adminId).orElseThrow(
                ()-> new ResourceNotFoundException("User","id",String.valueOf(adminId))
        );

        String dateStart = String.valueOf(semesterDTO.getDateStart());

        String[] parts = dateStart.split("-");
        int yearCheck = Integer.parseInt(parts[0]);

        if(yearCheck != semesterDTO.getYear()){
            throw new RuntimeException("Year in date and year you input are not the same.");
        }

        Semester semester = semesterRepository.findById(semesterId).orElseThrow(
                ()-> new ResourceNotFoundException("Semester","id", String.valueOf(semesterId))
        );
        semester.setSemesterName(semesterDTO.getSemesterName());
        semester.setDateStart(semesterDTO.getDateStart());
        semester.setDateEnd(semesterDTO.getDateEnd());
        semester.setYear(semesterDTO.getYear());
        semester.setStatus(semesterDTO.getStatus().toUpperCase());

        if(!semester.getAdmin().equals(admin)){
            semester.setAdmin(admin);
        }

        semesterRepository.save(semester);

        return modelMapper.map(semester, SemesterResponseDTO.class);
    }

    //DONE-DONE
    @Override
    public String deleteSemester(Long adminId, Long semesterId) {
        User admin = userRepository.findById(adminId).orElseThrow(
                ()-> new ResourceNotFoundException("User","id",String.valueOf(adminId))
        );

        Semester semester = semesterRepository.findById(semesterId).orElseThrow(
                ()-> new ResourceNotFoundException("Semester","id",String.valueOf(semesterId))
        );

        if(!semester.getAdmin().equals(admin)){
            semester.setAdmin(admin);
        }

        semester.setStatus(Constant.CLOSED);

        return "This semester has been deleted.";
    }

    //insert subject into semester for admin DONE-DONE
    @Override
    public SubjectSemesterResponseDTO insertSubjectIntoSemester(Long adminId, SubjectSemesterDTO subjectSemesterDTO) {

        Semester semester = semesterRepository.findSemesterBySemesterIdAndStatus(subjectSemesterDTO.getSemesterId(), Constant.OPEN);
        if(semester == null) throw new ResourceNotFoundException("Semester","id",String.valueOf(subjectSemesterDTO.getSemesterId()));

        Map<String, String> subjectResponse = subjectSemesterDTO.getSubjectSet().stream()
                .map(s -> {
                    Subject subject = subjectRepository.findSubjectBySubjectIdAndStatus(s, Constant.OPEN);
                    if (subject.getSubjectName() == null) throw new ResourceNotFoundException("Subject", "id", s);

                    subject.getSemesterSet().add(semester);
                    semester.getSubjectSet().add(subject);

                    subjectRepository.save(subject);

                    return new AbstractMap.SimpleEntry<>(s, subject.getSubjectName());
                }).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

        SubjectSemesterResponseDTO subjectSemesterResponseDTO = new SubjectSemesterResponseDTO();
        subjectSemesterResponseDTO.setSubjectList(subjectResponse);
        subjectSemesterResponseDTO.setSemesterId(semester.getSemesterId());
        subjectSemesterResponseDTO.setSemesterName(semester.getSemesterName());

        return subjectSemesterResponseDTO;
    }


}
