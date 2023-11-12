package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.ResponseDTO.SemesterResponseDTO;
import com.springboot.meetMyLecturer.constant.PageConstant;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.SemesterResponse;
import com.springboot.meetMyLecturer.modelDTO.SemesterDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectSemesterResponseDTO;
import com.springboot.meetMyLecturer.modelDTO.SubjectSemesterDTO;
import com.springboot.meetMyLecturer.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/semester/admin")
public class SemesterController {

    @Autowired
    SemesterService semesterService;

    //DONE-DONE
    @GetMapping("semesters")
    public SemesterResponse getAllSemester(
            @RequestParam(value = "pageNo", defaultValue = PageConstant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = PageConstant.DEFAULT_PAGE_SIZE, required = false)int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "semesterName", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = PageConstant.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            @RequestParam(defaultValue = "",required = false) String status
    ){
        return semesterService.getAllSemesters(pageNo, pageSize, sortBy, sortDir, status);
    }

    //DONE-DONE
    @GetMapping("{semesterId}")
    public ResponseEntity<SemesterResponseDTO> getSemesterInfo(@PathVariable Long semesterId){
        SemesterResponseDTO semesterResponseDTO = semesterService.getSemesterInfo(semesterId);
        return new ResponseEntity<>(semesterResponseDTO, HttpStatus.OK);
    }


    //DONE-DONE
    @PostMapping("/admin/{adminId}")
    public ResponseEntity<SemesterResponseDTO> createSemester(@PathVariable Long adminId,
                                                              @RequestBody SemesterDTO semesterDTO){
        SemesterResponseDTO responseDTO = semesterService.createSemester(adminId, semesterDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    //DONE-DONE
    @PutMapping("/{semesterId}/admin/{adminId}")
    public ResponseEntity<SemesterResponseDTO> editSemester(@PathVariable Long adminId,@PathVariable Long semesterId,@RequestBody SemesterDTO semesterDTO){
        SemesterResponseDTO responseDTO = semesterService.editSemester(adminId,semesterId, semesterDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    //DONE-DONE
    @PutMapping("/{semesterId}/admin/{adminId}/deleting")
    public ResponseEntity<String> deleteSemester(@PathVariable Long adminId, @PathVariable Long semesterId){
        String result = semesterService.deleteSemester(adminId, semesterId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/{adminId}/subjects")
    public ResponseEntity<SubjectSemesterResponseDTO> insertSubjectIntoSemester(@PathVariable Long adminId,
                                                                                      @RequestBody SubjectSemesterDTO semesterDTO){
        SubjectSemesterResponseDTO responseList = semesterService.insertSubjectIntoSemester(adminId,semesterDTO);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }


    
}
