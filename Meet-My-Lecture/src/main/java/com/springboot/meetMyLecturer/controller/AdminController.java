package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;
import com.springboot.meetMyLecturer.modelDTO.UserProfileDTO;
import com.springboot.meetMyLecturer.service.MeetingRequestService;
import com.springboot.meetMyLecturer.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {


    @Autowired
    UserServiceImpl userService;

    @Autowired
    MeetingRequestService meetingRequestService;

    @GetMapping("/users")
    public ResponseEntity<List<String>> getAllUsers(){
        List<String> userDTOList = userService.getAllUsers();
        return new ResponseEntity<>(userDTOList, HttpStatus.FOUND);
    }

    @GetMapping("/user-account")
    public ResponseEntity<List<String>> getUserAccount(@RequestParam String userName){
    return null;
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileDTO> viewProfileUserByUserId(@PathVariable Long userId){
        UserProfileDTO userDTO = userService.viewProfileByUserId(userId);
        return new ResponseEntity<>(userDTO, HttpStatus.FOUND);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        String result = userService.deleteUser(userId);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/requests")
    public ResponseEntity<List<MeetingRequestDTO>> getAllRequests(){
        List<MeetingRequestDTO> meetingRequestDTOList = meetingRequestService.getAllRequests();
        return new ResponseEntity<>(meetingRequestDTOList, HttpStatus.FOUND);
    }


}
