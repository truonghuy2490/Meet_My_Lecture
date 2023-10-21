package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.BookedSlotCalendarDTO;
import com.springboot.meetMyLecturer.ResponseDTO.BookedSlotHomePageDTO;
import com.springboot.meetMyLecturer.modelDTO.EmptySlotDTO;


import java.util.List;

public interface EmptySlotService {
    List<BookedSlotHomePageDTO> getAllEmptySlotByUserId(Long userId);

    BookedSlotHomePageDTO creatEmptySlot(Long lectureId, EmptySlotDTO emptySlotDTO);
    BookedSlotCalendarDTO assignRequestToSlot(Long meetingRequestId, Long emptySlotId);
}
