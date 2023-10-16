package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import com.springboot.meetMyLecturer.modelDTO.UserProfileDTO;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.UserService;
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

    @GetMapping("/users")
    public ResponseEntity<List<String>> getAllUsers(){
        List<String> userDTOList = userService.getAllUsers();
        return new ResponseEntity<>(userDTOList, HttpStatus.FOUND);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> viewProfile(@RequestParam String email){
        UserProfileDTO userDTO = userService.viewProfileByEmail(email);
        return new ResponseEntity<>(userDTO, HttpStatus.FOUND);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        String result = userService.deleteUser(userId);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

}
