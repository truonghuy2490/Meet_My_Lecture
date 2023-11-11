package com.springboot.meetMyLecturer.controller;


import com.springboot.meetMyLecturer.ResponseDTO.*;
import com.springboot.meetMyLecturer.constant.PageConstant;
import com.springboot.meetMyLecturer.entity.Room;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.*;
import com.springboot.meetMyLecturer.service.*;
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
    EmptySlotService slotService;

    @Autowired
    MeetingRequestService meetingRequestService;

    @Autowired
    WeeklyEmptySlotService weeklyEmptySlotService;

    @Autowired
    SubjectService  subjectService;

    @Autowired
    SemesterService semesterService;

    @Autowired
    MajorService majorService;

    @Autowired
    ReportErrorService reportErrorService;

    @Autowired
    RoomService roomService;


    //DONE-DONE
    @GetMapping("user")
    public UserResponse getAllUsers(
            @RequestParam(value = "pageNo", defaultValue = PageConstant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = PageConstant.DEFAULT_PAGE_SIZE, required = false)int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "userName", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = PageConstant.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            @RequestParam(value = "status", defaultValue = "", required = false) String status
            ){
        return userService.getAllUsers(pageNo, pageSize, sortBy, sortDir, status);
    }

    //DONE-DONE
    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileForAdminDTO> viewProfileUserByUserId(@PathVariable Long userId){
        UserProfileForAdminDTO userDTO = userService.viewProfileByUserId(userId);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    //DONE-DONE
    @PutMapping("/{userId}")
    public ResponseEntity<UserProfileForAdminDTO> updateUserStatus(
            @PathVariable Long userId,
            @RequestParam String status){
        UserProfileForAdminDTO result = userService.updateUserStatus(userId,status);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    //DONE-DONE
    @GetMapping("weeklyEmptySlot")
    public SlotResponse getAllSlots(
            @RequestParam(value = "pageNo", defaultValue = PageConstant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = PageConstant.DEFAULT_PAGE_SIZE, required = false)int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "timeStart", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = PageConstant.DEFAULT_SORT_DIRECTION_DECS, required = false) String sortDir
    ){
        return slotService.getAllSlot(pageNo, pageSize,sortBy,sortDir);
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
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    //DONE-DONE
    @GetMapping("emptySlots/lecturer/{lecturerId}")
    public SlotResponse viewEmptySlots(
            @RequestParam(value = "pageNo", defaultValue = PageConstant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = PageConstant.DEFAULT_PAGE_SIZE, required = false)int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "timeStart", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = PageConstant.DEFAULT_SORT_DIRECTION_DECS, required = false) String sortDir,
            @PathVariable Long lecturerId,
            @RequestParam(value = "filter", defaultValue = "", required = false) String filter
    ){
        return userService.viewEmptySlotForAdmin(pageNo, pageSize, sortBy, sortDir, lecturerId, filter);
    }

}
