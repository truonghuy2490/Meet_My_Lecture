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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

        User lecturer = checkUser(lecturerSubjectId.getLecturerId());

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
    public  List<LecturerSubjectResponseDTO> insertTaughtSubjects(Set<LecturerSubjectId> lecturerSubjectId) {

        return lecturerSubjectId.stream().map(
                ls -> {
                    User lecturer = checkUser(ls.getLecturerId());

                    Subject subject = subjectRepository.findSubjectBySubjectIdAndStatus(ls.getSubjectId(), Constant.OPEN);
                    if(subject == null) throw new RuntimeException("This subject is not existed.");


                    LecturerSubject lecturerSubjectDB = lecturerSubjectRepository.findLecturerSubjectByLecturerSubjectId(ls);


                    if(lecturerSubjectDB.getStatus().equals(Constant.CLOSED) ){
                        lecturerSubjectDB.setStatus(Constant.OPEN);
                        lecturerSubjectRepository.save(lecturerSubjectDB);

                        LecturerSubjectResponseDTO lecturerSubjectDTO = modelMapper.map(lecturerSubjectDB, LecturerSubjectResponseDTO.class);
                        lecturerSubjectDTO.setUnique(lecturer.getUnique());

                        return lecturerSubjectDTO;

                    }else if(lecturerSubjectDB.getStatus().equals(Constant.OPEN)){
                        throw new RuntimeException("You already teach this subject.");
                    }

                    LecturerSubject lecturerSubject = new LecturerSubject();
                    lecturerSubject.setLecturerSubjectId(ls);
                    lecturerSubject.setLecturer(lecturer);
                    lecturerSubject.setSubject(subject);
                    lecturerSubjectRepository.save(lecturerSubject);

                    LecturerSubjectResponseDTO lecturerSubjectResponse = modelMapper.map(lecturerSubject, LecturerSubjectResponseDTO.class);
                    lecturerSubjectResponse.setUnique(lecturer.getUnique());
                    return  lecturerSubjectResponse;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public List<LecturerSubjectResponseDTO> getAllSubjects(Long lecturerId) {

        User lecturer = userRepository.findUserByUserIdAndStatus(lecturerId, Constant.OPEN);
        if(lecturer == null) throw new ResourceNotFoundException("Lecturer","id",String.valueOf(lecturerId));

        List<Subject> subjectList = subjectRepository.findSubjectsByLecturerIdAndStatus(lecturer.getUserId(), Constant.OPEN);

        return subjectList.stream().map(
                subject -> {
                    LecturerSubjectResponseDTO dto = new LecturerSubjectResponseDTO();
                    dto.setLecturerId(lecturer.getUserId());
                    dto.setUnique(lecturer.getUnique());
                    dto.setLecturerName(lecturer.getUserName());
                    dto.setSubjectId(subject.getSubjectId());
                    return dto;
                }
        ).collect(Collectors.toList());
    }

    public  User checkUser(Long userId){
        User lecturer = userRepository.findUserByUserIdAndStatus(userId, Constant.OPEN);
        if(lecturer == null) throw new RuntimeException("This lecturer is not existed.");
        return lecturer;
    }

}
