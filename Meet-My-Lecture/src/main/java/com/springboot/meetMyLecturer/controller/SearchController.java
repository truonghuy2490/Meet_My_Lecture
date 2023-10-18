package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.modelDTO.SubjectResponseDTO;
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



    @GetMapping("/lecturer")
    public ResponseEntity<?> searchLecturer (@RequestParam String name){
        try{

            List<UserDTO> lecturerList = studentService.searchLecturers(name);

            if(!lecturerList.isEmpty()){
                return ResponseEntity.ok().body(lecturerList);
            }else{
                return  ResponseEntity.notFound().build();
            }

        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Error at:" + e.getMessage());
        }
    }

    @GetMapping("/subject/lecturer/{id}")
    public ResponseEntity<List<SubjectResponseDTO>> getSubjectByLecturerId (@PathVariable Long id){
        List<SubjectResponseDTO> subjectResponseDTOS = subjectService.getSubjectByLecturerId(id);
        return new ResponseEntity<>(subjectResponseDTOS,HttpStatus.FOUND);
    }

}
