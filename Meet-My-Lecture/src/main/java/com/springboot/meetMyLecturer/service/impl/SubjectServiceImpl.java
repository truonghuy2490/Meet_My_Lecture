package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.MajorResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectMajorResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectResponseDTO;
import com.springboot.meetMyLecturer.constant.Constant;
import com.springboot.meetMyLecturer.entity.*;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.SubjectForAminDTO;
import com.springboot.meetMyLecturer.repository.*;
import com.springboot.meetMyLecturer.service.SubjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SubjectMajorRepository subjectMajorRepository;

    @Autowired
    MajorRepository majorRepository;


    // search subject by subjectId DONE - DONE
    @Override
    public List<LecturerSubjectResponseDTO> searchSubject(String keyword) {
        List<Subject> subjectList = subjectRepository.findSubjectBySubjectIdContainsAndStatus(keyword, Constant.OPEN);

        if(subjectList.isEmpty()){
            throw  new RuntimeException("There are no subjects with this name:" + keyword);
        }

        return subjectList.stream()
                .flatMap(subject -> {
                    List<User> lecturerList = subjectRepository.findLecturerBySubjectIdAndStatus(subject.getSubjectId(), Constant.OPEN);
                    return lecturerList.stream().map(lecturer -> {
                        LecturerSubjectResponseDTO dto = new LecturerSubjectResponseDTO();
                        dto.setLecturerId(lecturer.getUserId());
                        dto.setSubjectId(subject.getSubjectId());
                        dto.setLecturerName(lecturer.getUserName());
                        dto.setUnique(lecturer.getUnique());
                        return dto;
                    });
                }).collect(Collectors.toList());
    }

    //search subject by majorId DONE - DONE
    @Override
    public List<LecturerSubjectResponseDTO> getSubjectByMajorId(Long majorId) {
        Major major = majorRepository.findMajorByMajorIdAndStatus(majorId, Constant.OPEN);
        if (major == null){
            throw new RuntimeException("This major is not existed.");
        }

        List<String> subjects = subjectMajorRepository.findSubjectIdByMajorId(major.getMajorId());

        List<Subject> subjectList = subjects.stream()
                .map(subject -> subjectRepository.findSubjectBySubjectIdAndStatus(subject, Constant.OPEN))
                .filter(Objects::nonNull)
                .toList();

        return subjectList.stream()
                .flatMap(subject -> {
                    List<User> lecturerList = subjectRepository.findLecturerBySubjectIdAndStatus(subject.getSubjectId(), Constant.OPEN);
                    return lecturerList.stream()
                            .map(lecturer -> {
                                LecturerSubjectResponseDTO lecturerSubjectResponseDTO = new LecturerSubjectResponseDTO();
                                lecturerSubjectResponseDTO.setUnique(lecturer.getUnique());
                                lecturerSubjectResponseDTO.setLecturerName(lecturer.getUserName());
                                lecturerSubjectResponseDTO.setSubjectId(subject.getSubjectId());
                                lecturerSubjectResponseDTO.setLecturerId(lecturer.getUserId());
                                return lecturerSubjectResponseDTO;
                            });
                }).collect(Collectors.toList());
    }

    //get all subjects for amin DONE - DONE
    @Override
    public List<SubjectMajorResponseDTO> getAllSubjects() {
        List<Subject> subjectList = subjectRepository.findAll();
        if (subjectList.isEmpty()) {
            throw new RuntimeException("There are no subjects");
        }

        return subjectList.stream()
                .map(subject -> {
                    List<Long> lecturerList = userRepository.findLecturerIdBySubjectId(subject.getSubjectId());
                    List<Long> majorList = majorRepository.findMajorIdBySubjectId(subject.getSubjectId());

                    Set<MajorResponseDTO> majorSet = majorList.stream()
                            .map(majorId -> majorRepository.findById(majorId)
                                    .orElseThrow(() -> new ResourceNotFoundException("Major", "id", String.valueOf(majorId)))
                            )
                            .map(major -> modelMapper.map(major, MajorResponseDTO.class))
                            .collect(Collectors.toSet());

                    if (lecturerList.isEmpty()) {
                        SubjectMajorResponseDTO subjectMajorResponseDTO = new SubjectMajorResponseDTO();
                        subjectMajorResponseDTO.setSubjectId(subject.getSubjectId());
                        subjectMajorResponseDTO.setMajor(majorSet);
                        subjectMajorResponseDTO.setSubjectName(subject.getSubjectName());
                        subjectMajorResponseDTO.setStatus(subject.getStatus());
                        return Collections.singletonList(subjectMajorResponseDTO);
                    } else {
                        return lecturerList.stream()
                                .map(lecturer -> {
                                    String lecturerName = userRepository.findUserNameByUserId(lecturer);
                                    String unique = userRepository.findUniqueByUserId(lecturer);
                                    SubjectMajorResponseDTO dto = new SubjectMajorResponseDTO();
                                    dto.setLecturerId(lecturer);
                                    dto.setSubjectId(subject.getSubjectId());
                                    dto.setSubjectName(subject.getSubjectName());
                                    dto.setLecturerName(lecturerName);
                                    dto.setUnique(unique);
                                    dto.setStatus(subject.getStatus());
                                    dto.setMajor(majorSet);
                                    return dto;
                                })
                                .collect(Collectors.toList());
                    }
                })
                .flatMap(List::stream) // Flatten the inner lists
                .collect(Collectors.toList());
    }

    //create subject for admin DONE - DONE
    @Override
    public SubjectResponseDTO createSubject(Long adminId, SubjectForAminDTO subjectDTO) {

        Set<Long> majorSet = subjectDTO.getMajorId();

        List<Long> majorList = majorSet.stream().toList();

        User admin = userRepository.findById(adminId).orElseThrow(
                ()-> new ResourceNotFoundException("Admin","id",String.valueOf(adminId))
        );

        Subject subject = new Subject();
        subject.setSubjectName(subjectDTO.getSubjectName());
        subject.setSubjectId(subjectDTO.getSubjectId());
        subject.setStatus(Constant.OPEN);
        subject.setAdmin(admin);
        subjectRepository.save(subject);

        SubjectMajor subjectMajor = new SubjectMajor();
        SubjectMajorId subjectMajorId = new SubjectMajorId();

        for (Long aLong : majorList) {
            Major major = majorRepository.findMajorByMajorIdAndStatus(aLong, Constant.OPEN);
            if (major != null) {
                subjectMajorId.setSubjectId(subjectDTO.getSubjectId());
                subjectMajorId.setMajorId(major.getMajorId());
                subjectMajor.setSubjectMajorId(subjectMajorId);
                subjectMajor.setMajor(major);
                subjectMajor.setSubject(subject);
                subjectMajorRepository.save(subjectMajor);
            } else {
                throw new ResourceNotFoundException("Major", "id", String.valueOf(aLong));
            }
        }
        return modelMapper.map(subject, SubjectResponseDTO.class);
    }

    // edit subject and major for admin DONE-DONE
    @Override
    public SubjectResponseDTO editSubjectsInMajor(Long adminId, String subjectId, Long majorId) {
        User admin = userRepository.findById(adminId).orElseThrow(
                ()-> new ResourceNotFoundException("Admin","id",String.valueOf(adminId))
        );

        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                ()-> new ResourceNotFoundException("Subject","id",subjectId)
        );

        if(subject.getStatus().equals(Constant.CLOSED)){
            throw new RuntimeException("This subject is disable.");
        }

        Major major = majorRepository.findById(majorId).orElseThrow(
                ()-> new ResourceNotFoundException("Major","id",String.valueOf(majorId))
        );

        if (major.getStatus().equals(Constant.CLOSED)){
            throw new RuntimeException("This major is disable.");
        }

        SubjectMajorId subjectMajorId = new SubjectMajorId();
        subjectMajorId.setMajorId(majorId);
        subjectMajorId.setSubjectId(subjectId);

        SubjectMajor subjectMajor = subjectMajorRepository.findSubjectMajorBySubjectMajorId(subjectMajorId);
        if(subjectMajor == null){
            throw new RuntimeException("These subject and major is not existed.");
        }

        subjectMajor.setMajor(major);
        subjectMajor.setSubject(subject);
        subjectMajor.setSubjectMajorId(subjectMajorId);

        subjectMajorRepository.save(subjectMajor);

        return modelMapper.map(subjectMajor, SubjectResponseDTO.class);
    }

    // get subject by subjectId for student DONE-DONE
    @Override
    public SubjectResponseDTO getSubjectBySubjectId(String subjectId) {
        Subject subject = subjectRepository.findSubjectBySubjectIdAndStatus(subjectId, Constant.OPEN);

        if(subject == null) throw new ResourceNotFoundException("Subject","id",subjectId);
        return modelMapper.map(subject, SubjectResponseDTO.class);
    }


}
