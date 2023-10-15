package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import com.springboot.meetMyLecturer.modelDTO.UserProfileDTO;
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


    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestParam long id){
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register/{roleId}")
    public ResponseEntity<UserDTO> registerUser (@PathVariable(value = "roleId") int roleId, @RequestBody User userRegister){


        return new  ResponseEntity<>(userService.registerUser(roleId,userRegister), HttpStatus.CREATED);
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileDTO> viewProfile(@PathVariable long userId){
        UserProfileDTO userDTO = userService.viewProfile(userId);
        return new ResponseEntity<>(userDTO, HttpStatus.FOUND);
    }

    @PutMapping("profile/{userId}/major/{majorId}")
    public ResponseEntity<UserProfileDTO> editProfile(@PathVariable long userId,@PathVariable int majorId, @RequestBody User user){
        UserProfileDTO userProfileDTO = userService.updateProfile(userId,majorId, user);
        return new ResponseEntity<>(userProfileDTO, HttpStatus.OK);
    }

}
