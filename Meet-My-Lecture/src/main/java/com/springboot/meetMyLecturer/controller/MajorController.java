package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.ResponseDTO.MajorResponseDTO;
import com.springboot.meetMyLecturer.modelDTO.MajorDTO;
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
    @GetMapping
    public ResponseEntity<List<MajorResponseDTO>> getAllMajorsForAdmin(){
        List<MajorResponseDTO> majorResponseDTOList = majorService.getAllMajors();
        return new ResponseEntity<>(majorResponseDTOList, HttpStatus.OK);
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


}
