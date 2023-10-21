package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectResponseDTO;
import com.springboot.meetMyLecturer.entity.LecturerSubject;
import com.springboot.meetMyLecturer.entity.Subject;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.repository.LecturerSubjectRepository;
import com.springboot.meetMyLecturer.repository.SubjectRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.LecturerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LecturerServiceImpl implements LecturerService {

    @Autowired
    LecturerSubjectRepository lecturerSubjectRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    ModelMapper modelMapper;

    // delete subjects for lecturer DONE
    @Override
    public String deleteSubjectsForLecturer(Long lecturerId, String subjectId) {

        User lecturer = userRepository.findById(lecturerId).orElseThrow(
                ()-> new ResourceNotFoundException("Lecturer","id", String.valueOf(lecturerId))
        );

        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                ()-> new ResourceNotFoundException("Subject","id",subjectId)
        );

        LecturerSubject lecturerSubject = lecturerSubjectRepository.findLecturerSubjectByLecturerIdAndSubjectId(lecturerId, subjectId);

        lecturerSubjectRepository.delete(lecturerSubject);

        return "This subject has been deleted!";

    }

    // insert subjects for lecturer
    @Override
    public LecturerSubjectResponseDTO insertTaughtSubjects(Long lecturerId, String subjectId) {
        User lecturer = userRepository.findById(lecturerId).orElseThrow(
                ()-> new ResourceNotFoundException("Lecturer","id", String.valueOf(lecturerId))
        );

        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                ()-> new ResourceNotFoundException("Subject","id",subjectId)
        );

        LecturerSubject lecturerSubject = new LecturerSubject();
        lecturerSubject.setLecturerId(lecturerId);
        lecturerSubject.setSubjectId(subjectId);
        lecturerSubjectRepository.save(lecturerSubject);

        LecturerSubjectResponseDTO lecturerSubjectResponseDTO = modelMapper.map(lecturerSubject, LecturerSubjectResponseDTO.class);
        lecturerSubjectResponseDTO.setLecturerName(lecturer.getUserName());
        lecturerSubjectResponseDTO.setUnique(lecturer.getUnique());

        return lecturerSubjectResponseDTO;
    }
}
