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
@RequestMapping("/api/v1/major")
public class MajorController {

    @Autowired
    MajorService majorService;


    //DONE-DONE
    @GetMapping("/majors")
    public ResponseEntity<List<MajorResponseDTO>> getAllMajors (){
        List<MajorResponseDTO> majorResponseDTOList = majorService.getAllMajors();
        return new ResponseEntity<>(majorResponseDTOList, HttpStatus.OK);
    }

    //DONE-DONE
    @PostMapping("/admin/{adminId}")
    public ResponseEntity<MajorResponseDTO> createMajor(@PathVariable Long adminId, @RequestParam String majorName ){
        MajorResponseDTO majorResponseDTO = majorService.createMajor(adminId,majorName);
        return new ResponseEntity<>(majorResponseDTO, HttpStatus.CREATED);
    }

    //DONE-DONE
    @PutMapping("/admin/{adminId}")
    public ResponseEntity<MajorResponseDTO> editMajor(@PathVariable Long adminId,
                                                      @RequestBody MajorDTO majorDTO){
        MajorResponseDTO majorResponseDTO = majorService.editMajor(adminId,majorDTO);
        return new ResponseEntity<>(majorResponseDTO, HttpStatus.CREATED);
    }


}
