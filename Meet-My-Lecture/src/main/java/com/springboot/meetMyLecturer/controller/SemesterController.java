package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.ResponseDTO.MajorResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SemesterResponseDTO;
import com.springboot.meetMyLecturer.service.MajorService;
import com.springboot.meetMyLecturer.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/semester/admin")
public class SemesterController {

    @Autowired
    SemesterService semesterService;

    //DONE-DONE
    @GetMapping
    public ResponseEntity<List<SemesterResponseDTO>> getAllMajorsForAdmin(){
        List<SemesterResponseDTO> majorList = semesterService.getAllSemestersForAdmin();
        return new ResponseEntity<>(majorList, HttpStatus.OK);
    }

}
