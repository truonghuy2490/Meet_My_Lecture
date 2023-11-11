package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.MeetingRequestResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SemesterResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectSemesterResponseDTO;
import com.springboot.meetMyLecturer.constant.Constant;
import com.springboot.meetMyLecturer.entity.*;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.RequestResponse;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.SemesterResponse;
import com.springboot.meetMyLecturer.modelDTO.SemesterDTO;
import com.springboot.meetMyLecturer.modelDTO.SubjectSemesterDTO;
import com.springboot.meetMyLecturer.repository.SemesterRepository;
import com.springboot.meetMyLecturer.repository.SubjectRepository;
import com.springboot.meetMyLecturer.repository.SubjectSemesterRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.SemesterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    SubjectSemesterRepository subjectSemesterRepository;

    @Autowired
    ModelMapper modelMapper;

    //get all semesters for user not admin DONE-DONE
    @Override
    public List<SemesterResponseDTO> getAllSemesters() {
        List<Semester> semesterList = semesterRepository.findSemestersByStatus(Constant.OPEN);
        if(semesterList.isEmpty()){
            throw new RuntimeException("There are no semesters.");
        }
        return semesterList.stream().map(semester -> modelMapper.map(semester, SemesterResponseDTO.class)).collect(Collectors.toList());
    }

    //get semester info for admin DONE-DONE
    @Override
    public SemesterResponseDTO getSemesterInfo(Long majorId) {
        Semester semester = semesterRepository.findById(majorId).orElseThrow(
                ()-> new ResourceNotFoundException("Major","id", String.valueOf(majorId))
        );
        return modelMapper.map(semester, SemesterResponseDTO.class);
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
                    if (subject == null) throw new ResourceNotFoundException("Subject", "id", s);

                    SubjectSemesterId subjectSemesterId = new SubjectSemesterId();
                    subjectSemesterId.setSubjectId(subject.getSubjectId());
                    subjectSemesterId.setSemesterId(semester.getSemesterId());

                    SubjectSemester subjectSemester = subjectSemesterRepository.findById(subjectSemesterId).orElseThrow(
                            ()-> new ResourceNotFoundException(subject.getSubjectId(), String.valueOf(semester.getSemesterId()), "are not found.")
                    );

                    if(subjectSemester.getStatus().equals(Constant.CLOSED)){
                        subjectSemester.setStatus(Constant.OPEN);
                    }else if(subjectSemester.getStatus().equals(Constant.OPEN)){
                        throw new RuntimeException("This "+ subject.getSubjectId() + semester.getSemesterId() + "not found.");
                    }

                    subjectSemester.setSemester(semester);
                    subjectSemester.setSubject(subject);
                    subjectSemesterRepository.save(subjectSemester);

                    subjectRepository.save(subject);

                    return new AbstractMap.SimpleEntry<>(s, subject.getSubjectName());
                }).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

        SubjectSemesterResponseDTO subjectSemesterResponseDTO = new SubjectSemesterResponseDTO();
        subjectSemesterResponseDTO.setSubjectList(subjectResponse);
        subjectSemesterResponseDTO.setSemesterId(semester.getSemesterId());
        subjectSemesterResponseDTO.setSemesterName(semester.getSemesterName());

        return subjectSemesterResponseDTO;
    }

    @Override
    public SemesterResponse getAllSemesters(int pageNo, int pageSize, String sortBy, String sortDir, String status) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        // CREATE PAGEABLE INSTANCE
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        // SAVE TO REPO
        Page<Semester> semesters;
        if(status.equalsIgnoreCase(Constant.OPEN) || status.equalsIgnoreCase(Constant.CLOSED)){
            semesters = semesterRepository.findByStatus(status, pageable); // findAllByStudentId()
        }else if(status.isEmpty()){
            semesters = semesterRepository.findAll(pageable);
        }else{
            throw new RuntimeException("Invalid status");
        }

        if(semesters.isEmpty()){
            throw new RuntimeException("There are no semesters.");
        }

        // get content for page object
        List<Semester> listOfSemesters = semesters.getContent();

        List<SemesterResponseDTO> content = listOfSemesters.stream().map(
                semester -> modelMapper.map(semester, SemesterResponseDTO.class)
        ).collect(Collectors.toList());


        SemesterResponse semesterResponse = new SemesterResponse();
        semesterResponse.setContent(content);
        semesterResponse.setTotalPage(semesters.getTotalPages());
        semesterResponse.setTotalElement(semesters.getTotalElements());
        semesterResponse.setPageNo(semesters.getNumber());
        semesterResponse.setPageSize(semesters.getSize());
        semesterResponse.setLast(semesters.isLast());

        return semesterResponse;
    }

    public void automatedCreateSemester(){



    }


}
