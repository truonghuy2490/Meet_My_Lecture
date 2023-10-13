package com.springboot.meetMyLecturer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/lecturer")
public class LecturerController {

    @GetMapping("{id}")
    public ResponseEntity<?> getSubjectByLecId(@PathVariable int id){

        return null;
    }

}
