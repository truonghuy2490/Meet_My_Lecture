package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.ReportErrorResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.ReportErrorResponseForAdminDTO;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.ReportErrorResponse;

import java.util.List;

public interface ReportErrorService {

    ReportErrorResponseDTO createReportError(String reportError, Long userId);

    List<ReportErrorResponseForAdminDTO> getAllReportForAdmin();


    ReportErrorResponseForAdminDTO updateStatusReportForAdmin(Long reportErrorId, String status);

    List<ReportErrorResponseDTO> getReports(Long userId);

    ReportErrorResponse getAllReportError(int pageNo, int pageSize, String sortBy, String sortDir);
}
