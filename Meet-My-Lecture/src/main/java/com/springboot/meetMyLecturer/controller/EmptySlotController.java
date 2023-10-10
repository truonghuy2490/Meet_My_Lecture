package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.service.EmptySlotService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/empty-slots")
public class EmptySlotController {
    private EmptySlotService emptySlotService;

    public EmptySlotController(EmptySlotService emptySlotService) {
        this.emptySlotService = emptySlotService;
    }
    // GET
    // PUT
    // POST
    // DELETE
}
