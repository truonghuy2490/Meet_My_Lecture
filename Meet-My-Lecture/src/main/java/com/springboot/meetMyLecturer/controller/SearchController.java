package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectResponseDTO;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import com.springboot.meetMyLecturer.service.StudentService;
import com.springboot.meetMyLecturer.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student/search")
public class SearchController {

    @Autowired
    StudentService studentService;

    @Autowired
    SubjectService subjectService;



    //DONE
    @GetMapping("/lecturer")
    public ResponseEntity<List<LecturerSubjectDTO>> searchLecturer (@RequestParam String name){

            List<LecturerSubjectDTO> lecturerSubjectDTOList = studentService.searchLecturers(name);
             return new ResponseEntity<>(lecturerSubjectDTOList,HttpStatus.FOUND);
    }

}
