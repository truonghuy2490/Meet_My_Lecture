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
import java.util.stream.Stream;

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
                    if(lecturerList.isEmpty()){
                        LecturerSubjectResponseDTO dto = new LecturerSubjectResponseDTO();
                        dto.setSubjectId(subject.getSubjectId());
                        return Stream.of(dto);
                    }
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

    //get subjects by majorId for student and lecturer DONE - DONE
    @Override
    public List<LecturerSubjectResponseDTO> getSubjectsByMajorId(Long majorId) {
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
                    if(lecturerList.isEmpty()){
                        LecturerSubjectResponseDTO lecturerSubjectResponseDTO = new LecturerSubjectResponseDTO();
                        lecturerSubjectResponseDTO.setSubjectId(subject.getSubjectId());
                        return Stream.of(lecturerSubjectResponseDTO);
                    }
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

    // get subject by subjectId for student DONE-DONE
    @Override
    public SubjectResponseDTO getSubjectBySubjectId(String subjectId) {
        Subject subject = subjectRepository.findSubjectBySubjectIdAndStatus(subjectId, Constant.OPEN);

        if(subject == null) throw new ResourceNotFoundException("Subject","id",subjectId);
        return modelMapper.map(subject, SubjectResponseDTO.class);
    }

    //get all subjects for admin DONE-DONE
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

        List<SubjectResponseForAdminDTO> content = listOfSubjects.stream().map(
                subject -> {
                    SubjectResponseForAdminDTO subjectResponseDTO = modelMapper.map(subject, SubjectResponseForAdminDTO.class);
                    Set<Major> major = subjectRepository.getMajorNamesForSubject(subject.getSubjectId());
                    Set<MajorResponseDTO> majorResponseDTOSet = major.stream().map(
                            m -> modelMapper.map(m, MajorResponseDTO.class)
                    ).collect(Collectors.toSet());
                    subjectResponseDTO.setMajor(majorResponseDTOSet);
                    return subjectResponseDTO;
                }
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



    //get all subject by majorId for admin DONE-DONE
    @Override
    public SubjectResponse getAllSubjectsByMajorId(int pageNo, int pageSize, String sortBy, String sortDir, Long majorId, String status) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        // CREATE PAGEABLE INSTANCE
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Subject> subjects;
        if(status.equalsIgnoreCase(Constant.OPEN) || status.equalsIgnoreCase(Constant.CLOSED)){
            subjects = subjectRepository.findSubjectsByMajorIdAndStatus(pageable, majorId, status);
        } else if (status.isEmpty()) {
            subjects = subjectRepository.findSubjectsByMajorIdAndStatus(pageable, majorId);
        }else {
            throw new RuntimeException("Invalid status.");
        }


        // SAVE TO REPO
        // get content for page object
        List<Subject> listOfSubjects = subjects.getContent();

        List<SubjectResponseForAdminDTO> content = listOfSubjects.stream().map(
                subject -> {
                    SubjectResponseForAdminDTO subjectResponseDTO = modelMapper.map(subject, SubjectResponseForAdminDTO.class);
                    Set<Major> major = subjectRepository.getMajorNamesForSubject(subject.getSubjectId());
                    Set<MajorResponseDTO> majorResponseDTOSet = major.stream().map(
                            m -> modelMapper.map(m, MajorResponseDTO.class)
                    ).collect(Collectors.toSet());
                    subjectResponseDTO.setMajor(majorResponseDTOSet);
                    return subjectResponseDTO;
                }
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

    //delete subject in major for admin DONE-DONE
    @Override
    public String deleteSubjectInMajor(Long adminId, SubjectMajorId subjectMajorId) {

        User admin = userRepository.findById(adminId).orElseThrow(
                ()-> new ResourceNotFoundException("Admin","id",String.valueOf(adminId))
        );

        SubjectMajor subjectMajor = subjectMajorRepository.findSubjectMajorBySubjectMajorId(subjectMajorId);
        if(subjectMajor == null) throw new RuntimeException("This subject in this major is not existed.");

        subjectMajor.setStatus(Constant.CLOSED);

        return "This subject in this major is deleted.";
    }

    //get lecturers and majors by subjectId for admin
    @Override
    public LecturersMajorsResponseDTO getLecturersAndMajorsBySubjectId(String subjectId) {

        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                ()-> new ResourceNotFoundException("Subject","id", subjectId)
        );
        List<User> lecturer = subjectRepository.findLecturerBySubjectId(subjectId);

        Set<UserProfileDTO> lecturerProfile = new HashSet<>();

        for (User user : lecturer) {
            UserProfileDTO userProfileDTO = modelMapper.map(user, UserProfileDTO.class);
            lecturerProfile.add(userProfileDTO);
        }


        Set<Major> majorName = majorRepository.findMajorNameBySubjectId(subjectId);
        Set<MajorResponseDTO> majorResponseDTO = majorName.stream().map(major -> modelMapper.map(major, MajorResponseDTO.class)).collect(Collectors.toSet());

        LecturersMajorsResponseDTO lecturersMajorsResponseDTO = new LecturersMajorsResponseDTO();
        lecturersMajorsResponseDTO.setLecturerSet(lecturerProfile);
        lecturersMajorsResponseDTO.setMajorSet(majorResponseDTO);
        lecturersMajorsResponseDTO.setSubjectId(subjectId);
        lecturersMajorsResponseDTO.setSubjectName(subject.getSubjectName());


        return lecturersMajorsResponseDTO;
    }

    // search subject for admin DONE-DONE
    @Override
    public List<SubjectResponseDTO> searchSubjectForAdmin(String subjectId, String subjectName) {

        List<Subject> subjectList;

        if(subjectId.isEmpty()){
            subjectList = subjectRepository.findSubjectByName(subjectName);
        }else if(subjectName.isEmpty()){
            subjectList = subjectRepository.findSubjectById(subjectId);
        }else{
            subjectList = null;
        }

        if (subjectList == null) throw new RuntimeException("There are no subjects");
        return subjectList.stream().map(
                subject -> modelMapper.map(subject, SubjectResponseDTO.class)
        ).collect(Collectors.toList());
    }

}
