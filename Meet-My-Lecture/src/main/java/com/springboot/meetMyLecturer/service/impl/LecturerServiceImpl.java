package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectResponseDTO;
import com.springboot.meetMyLecturer.entity.LecturerSubject;
import com.springboot.meetMyLecturer.entity.LecturerSubjectId;
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
    public String deleteSubjectsForLecturer(LecturerSubjectId lecturerSubjectId) {

        User lecturer = userRepository.findById(lecturerSubjectId.getLecturerId()).orElseThrow(
                ()-> new ResourceNotFoundException("Lecturer","id", String.valueOf(lecturerSubjectId.getLecturerId()))
        );

        Subject subject = subjectRepository.findById(lecturerSubjectId.getSubjectId()).orElseThrow(
                ()-> new ResourceNotFoundException("Subject","id",lecturerSubjectId.getSubjectId())
        );

        LecturerSubject lecturerSubject = lecturerSubjectRepository.findLecturerSubjectByLecturerSubjectId(lecturerSubjectId);

        if(lecturerSubject == null){
            throw  new RuntimeException("You do not teach this subject.");
        }

        lecturerSubjectRepository.delete(lecturerSubject);

        return "This subject has been deleted!";

    }

    // insert subjects for lecturer DONE
    @Override
    public LecturerSubjectResponseDTO insertTaughtSubjects(LecturerSubjectId lecturerSubjectId) {
        User lecturer = userRepository.findById(lecturerSubjectId.getLecturerId()).orElseThrow(
                ()-> new ResourceNotFoundException("Lecturer","id", String.valueOf(lecturerSubjectId.getLecturerId()))
        );

        Subject subject = subjectRepository.findById(lecturerSubjectId.getSubjectId()).orElseThrow(
                ()-> new ResourceNotFoundException("Subject","id",lecturerSubjectId.getSubjectId())
        );

        LecturerSubject lecturerSubjectDB = lecturerSubjectRepository.findLecturerSubjectByLecturerSubjectId(lecturerSubjectId);

        if(lecturerSubjectDB != null){
            throw  new RuntimeException("You already teach this subject");
        }

        LecturerSubject lecturerSubject = new LecturerSubject();
        lecturerSubject.setLecturerSubjectId(lecturerSubjectId);
        lecturerSubject.setLecturer(lecturer);
        lecturerSubject.setSubject(subject);
        lecturerSubjectRepository.save(lecturerSubject);

        LecturerSubjectResponseDTO lecturerSubjectResponseDTO = modelMapper.map(lecturerSubject, LecturerSubjectResponseDTO.class);
        lecturerSubjectResponseDTO.setUnique(lecturer.getUnique());

        return lecturerSubjectResponseDTO;
    }
}
