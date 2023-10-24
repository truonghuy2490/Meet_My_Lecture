package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectResponseDTO;
import com.springboot.meetMyLecturer.constant.Constant;
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

    // delete subjects for lecturer DONE-DONE
    @Override
    public String deleteSubjectsForLecturer(LecturerSubjectId lecturerSubjectId) {

        User lecturer = userRepository.findUserByUserIdAndStatus(lecturerSubjectId.getLecturerId(), Constant.OPEN);
        if(lecturer == null) throw new RuntimeException("This lecturer is not existed.");

        Subject subject = subjectRepository.findSubjectBySubjectIdAndStatus(lecturerSubjectId.getSubjectId(), Constant.OPEN);
        if(subject == null) throw new RuntimeException("This subject is not existed.");

        LecturerSubject lecturerSubject = lecturerSubjectRepository.findLecturerSubjectByLecturerSubjectId(lecturerSubjectId);

        if(lecturerSubject == null){
            throw  new RuntimeException("You do not teach this subject.");
        }

        lecturerSubject.setStatus(Constant.CLOSED);

        lecturerSubjectRepository.save(lecturerSubject);

        return "This subject has been deleted!";

    }

    // insert subjects for lecturer DONE-DONE
    @Override
    public LecturerSubjectResponseDTO insertTaughtSubjects(LecturerSubjectId lecturerSubjectId) {
        User lecturer = userRepository.findUserByUserIdAndStatus(lecturerSubjectId.getLecturerId(), Constant.OPEN);
        if(lecturer == null) throw new RuntimeException("This lecturer is not existed.");

        Subject subject = subjectRepository.findSubjectBySubjectIdAndStatus(lecturerSubjectId.getSubjectId(), Constant.OPEN);
        if(subject == null) throw new RuntimeException("This subject is not existed.");


        LecturerSubject lecturerSubjectDB = lecturerSubjectRepository.findLecturerSubjectByLecturerSubjectId(lecturerSubjectId);


        if(lecturerSubjectDB.getStatus().equals(Constant.CLOSED) ){
            lecturerSubjectDB.setStatus(Constant.OPEN);
            lecturerSubjectRepository.save(lecturerSubjectDB);

            LecturerSubjectResponseDTO lecturerSubjectResponseDTO = modelMapper.map(lecturerSubjectDB, LecturerSubjectResponseDTO.class);
            lecturerSubjectResponseDTO.setUnique(lecturer.getUnique());

            return lecturerSubjectResponseDTO;

        }else if(lecturerSubjectDB.getStatus().equals(Constant.OPEN)){
            throw new RuntimeException("You already teach this subject.");
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
