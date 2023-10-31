package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.ResponseDTO.*;
import com.springboot.meetMyLecturer.constant.Constant;
import com.springboot.meetMyLecturer.modelDTO.UserRegister;
import com.springboot.meetMyLecturer.service.SemesterService;
import com.springboot.meetMyLecturer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    SemesterService semesterService;


    //DONE-DONE
    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileDTO> viewProfileUser(@PathVariable long userId){
        UserProfileDTO userDTO = userService.viewProfileUser(userId);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    //DONE-DONE
    @PutMapping("profile/{userId}")
    public ResponseEntity<UserProfileDTO> updateProfile(@PathVariable Long userId,
                                                      @RequestBody UserRegister userRegister) {
        UserProfileDTO userProfileDTO = userService.updateProfile(userId, userRegister);
        return new ResponseEntity<>(userProfileDTO, HttpStatus.OK);
    }

    //DONE-DONE
    @GetMapping("/emptySlot/lecturer/{lecturerId}")
    public ResponseEntity<List<EmptySlotResponseDTO>> viewEmptySlots (@PathVariable Long lecturerId){
        List<EmptySlotResponseDTO> emptySlotDTOList = userService.viewEmptySlot(lecturerId);
        return new ResponseEntity<>(emptySlotDTOList, HttpStatus.OK);
    }

    //DONE-DONE
    @GetMapping("/emptySlot/user/{userId}/semester/{semesterId}")
    public ResponseEntity<List<EmptySlotResponseForSemesterDTO>> getEmptySlotsInSemester(@PathVariable Long userId,
                                                                                         @PathVariable Long semesterId){
        List<EmptySlotResponseForSemesterDTO> emptySlotList = userService.getEmptySlotsInSemester(userId,semesterId);
        return new ResponseEntity<>(emptySlotList, HttpStatus.OK);
    }

    //DONE-DONE
    @GetMapping("/userId")
    public ResponseEntity<UserRoleResponseDTO> getUserId(){
        return new ResponseEntity<>(userService.getUserId(Constant.EMAIL), HttpStatus.OK);
    }

    //DONE-DONE
    @GetMapping("/semester")
    public ResponseEntity<List<SemesterResponseDTO>> getAllSemester(){
        List<SemesterResponseDTO> responseDTOList = semesterService.getAllSemesters();
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }
}
