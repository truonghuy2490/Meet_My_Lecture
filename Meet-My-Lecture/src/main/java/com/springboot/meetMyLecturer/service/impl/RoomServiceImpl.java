package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.entity.Room;
import com.springboot.meetMyLecturer.repository.RoomRepository;
import com.springboot.meetMyLecturer.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    RoomRepository roomRepository;
    //get all room DONE
    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
}
