package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.ReportErrorResponseDTO;
import com.springboot.meetMyLecturer.constant.Constant;
import com.springboot.meetMyLecturer.entity.ReportError;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.ReportErrorDTO;
import com.springboot.meetMyLecturer.repository.ReportErrorRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.ReportErrorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReportErrorServiceImpl implements ReportErrorService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ReportErrorRepository reportErrorRepository;

    @Autowired
    ModelMapper modelMapper;


    //create report error for user
    @Override
    public ReportErrorResponseDTO createReportError(ReportErrorDTO reportErrorDTO, Long userId){

        String unique = userRepository.findNickNameByUserId(userId, Constant.OPEN);
        if(unique == null) throw new ResourceNotFoundException("User","id", String.valueOf(userId));

        LocalDate currentDate = LocalDate.now();

        ReportError reportError = modelMapper.map(reportErrorDTO, ReportError.class);
        reportError.setCreateAt(Date.valueOf(currentDate));
        reportError.setStatus(Constant.PENDING);

        reportErrorRepository.save(reportError);

        return modelMapper.map(reportError, ReportErrorResponseDTO.class);
    }

    @Override
    public List<ReportErrorResponseDTO> getAllReportForAdmin() {

        return null;
    }
}
