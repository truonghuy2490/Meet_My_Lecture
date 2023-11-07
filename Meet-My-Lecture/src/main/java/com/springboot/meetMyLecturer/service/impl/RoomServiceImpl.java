package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.EmptySlotResponseDTO;
import com.springboot.meetMyLecturer.constant.Constant;
import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.entity.Room;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.RoomResponse;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.SlotResponse;
import com.springboot.meetMyLecturer.modelDTO.RoomDTO;
import com.springboot.meetMyLecturer.repository.RoomRepository;
import com.springboot.meetMyLecturer.service.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    ModelMapper modelMapper;
    //get all room DONE - DONE
    @Override
    public List<Room> getAllRooms() {
        List<Room> roomList = roomRepository.findRoomsByStatus(Constant.OPEN);
        if(roomList.isEmpty()){
            throw new RuntimeException("There are no rooms.");
        }
        return roomList;
    }

    //get all rooms for admin DONE-DONE
    @Override
    public List<Room> getAllRoomsForAdmin() {
        List<Room> roomList = roomRepository.findAll();

        if (roomList.isEmpty()){
            throw new RuntimeException("There are no rooms.");
        }
        return roomList;
    }

    //create room for admin DONE-DONE
    @Override
    public Room createRoomForAmin(RoomDTO roomDTO) {
        validateRoom(roomDTO);

        Room room = roomRepository.findRoomByRoomIdAndAddress(roomDTO.getRoomId(), roomDTO.getAddress());
        if(room != null){
            throw new RuntimeException("This room is already existed.");
        }

        Room roomDB = modelMapper.map(roomDTO, Room.class);
        roomDB.setStatus(Constant.OPEN);
        roomRepository.save(roomDB);

        return roomDB;
    }

    //edit room for admin DONE-DONE
    @Override
    public Room editRoomForAdmin(Room roomDTO) {
        RoomDTO roomDTOCheck = modelMapper.map(roomDTO, RoomDTO.class);

        validateRoom(roomDTOCheck);

        Room room = roomRepository.findRoomByRoomIdAndAddress(roomDTO.getRoomId(), roomDTO.getAddress());
        if(room == null){
            throw new RuntimeException("This room is not existed.");
        }

        Room roomDB = modelMapper.map(roomDTO, Room.class);
        roomRepository.save(roomDB);
        return roomDB;
    }

    @Override
    public RoomResponse getAllRoom(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        // CREATE PAGEABLE INSTANCE
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);


        // SAVE TO REPO
        Page<Room> rooms = roomRepository.findAll(pageable);

        // get content for page object
        List<Room> roomList = rooms.getContent();

        List<RoomDTO> content = roomList.stream().map(
                slot -> modelMapper.map(slot, RoomDTO.class)
        ).collect(Collectors.toList());

        RoomResponse roomResponse = new RoomResponse();
        roomResponse.setContent(content);
        roomResponse.setTotalPage(rooms.getTotalPages());
        roomResponse.setTotalElement(rooms.getTotalElements());
        roomResponse.setPageNo(rooms.getNumber());
        roomResponse.setPageSize(rooms.getSize());
        roomResponse.setLast(rooms.isLast());

        return roomResponse;
    }

    public void validateRoom(RoomDTO roomDTO) {
        Pattern patternFPT = Pattern.compile("^(\\d{3})$");
        Pattern patternNVH = Pattern.compile("^(\\d{3}NVH)$");

        Matcher matcher = null;

        switch (roomDTO.getAddress()) {
            case "FPT University":
                matcher = patternFPT.matcher(roomDTO.getRoomId());
                if (!matcher.matches()) {
                    throw new RuntimeException("Wrong format for FPT University.");
                }
                break;
            case "Nha Van Hoa":
                matcher = patternNVH.matcher(roomDTO.getRoomId());
                if (!matcher.matches()) {
                    throw new RuntimeException("Wrong format for Nha Van Hoa.");
                }
                break;
            default:
                throw new RuntimeException("Wrong address.");
        }
    }
}
