package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.ResponseDTO.UserRegisterResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.UserProfileDTO;
import com.springboot.meetMyLecturer.modelDTO.UserRegister;
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


    //DONE
    @PostMapping("/register/{roleId}")
    public ResponseEntity<UserRegisterResponseDTO> registerUser (@PathVariable Long roleId, @RequestBody UserRegister userRegister){
        UserRegisterResponseDTO userRegisterResponseDTO = userService.registerUser(roleId,userRegister);
        return new  ResponseEntity<>(userRegisterResponseDTO, HttpStatus.CREATED);
    }

    //DONE
    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileDTO> viewProfileUser(@PathVariable long userId){
        UserProfileDTO userDTO = userService.viewProfileUser(userId);
        return new ResponseEntity<>(userDTO, HttpStatus.FOUND);
    }



    //DONE
    @PutMapping("profile/{studentId}/major/{majorId}")
    public ResponseEntity<UserProfileDTO> editProfile(@PathVariable Long studentId,
                                                      @RequestBody UserRegister userRegister) {
        UserProfileDTO userProfileDTO = userService.updateProfileForStudent(studentId, userRegister);
        return new ResponseEntity<>(userProfileDTO, HttpStatus.OK);
    }



}
