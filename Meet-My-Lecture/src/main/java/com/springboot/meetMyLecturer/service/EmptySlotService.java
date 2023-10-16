package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.modelDTO.EmptySlotDTO;

import java.util.List;

public interface EmptySlotService {
    List<EmptySlotDTO> getAllEmptySlot();
    EmptySlotDTO creatEmptySlot(long userId, EmptySlot emptySlot);
}
