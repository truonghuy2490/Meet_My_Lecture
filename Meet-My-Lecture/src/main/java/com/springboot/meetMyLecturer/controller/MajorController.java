package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.ResponseDTO.MajorResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectResponseForAdminDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectsInMajorResponseDTO;
import com.springboot.meetMyLecturer.constant.PageConstant;
import com.springboot.meetMyLecturer.modelDTO.MajorDTO;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.MajorResponse;
import com.springboot.meetMyLecturer.modelDTO.SubjectMajorDTO;
import com.springboot.meetMyLecturer.modelDTO.SubjectSemesterDTO;
import com.springboot.meetMyLecturer.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/major/admin")
public class MajorController {

    @Autowired
    MajorService majorService;

    //DONE-DONE
    @GetMapping("majors")
    public MajorResponse getAllMajors(
            @RequestParam(value = "pageNo", defaultValue = PageConstant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = PageConstant.DEFAULT_PAGE_SIZE, required = false)int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "majorName", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = PageConstant.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            @RequestParam(value = "status", defaultValue = "",required = false) String status
    ){
        return majorService.getAllMajors(pageNo, pageSize, sortBy, sortDir, status);
    }

    //DONE-DONE
    @GetMapping("major/{majorId}")
    public ResponseEntity<MajorResponseDTO> getMajorByMajorId(@PathVariable Long majorId){
        MajorResponseDTO major = majorService.getMajorByMajorId(majorId);
        return new ResponseEntity<>(major, HttpStatus.OK);
    }

    //DONE-DONE
    @PostMapping("/{adminId}")
    public ResponseEntity<MajorResponseDTO> createMajorForAdmin(@PathVariable Long adminId, @RequestParam String majorName ){
        MajorResponseDTO majorResponseDTO = majorService.createMajor(adminId,majorName);
        return new ResponseEntity<>(majorResponseDTO, HttpStatus.CREATED);
    }

    //DONE-DONE
    @PutMapping("/{adminId}")
    public ResponseEntity<MajorResponseDTO> editMajorForAdmin(@PathVariable Long adminId,
                                                      @RequestBody MajorDTO majorDTO){
        MajorResponseDTO majorResponseDTO = majorService.editMajor(adminId,majorDTO);
        return new ResponseEntity<>(majorResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("searching")
    public ResponseEntity<List<MajorResponseDTO>> searchMajor(@RequestParam(defaultValue = "", required = false) String majorName){
        List<MajorResponseDTO> majorResponseDTO = majorService.searchMajor(majorName);
        return new ResponseEntity<>(majorResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/{adminId}/subjects")
    public ResponseEntity<SubjectsInMajorResponseDTO> insertSubjectsIntoMajor(@PathVariable Long adminId,
                                                                              @RequestBody SubjectMajorDTO subjectMajorDTO){
        SubjectsInMajorResponseDTO responseDTO = majorService.insertSubjectsIntoMajor(adminId, subjectMajorDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
