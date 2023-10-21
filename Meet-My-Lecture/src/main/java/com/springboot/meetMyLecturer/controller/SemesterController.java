package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.modelDTO.SemesterDTO;
import com.springboot.meetMyLecturer.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/semester")
public class SemesterController {
    @Autowired
    SemesterService semesterService;


    @GetMapping
    public List<SemesterDTO> getAllSeme(){
        return semesterService.getAllSemester();
    }
    @PostMapping("admin/{adminId}")
    public ResponseEntity<SemesterDTO> createSemester(
            @PathVariable Long adminId,
            @RequestBody SemesterDTO semesterDTO
    ){
        SemesterDTO responseSemester = semesterService.createSemester(adminId, semesterDTO);
        return new ResponseEntity<>(responseSemester, HttpStatus.CREATED);
    }
}
