package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.modelDTO.BookedSlotHomePageDTO;

import java.util.List;

public interface EmptySlotService {
//    List<EmptySlotDTO> getAllEmptySlot(); // dung de test thoi

    List<BookedSlotHomePageDTO> getAllEmptySlotByUserId(Long userId);

    BookedSlotHomePageDTO creatEmptySlot(Long lectureId, EmptySlot emptySlot);
    BookedSlotHomePageDTO assignRequestToSlot(Long meetingRequestId, Long slotId);
}
