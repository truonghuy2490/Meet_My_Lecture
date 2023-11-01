package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.ResponseDTO.ReportErrorResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.ReportErrorResponseForAdminDTO;
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
                                                                    @RequestParam String reportError){
        ReportErrorResponseDTO responseDTO = reportErrorService.createReportError(reportError, userId);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    //DONE-DONE
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReportErrorResponseDTO>> getReports(@PathVariable Long userId){
        List<ReportErrorResponseDTO> responseDTO = reportErrorService.getReports(userId);
        return  new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    //DONE-DONE
    @GetMapping("/admin")
    public ResponseEntity<List<ReportErrorResponseForAdminDTO>> getAllReports(){
        List<ReportErrorResponseForAdminDTO> responseDTO = reportErrorService.getAllReportForAdmin();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    //DONE-DONE
    @PutMapping("/{reportErrorId}/admin")
    public ResponseEntity<ReportErrorResponseForAdminDTO> updateStatusReportFroAdmin(@PathVariable Long reportErrorId,
                                                                                     @RequestParam String status){
        ReportErrorResponseForAdminDTO response = reportErrorService.updateStatusReportForAdmin(reportErrorId, status);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
