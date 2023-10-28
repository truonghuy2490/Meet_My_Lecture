package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.MajorResponseDTO;
import com.springboot.meetMyLecturer.constant.Constant;
import com.springboot.meetMyLecturer.entity.Major;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.MajorDTO;
import com.springboot.meetMyLecturer.repository.MajorRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.MajorService;
import com.sun.source.tree.ModuleTree;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    //get all majors for admin DONE-DONE
    @Override
    public List<MajorResponseDTO> getAllMajors() {
        List<Major> majorList = majorRepository.findAll();
        if(majorList.isEmpty()){
            throw  new RuntimeException("There are no majors");
        }
        return majorList.stream().map(major -> modelMapper.map(major, MajorResponseDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<MajorResponseDTO> getAllMajorsForAdmin() {
        return null;
    }

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
}
