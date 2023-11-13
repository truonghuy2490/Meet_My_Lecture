package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.MajorResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectResponseTwoFieldDTO;
import com.springboot.meetMyLecturer.service.MajorService;
import com.springboot.meetMyLecturer.service.StudentService;
import com.springboot.meetMyLecturer.service.SubjectService;
import com.springboot.meetMyLecturer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/student/searching")
public class SearchController {

    @Autowired
    StudentService studentService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    UserService userService;

    @Autowired
    MajorService majorService;

    //DONE-DONE
    @GetMapping("/lecturer")
    public ResponseEntity<List<LecturerSubjectResponseDTO>> searchLecturerByLecturerName(@RequestParam String name){

            List<LecturerSubjectResponseDTO> lecturerSubjectResponseDTOList = studentService.searchLecturers(name);
             return new ResponseEntity<>(lecturerSubjectResponseDTOList,HttpStatus.OK);
    }

    //DONE-DONE
    @GetMapping("/subject")
    public ResponseEntity<List<LecturerSubjectResponseDTO> > searchSubject(@RequestParam String keyword){
        List<LecturerSubjectResponseDTO> subjectList = subjectService.searchSubject(keyword);
        return new ResponseEntity<>(subjectList, HttpStatus.OK);
    }

    //DONE-DONE
    @GetMapping("subject/major/{majorId}")
    public ResponseEntity<List<LecturerSubjectResponseDTO>> getSubjectsByMajorId(@PathVariable Long majorId){
        List<LecturerSubjectResponseDTO> lecturerSubjectResponseDTOList = subjectService.getSubjectsByMajorId(majorId);
        return new ResponseEntity<>(lecturerSubjectResponseDTOList,HttpStatus.OK);
    }

    //DONE-DONE
    @GetMapping("/majors")
    public ResponseEntity<List<MajorResponseDTO>> getAllMajors (){
        List<MajorResponseDTO> majorResponseDTOList = userService.getAllMajors();
        return new ResponseEntity<>(majorResponseDTOList, HttpStatus.OK);
    }

    //DONE-DONE
    @GetMapping("/{subjectId}/subjectName")
    public ResponseEntity<SubjectResponseDTO> getSubject(@PathVariable String subjectId) {
        SubjectResponseDTO subjectResponseDTO = subjectService.getSubjectBySubjectId(subjectId);
        return new ResponseEntity<>(subjectResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/subjects/major/{majorId}")
    public ResponseEntity<Set<SubjectResponseTwoFieldDTO>> getSubjectsByMajorIdForLec(@PathVariable Long majorId){
        Set<SubjectResponseTwoFieldDTO> response = subjectService.getSubjectsByMajorIdForLec(majorId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
