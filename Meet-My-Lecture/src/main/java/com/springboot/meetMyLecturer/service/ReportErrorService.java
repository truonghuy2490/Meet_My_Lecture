package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.ReportErrorResponseDTO;
import com.springboot.meetMyLecturer.modelDTO.ReportErrorDTO;

import java.util.List;

public interface ReportErrorService {

    ReportErrorResponseDTO createReportError(ReportErrorDTO reportErrorDTO, Long userId);

    List<ReportErrorResponseDTO> getAllReportForAdmin();

}
