package com.springboot.meetMyLecturer.controller;


import com.springboot.meetMyLecturer.ResponseDTO.*;
import com.springboot.meetMyLecturer.service.MeetingRequestService;
import com.springboot.meetMyLecturer.service.UserService;
import com.springboot.meetMyLecturer.service.WeeklyEmptySlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {


    @Autowired
    UserService userService;

    @Autowired
    MeetingRequestService meetingRequestService;

    @Autowired
    WeeklyEmptySlotService weeklyEmptySlotService;


    //DONE-DONE
    @GetMapping("/users")
    public ResponseEntity<List<UserProfileForAdminDTO>> getAllUsers(){
        List<UserProfileForAdminDTO> userDTOList = userService.getAllUsers();
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    //DONE-DONE
    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileForAdminDTO> viewProfileUserByUserId(@PathVariable Long userId){
        UserProfileForAdminDTO userDTO = userService.viewProfileByUserId(userId);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    //DONE-DONE
    @PutMapping("/{userId}")
    public ResponseEntity<UserProfileForAdminDTO> updateUserStatus(@PathVariable Long userId,
                                                   @RequestParam String status){
        UserProfileForAdminDTO result = userService.updateUserStatus(userId,status);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    //DONE-DONE
    @GetMapping("/weeklyEmptySlot")
    public ResponseEntity<List<WeeklyEmptySlotResponseForAdminDTO> >viewWeeklyEmptySlot(){
        List<WeeklyEmptySlotResponseForAdminDTO> weeklyEmptySlotResponseDTO = weeklyEmptySlotService.viewAllWeeks();
        return new ResponseEntity<>(weeklyEmptySlotResponseDTO, HttpStatus.OK);
    }

    //DONE-DONE
    @GetMapping("/emptySlots/weeklyEmptySlot")
    public ResponseEntity<List<EmptySlotResponseDTO>> viewEmptySlotInWeek(@RequestParam Long lecturerId, @RequestParam Long weeklyEmptySlotId){
        List<EmptySlotResponseDTO> emptySlotResponseDTOList = weeklyEmptySlotService.getEmptySlotsInWeek(lecturerId, weeklyEmptySlotId);
        return new ResponseEntity<>(emptySlotResponseDTOList, HttpStatus.OK);
    }

    //DONE-DONE
    @PutMapping("/weeklyEmptySlot/{weeklyEmptySlotId}")
    public ResponseEntity<String> updateWeeklyEmptySlotStatus(@PathVariable Long weeklyEmptySlotId,
                                                              @RequestParam String status){
        String result = weeklyEmptySlotService.updateWeeklyEmptySlotStatus(weeklyEmptySlotId, status);
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }


    //DONE-DONE
    @GetMapping("emptySlots/lecturer/{lecturerId}")
    public ResponseEntity<List<EmptySlotResponseDTO>> viewEmptySlots(@PathVariable Long lecturerId){
        List<EmptySlotResponseDTO> emptySlotResponseDTOList = userService.viewEmptySlotForAdmin(lecturerId);
        return new ResponseEntity<>(emptySlotResponseDTOList, HttpStatus.OK);
    }

}
