package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.modelDTO.EmptySlotDTO;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import java.util.List;

public interface EmptySlotService {
    List<EmptySlotDTO> getAllEmptySlot();
    EmptySlotDTO creatEmptySlot(Long lectureId, EmptySlot emptySlot);
    EmptySlotDTO assignRequestToSlot(Long meetingRequestId, Long slotId);
}
