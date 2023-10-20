package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import com.springboot.meetMyLecturer.ResponseDTO.UserProfileDTO;
import com.springboot.meetMyLecturer.repository.RoleRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public UserService userService;

    @Autowired
    public RoleRepository roleRepository;


    @PostMapping("/register/{roleId}")
    public ResponseEntity<UserDTO> registerUser (@PathVariable Long roleId, @RequestBody User userRegister){
        UserDTO userDTO = userService.registerUser(roleId,userRegister);

        return new  ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    //DONE
    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileDTO> viewProfileUser(@PathVariable long userId){
        UserProfileDTO userDTO = userService.viewProfileUser(userId);
        return new ResponseEntity<>(userDTO, HttpStatus.FOUND);
    }

    //
    @PutMapping("profile/{studentId}/major/{majorId}")
    public ResponseEntity<UserProfileDTO> editProfile(@PathVariable Long studentId, @PathVariable Long majorId, @RequestBody UserDTO userDTO,
                                                      @RequestParam String subjectId,
                                                      @RequestParam Long lecturerId) {
        UserProfileDTO userProfileDTO = userService.updateProfileForStudent(studentId,majorId, userDTO, subjectId, lecturerId);
        return new ResponseEntity<>(userProfileDTO, HttpStatus.OK);
    }

}
