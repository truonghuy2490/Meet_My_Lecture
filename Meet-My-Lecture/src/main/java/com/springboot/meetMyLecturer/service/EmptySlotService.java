package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.EmptySlotResponseDTO;
import com.springboot.meetMyLecturer.modelDTO.EmptySlotDTO;
public interface EmptySlotService {

    EmptySlotResponseDTO creatEmptySlot(Long lectureId, EmptySlotDTO emptySlotDTO);
    EmptySlotResponseDTO assignRequestToSlot(Long meetingRequestId, Long emptySlotId);

}
