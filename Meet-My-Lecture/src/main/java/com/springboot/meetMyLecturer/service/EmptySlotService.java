package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.EmptySlotResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectResponseDTO;
import com.springboot.meetMyLecturer.modelDTO.EmptySlotDTO;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.SlotResponse;

import java.util.List;

public interface EmptySlotService {

    SlotResponse getAllSlot(int pageNo, int pageSize, String sortBy, String sortDir);

    EmptySlotResponseDTO creatEmptySlot(Long lecturerId, EmptySlotDTO emptySlotDTO);

    EmptySlotResponseDTO assignRequestToSlot(Long meetingRequestId, Long emptySlotId);
    // lecturer
    EmptySlotResponseDTO rescheduleEmptySlot(Long lecturerId, Long emptySlotId, EmptySlotResponseDTO emptySlotResponseDTO);

    // lecturer
    EmptySlotResponseDTO deleteSlot(Long lecturerId, Long emptySlotId, EmptySlotDTO emptySlotDTO);

    List<SubjectResponseDTO> getSubjectsOfLecturer(Long lecturerId);

}
