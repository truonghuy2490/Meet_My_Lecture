package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.MajorResponseDTO;
import com.springboot.meetMyLecturer.constant.Constant;
import com.springboot.meetMyLecturer.entity.Major;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.MajorDTO;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.MajorResponse;
import com.springboot.meetMyLecturer.repository.MajorRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.MajorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MajorServiceImpl implements MajorService {
    @Autowired
    MajorRepository majorRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;


    //create major for admin DONE-DONE
    @Override
    public MajorResponseDTO createMajor(Long adminId, String majorName) {
        User admin = userRepository.findById(adminId).orElseThrow(
                ()-> new ResourceNotFoundException("Admin","id",String.valueOf(adminId))
        );

        Major majorDB = majorRepository.findMajorByMajorName(majorName);
        if(majorDB != null){
            if(majorDB.getStatus().equals(Constant.CLOSED)){
                throw new RuntimeException("This major is disable!");
            }
            throw new RuntimeException("This major is already existed!");
        }

        Major major = new Major();
        major.setMajorName(majorName);
        major.setAdmin(admin);
        major.setStatus(Constant.OPEN);
        majorRepository.save(major);

        return modelMapper.map(major, MajorResponseDTO.class);
    }

    //edit major for admin DONE-DONE
    @Override
    public MajorResponseDTO editMajor(Long adminId, MajorDTO majorDTO) {
        User admin = userRepository.findById(adminId).orElseThrow(
                ()-> new ResourceNotFoundException("Admin","id",String.valueOf(adminId))
        );
        Major major = majorRepository.findById(majorDTO.getMajorId()).orElseThrow(
                ()-> new ResourceNotFoundException("Major","id",String.valueOf(majorDTO.getMajorId()))
        );

        if(majorDTO.getStatus().equals(Constant.OPEN)){
            major.setStatus(Constant.OPEN);
        }else if(majorDTO.getStatus().equals(Constant.CLOSED)){
            major.setStatus(Constant.CLOSED);
        }

        major.setMajorName(majorDTO.getMajorName());
        major.setAdmin(admin);
        majorRepository.save(major);

        return modelMapper.map(major, MajorResponseDTO.class);

    }

    //get all majors for admin DONE-DONE
    @Override
    public MajorResponse getAllMajors(int pageNo, int pageSize, String sortBy, String sortDir, String status) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        // CREATE PAGEABLE INSTANCE
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        // SAVE TO REPO
        if(!status.equalsIgnoreCase(Constant.OPEN) && !status.equalsIgnoreCase(Constant.CLOSED)){
            throw new RuntimeException("Invalid status.");
        }
        Page<Major> majors = majorRepository.findByStatus(status,pageable);
        if(majors.isEmpty()){
            throw new RuntimeException("There are no majors.");
        }
        // get content for page object
        List<Major> listOfMajors = majors.getContent();

        List<MajorResponseDTO> content = listOfMajors.stream().map(
                major -> modelMapper.map(major, MajorResponseDTO.class)
                ).collect(Collectors.toList());


        MajorResponse majorResponse = new MajorResponse();
        majorResponse.setContent(content);
        majorResponse.setCount(content.size());
        majorResponse.setTotalPage(majors.getTotalPages());
        majorResponse.setTotalElement(majors.getTotalElements());
        majorResponse.setPageNo(majors.getNumber());
        majorResponse.setPageSize(majors.getSize());
        majorResponse.setLast(majors.isLast());

        return majorResponse;
    }

    //get major by majorId for admin DONE-DONE
    @Override
    public MajorResponseDTO getMajorByMajorId(Long MajorId) {
        Major major = majorRepository.findById(MajorId).orElseThrow(
                () -> new ResourceNotFoundException("Major", "id", String.valueOf(MajorId))
        );

        return modelMapper.map(major, MajorResponseDTO.class);
    }
}
