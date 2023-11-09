package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.ReportErrorResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.ReportErrorResponseForAdminDTO;
import com.springboot.meetMyLecturer.constant.Constant;
import com.springboot.meetMyLecturer.entity.ReportError;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.ReportErrorResponse;
import com.springboot.meetMyLecturer.repository.ReportErrorRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.ReportErrorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportErrorServiceImpl implements ReportErrorService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ReportErrorRepository reportErrorRepository;

    @Autowired
    ModelMapper modelMapper;


    //create report error for user DONE-DONE
    @Override
    public ReportErrorResponseDTO createReportError(String reportError, Long userId){

        User user = userRepository.findUserByUserIdAndStatus(userId, Constant.OPEN);
        if(user == null) throw new ResourceNotFoundException("User","id", String.valueOf(userId));

        LocalDate currentDate = LocalDate.now();

        ReportError reportErrorDB = new ReportError();
        reportErrorDB.setUser(user);
        reportErrorDB.setReportErrorContent(reportError);
        reportErrorDB.setCreateAt(Date.valueOf(currentDate));
        reportErrorDB.setStatus(Constant.PENDING);

        reportErrorRepository.save(reportErrorDB);

        return modelMapper.map(reportErrorDB, ReportErrorResponseDTO.class);
    }

    //get all reports for admin DONE-DONE
    @Override
    public List<ReportErrorResponseForAdminDTO> getAllReportForAdmin() {
        List<Long> reportErrorIdList = reportErrorRepository.getReportErrorId();
        if(reportErrorIdList.isEmpty()) throw new RuntimeException("There are no reports.");

        return reportErrorIdList.stream()
                .map(id -> {
                    ReportErrorResponseForAdminDTO responseForAdminDTO = new ReportErrorResponseForAdminDTO();

                    String reportErrorContent = reportErrorRepository.getReportContent(id);
                    String status = reportErrorRepository.getStatus(id);
                    Date createAt = reportErrorRepository.getCreateAt(id);
                    Long userId = reportErrorRepository.getUserId(id);
                    String unique = userRepository.findUniqueByUserId(userId);

                    responseForAdminDTO.setReportErrorId(id);
                    responseForAdminDTO.setUnique(unique);
                    responseForAdminDTO.setCreateAt(createAt);
                    responseForAdminDTO.setStatus(status);
                    responseForAdminDTO.setReportErrorContent(reportErrorContent);

                    return responseForAdminDTO;
                })
                .collect(Collectors.toList());
    }

    //update status report for admin DONE-DONE
    @Override
    public ReportErrorResponseForAdminDTO updateStatusReportForAdmin(Long reportErrorId, String status) {
        ReportError reportError = reportErrorRepository.findById(reportErrorId).orElseThrow(
                ()-> new ResourceNotFoundException("Report","id",String.valueOf(reportErrorId))
        );

        if(status.equalsIgnoreCase("DONE")){
            reportError.setStatus("DONE");
        }

        ReportErrorResponseForAdminDTO response = modelMapper.map(reportError, ReportErrorResponseForAdminDTO.class);
        response.setUnique(reportError.getUser().getUnique());
        return response;
    }

    //get reports for user DONE-DONE
    @Override
    public List<ReportErrorResponseDTO> getReports(Long userId) {
        List<Long> reportErrorIdList = reportErrorRepository.getReportErrorByUserId(userId);

        return reportErrorIdList.stream()
                .map(id -> {
                    ReportErrorResponseDTO responseDTO = new ReportErrorResponseDTO();

                    String reportErrorContent = reportErrorRepository.getReportContent(id);
                    String status = reportErrorRepository.getStatus(id);
                    String userName = userRepository.findUserNameByUserId(userId);


                    responseDTO.setUserName(userName);
                    responseDTO.setStatus(status);
                    responseDTO.setReportErrorContent(reportErrorContent);

                    return responseDTO;
                })
                .collect(Collectors.toList());
    }

    //get all report for admin DONE-DONE
    @Override
    public ReportErrorResponse getAllReportError(int pageNo, int pageSize, String sortBy, String sortDir, String status) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        // CREATE PAGEABLE INSTANCE
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        // SAVE TO REPO
        if(!status.equalsIgnoreCase(Constant.PENDING) && !status.equalsIgnoreCase("DONE")){
            throw new RuntimeException("Invalid status.");
        }
        Page<ReportError> reportErrors = reportErrorRepository.findByStatus(status, pageable); // findAllByStudentId()
        if(reportErrors.isEmpty()){
            throw new RuntimeException("There are no report errors.");
        }

        // get content for page object
        List<ReportError> listOfReportErrors = reportErrors.getContent();

        List<ReportErrorResponseDTO> content = listOfReportErrors.stream().map(
                reportError -> modelMapper.map(reportError, ReportErrorResponseDTO.class)
        ).collect(Collectors.toList());


        ReportErrorResponse reportErrorResponse = new ReportErrorResponse();
        reportErrorResponse.setContent(content);
        reportErrorResponse.setTotalPage(reportErrors.getTotalPages());
        reportErrorResponse.setTotalElement(reportErrors.getTotalElements());
        reportErrorResponse.setPageNo(reportErrors.getNumber());
        reportErrorResponse.setPageSize(reportErrors.getSize());
        reportErrorResponse.setLast(reportErrors.isLast());

        return reportErrorResponse;
    }
}
