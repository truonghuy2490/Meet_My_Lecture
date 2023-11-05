package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.ResponseDTO.*;
import com.springboot.meetMyLecturer.entity.SubjectLecturerStudentId;
import com.springboot.meetMyLecturer.modelDTO.BookSlotDTO;
import com.springboot.meetMyLecturer.modelDTO.SubjectDTO;
import com.springboot.meetMyLecturer.modelDTO.SubjectForAminDTO;
import com.springboot.meetMyLecturer.modelDTO.SubjectLecturerStudentDTO;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.StudentService;
import com.springboot.meetMyLecturer.service.SubjectService;
import com.springboot.meetMyLecturer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subject/admin")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    //DONE-DONE
//    @GetMapping
//    public ResponseEntity<List<SubjectMajorResponseDTO>> getAllSubjects() {
//        List<SubjectMajorResponseDTO> subjectList = subjectService.getAllSubjects();
//        return new ResponseEntity<>(subjectList, HttpStatus.OK);
//    }

    //DONE-DONE
    @PostMapping("/{adminId}")
    public ResponseEntity<SubjectMajorResponseForAdminDTO> createSubject(@PathVariable Long adminId,
                                                            @RequestBody SubjectForAminDTO subjectDTO) {
        SubjectMajorResponseForAdminDTO subjectResponseDTO = subjectService.createSubject(adminId, subjectDTO);
        return new ResponseEntity<>(subjectResponseDTO, HttpStatus.CREATED);
    }

    //DONE-DONE
    @PutMapping("/{adminId}")
    public ResponseEntity<SubjectResponseDTO> editSubjectsInMajor(@PathVariable Long adminId,
                                                                  @RequestParam String subjectId,
                                                                  @RequestParam Long majorId) {
        SubjectResponseDTO subject = subjectService.editSubjectsInMajor(adminId, subjectId, majorId);

        return new ResponseEntity<>(subject, HttpStatus.OK);
    }


}