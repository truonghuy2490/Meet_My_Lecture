package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.*;
import com.springboot.meetMyLecturer.constant.Constant;
import com.springboot.meetMyLecturer.entity.*;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.SlotResponse;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.SubjectResponse;
import com.springboot.meetMyLecturer.modelDTO.SubjectDTO;
import com.springboot.meetMyLecturer.modelDTO.SubjectForAminDTO;
import com.springboot.meetMyLecturer.repository.*;
import com.springboot.meetMyLecturer.service.SubjectService;
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
                    List<User> lecturerList = subjectRepository.findLecturerBySubjectId(subject.getSubjectId());
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
    public SubjectMajorResponseForAdminDTO createSubject(Long adminId, SubjectForAminDTO subjectDTO) {

        List<Long> majorList = subjectDTO.getMajorId().stream().toList();

        User admin = userRepository.findById(adminId).orElseThrow(
                ()-> new ResourceNotFoundException("Admin","id",String.valueOf(adminId))
        );

        Subject subjectDB = subjectRepository.findSubjectNameBySubjectId(subjectDTO.getSubjectId());
        if(subjectDB != null){
            if(subjectDB.getStatus().equals(Constant.CLOSED)){
                subjectDB.setStatus(Constant.OPEN);
            }else{
                throw new RuntimeException("This subject is already existed.");
            }
        }

        Subject subject = new Subject();
        subject.setSubjectName(subjectDTO.getSubjectName());
        subject.setSubjectId(subjectDTO.getSubjectId());
        subject.setStatus(Constant.OPEN);
        subject.setAdmin(admin);
        subjectRepository.save(subject);

        for (Long aLong : majorList) {
            Major major = majorRepository.findMajorByMajorIdAndStatus(aLong, Constant.OPEN);
            if(major == null) throw new ResourceNotFoundException("Major","id",String.valueOf(aLong));
            SubjectMajorId subjectMajorId = new SubjectMajorId();
            subjectMajorId.setSubjectId(subject.getSubjectId());
            subjectMajorId.setMajorId(major.getMajorId());

            SubjectMajor subjectMajor = subjectMajorRepository.findSubjectMajorBySubjectMajorId(subjectMajorId);

            if(subjectMajor == null){
                SubjectMajor subjectMajorDB = new SubjectMajor();
                subjectMajorDB.setStatus(Constant.OPEN);
                subjectMajorDB.setMajor(major);
                subjectMajorDB.setSubject(subject);
                subjectMajorDB.setSubjectMajorId(subjectMajorId);
                subjectMajorRepository.save(subjectMajorDB);
            }else {
                if (subjectMajor.getStatus().equals(Constant.CLOSED)) {
                    subjectMajor.setStatus(Constant.OPEN);
                } else if (subjectMajor.getStatus().equals(Constant.OPEN)) {
                    throw new RuntimeException("This " + subject.getSubjectId() + " " + major.getMajorId() + " is already existed.");
                }
            }
        }

        SubjectMajorResponseForAdminDTO subjectResponseDTO = modelMapper.map(subject, SubjectMajorResponseForAdminDTO.class);
        subjectResponseDTO.setMajorId(subjectDTO.getMajorId());
        return subjectResponseDTO;
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

    @Override
    public List<LecturerSubjectResponseDTO> getSubjectsByMajorId(Long majorId) {
        List<Subject> subjectList = subjectRepository.findSubjectsByMajorId(majorId, Constant.OPEN);

        if(subjectList == null) throw new ResourceNotFoundException("Subjects", "is not found", "with this id: "+ majorId);
        return subjectList.stream().map(
                subject -> modelMapper.map(subject, LecturerSubjectResponseDTO.class)
        ).collect(Collectors.toList());
    }

    @Override
    public SubjectResponse getAllSubjects(int pageNo, int pageSize, String sortBy, String sortDir, String status) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        // CREATE PAGEABLE INSTANCE
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Subject> subjects;
        List<Subject> subjectListOpen = subjectRepository.findSubjectsByStatus(Constant.OPEN);
        // SAVE TO REPO
        // fix status is empty = get All
        if(status.equalsIgnoreCase(Constant.OPEN) || status.equalsIgnoreCase(Constant.CLOSED)){
            subjects = subjectRepository.findSubjectByStatus(status,pageable);
        } else if (status.isEmpty()) {
            subjects = subjectRepository.findAll(pageable);
        }else {
            throw new RuntimeException("Invalid status.");
        }

        if(subjects.isEmpty()){
            throw new RuntimeException("There are no subjects.");
        }
        // get content for page object
        List<Subject> listOfSubjects = subjects.getContent();

        List<SubjectResponseDTO> content = listOfSubjects.stream().map(
                subject -> modelMapper.map(subject, SubjectResponseDTO.class)
        ).collect(Collectors.toList());

        SubjectResponse subjectResponse = new SubjectResponse();
        subjectResponse.setContent(content);
        subjectResponse.setTotalPage(subjects.getTotalPages());
        subjectResponse.setTotalElement(subjects.getTotalElements());
        subjectResponse.setTotalOpen(subjectListOpen.size());
        subjectResponse.setPageNo(subjects.getNumber());
        subjectResponse.setPageSize(subjects.getSize());
        subjectResponse.setLast(subjects.isLast());

        return subjectResponse;
    }



    @Override
    public SubjectResponse getAllSubjectsByMajorId(int pageNo, int pageSize, String sortBy, String sortDir, Long majorId) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        // CREATE PAGEABLE INSTANCE
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);


        // SAVE TO REPO
        Page<Subject> subjects = subjectRepository.findSubjectsByMajorId(pageable, majorId);
        // get content for page object
        List<Subject> listOfSubjects = subjects.getContent();

        List<SubjectResponseDTO> content = listOfSubjects.stream().map(
                subject -> modelMapper.map(subject, SubjectResponseDTO.class)
        ).collect(Collectors.toList());

        SubjectResponse subjectResponse = new SubjectResponse();
        subjectResponse.setContent(content);
        subjectResponse.setTotalPage(subjects.getTotalPages());
        subjectResponse.setTotalElement(subjects.getTotalElements());
        subjectResponse.setPageNo(subjects.getNumber());
        subjectResponse.setPageSize(subjects.getSize());
        subjectResponse.setLast(subjects.isLast());

        return subjectResponse;
    }


}
