package com.springboot.meetMyLecturer.controller;


import com.springboot.meetMyLecturer.ResponseDTO.UserProfileDTO;
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

    //DONE
    @GetMapping("/users")
    public ResponseEntity<List<UserProfileDTO>> getAllUsers(){
        List<UserProfileDTO> userDTOList = userService.getAllUsers();
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    //DONE
    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileDTO> viewProfileUserByUserId(@PathVariable Long userId){
        UserProfileDTO userDTO = userService.viewProfileByUserId(userId);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    //DONE
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        String result = userService.deleteUser(userId);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    

}
