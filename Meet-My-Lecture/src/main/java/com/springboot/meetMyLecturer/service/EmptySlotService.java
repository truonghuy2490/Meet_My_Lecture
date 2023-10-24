package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.EmptySlotResponseDTO;
import com.springboot.meetMyLecturer.modelDTO.EmptySlotDTO;
public interface EmptySlotService {

    EmptySlotResponseDTO creatEmptySlot(Long lecturerId, EmptySlotDTO emptySlotDTO);
    EmptySlotResponseDTO assignRequestToSlot(Long meetingRequestId, Long emptySlotId);


    EmptySlotResponseDTO updateEmptySlot(Long lecturerId, Long emptySlotId, EmptySlotDTO emptySlotDTO);
}
