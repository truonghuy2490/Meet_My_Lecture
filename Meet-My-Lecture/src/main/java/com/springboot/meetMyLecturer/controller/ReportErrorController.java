package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.ResponseDTO.ReportErrorResponseDTO;
import com.springboot.meetMyLecturer.modelDTO.ReportErrorDTO;
import com.springboot.meetMyLecturer.service.ReportErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/report-error")
public class ReportErrorController {

    @Autowired
    ReportErrorService reportErrorService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<ReportErrorResponseDTO> createReportError(@PathVariable Long userId,
                                                                    @RequestBody ReportErrorDTO reportErrorDTO){
        ReportErrorResponseDTO responseDTO = reportErrorService.createReportError(reportErrorDTO, userId);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ReportErrorResponseDTO>> getAllReports(){

        List<ReportErrorResponseDTO> responseDTO = reportErrorService.getAllReportForAdmin();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
