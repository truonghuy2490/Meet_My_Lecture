package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.ResponseDTO.EmptySlotResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectResponseDTO;
import com.springboot.meetMyLecturer.entity.SubjectLecturerStudentId;
import com.springboot.meetMyLecturer.modelDTO.BookSlotDTO;
import com.springboot.meetMyLecturer.modelDTO.SubjectLecturerStudentDTO;
import com.springboot.meetMyLecturer.service.EmptySlotService;
import com.springboot.meetMyLecturer.service.StudentService;
import com.springboot.meetMyLecturer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    @Autowired
    StudentService studentService;
    @Autowired
    EmptySlotService slotService;

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
    public ResponseEntity<List<LecturerSubjectResponseDTO>> updateSubjectsForStudent(@RequestBody Set<SubjectLecturerStudentDTO> subjectLecturerStudent) {
        List<LecturerSubjectResponseDTO> result = userService.updateSubjectsForStudent(subjectLecturerStudent);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    //DONE-DONE
    @PostMapping("/profile/subject")
    public ResponseEntity<List<LecturerSubjectResponseDTO>> insertSubjectsForStudent(@RequestBody Set<SubjectLecturerStudentId> subjectLecturerStudentId) {
        List<LecturerSubjectResponseDTO> result = userService.insertSubjectsForStudent(subjectLecturerStudentId);
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

    //DONE-DONE
    @PutMapping("/{studentId}/major/{majorId}")
    public ResponseEntity<Long> chooseMajor(@PathVariable Long studentId, @PathVariable Long majorId){
        Long response = studentService.chooseMajor(studentId, majorId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("{studentId}/updating/bookedSlot/{bookedSlotId}")
    public ResponseEntity<EmptySlotResponseDTO> updateBookedSlot(@PathVariable Long studentId,
                                                                 @PathVariable Long bookedSlotId,
                                                                 @RequestParam String subjectId,
                                                                 @RequestParam String description){

        EmptySlotResponseDTO emptySlotResponseDTO = studentService.updateBookedSlot(studentId, bookedSlotId, subjectId, description);

        return new ResponseEntity<>(emptySlotResponseDTO, HttpStatus.OK);

    }

    @GetMapping("/subjects/lecturer/{lecturerId}")
    public ResponseEntity<List<SubjectResponseDTO>> getSubjectsOfLecturer(@PathVariable Long lecturerId){
        List<SubjectResponseDTO> responseDTOList = slotService.getSubjectsOfLecturer(lecturerId);
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }


}
