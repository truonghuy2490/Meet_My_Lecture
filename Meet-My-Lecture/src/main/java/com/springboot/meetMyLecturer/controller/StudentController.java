package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.ResponseDTO.EmptySlotResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectResponseDTO;
import com.springboot.meetMyLecturer.entity.SubjectLecturerStudentId;
import com.springboot.meetMyLecturer.modelDTO.BookSlotDTO;
import com.springboot.meetMyLecturer.modelDTO.SubjectLecturerStudentDTO;
import com.springboot.meetMyLecturer.service.StudentService;
import com.springboot.meetMyLecturer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    UserService userService;


    //DONE-DONE
    @GetMapping("/bookedSlot/homePage/{studentId}")
    public ResponseEntity<List<EmptySlotResponseDTO>> viewBookedSlotHomePage(@PathVariable Long studentId){
            List<EmptySlotResponseDTO> emptySlotResponseDTOS = studentService.viewBookedSlotHomePage(studentId);
            return new ResponseEntity<>(emptySlotResponseDTOS,HttpStatus.OK);
    }

    //DONE-DONE
    @GetMapping("bookedSlot/calendar/{lecturerId}")
    public ResponseEntity<List<EmptySlotResponseDTO>> viewBookedSlotCalendar(@PathVariable Long lecturerId){
        List<EmptySlotResponseDTO> emptySlotResponseDTOS = studentService.viewBookedSlotCalendar(lecturerId);
        return new ResponseEntity<>(emptySlotResponseDTOS,HttpStatus.OK);
    }


    //DONE-DONE
    @PutMapping("/emptySlot/{emptySlotId}/student/{studentId}/subject/{subjectId}")
    public ResponseEntity<EmptySlotResponseDTO> bookEmptySlot(@PathVariable Long emptySlotId,
                                                               @PathVariable Long studentId,
                                                               @RequestBody BookSlotDTO bookSlotDTO){
        EmptySlotResponseDTO emptySlotResponseDTO = studentService.bookEmptySlot(emptySlotId, studentId, bookSlotDTO);

        return new ResponseEntity<>(emptySlotResponseDTO,HttpStatus.OK);
    }

    //DONE-DONE
    @PutMapping("{studentId}/bookedSlot/{bookedSlotId}")
    public ResponseEntity<String> deleteBookedSlot (@PathVariable Long bookedSlotId,
                                                    @PathVariable Long studentId){
        String result = studentService.deleteBookedSlot(bookedSlotId,studentId);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    //DONE - DONE
    @PutMapping("/profile")
    public ResponseEntity<LecturerSubjectResponseDTO> updateSubjectsForStudent(@RequestBody SubjectLecturerStudentDTO subjectLecturerStudent) {
        LecturerSubjectResponseDTO result = userService.updateSubjectsForStudent(subjectLecturerStudent);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    //DONE-DONE
    @PostMapping("/profile/subject")
    public ResponseEntity<LecturerSubjectResponseDTO> insertSubjectsForStudent(@RequestBody SubjectLecturerStudentId subjectLecturerStudentId) {
        LecturerSubjectResponseDTO result = userService.insertSubjectsForStudent(subjectLecturerStudentId);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    //DONE-DONE
    @PutMapping("/profile/subject-deleting")
    public ResponseEntity<String> deleteSubjectsForStudent(@RequestBody SubjectLecturerStudentId subjectLecturerStudentId) {
        String result = userService.deleteSubjectsForStudent(subjectLecturerStudentId);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    //DONE-DONE
    @GetMapping("/{studentId}/subjects/lecturers")
    public ResponseEntity<List<LecturerSubjectResponseDTO>> recommendRelatedCourses(@PathVariable Long studentId) {
        List<LecturerSubjectResponseDTO> lecturerSubjectResponseDTOList = studentService.recommendRelatedCourses(studentId);
        return new ResponseEntity<>(lecturerSubjectResponseDTOList, HttpStatus.OK);
    }

}
