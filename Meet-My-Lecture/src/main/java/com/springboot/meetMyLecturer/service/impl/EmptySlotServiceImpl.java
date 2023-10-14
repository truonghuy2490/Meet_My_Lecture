package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNoFoundException;
import com.springboot.meetMyLecturer.modelDTO.EmptySlotDTO;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import com.springboot.meetMyLecturer.repository.EmptySlotRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.EmptySlotService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmptySlotServiceImpl implements EmptySlotService {

    @Autowired
    EmptySlotRepository emptySlotRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<EmptySlotDTO> getAllEmptySlot() {
        List<EmptySlot> emptySlots = emptySlotRepository.findAll();
        return emptySlots.stream().map(emptySlot -> mapToDTO(emptySlot)).collect(Collectors.toList());
    }

    @Override // can fix , vua sua ben controller
    public EmptySlotDTO creatEmptySlot(int userId, EmptySlot emptySlot) {
        EmptySlotDTO emptySlotDTO = mapToDTO(emptySlot);
        User user = userRepository.findUserByUserId(userId);
        emptySlot.setLecturer(user);

        UserDTO userDTO = new UserDTO();

        userDTO.setUserId(userId);
        emptySlotDTO.setUsers(userDTO);

//        EmptySlot slot = mapToEntity(emptySlotDTO);
//
        EmptySlot newEmptySlot = emptySlotRepository.save(emptySlot);
//
//        EmptySlotDTO responeseEmptySlotDTO = mapToDTO(newEmptySlot);

        return emptySlotDTO;

    }
    // convert entity to DTO
    public EmptySlotDTO mapToDTO(EmptySlot emptySlot) {
        EmptySlotDTO emptySlotDTO = new EmptySlotDTO();
        emptySlotDTO.setSlotId(emptySlot.getSlotId());
        emptySlotDTO.setTimeStart(emptySlot.getTimeStart());
        emptySlotDTO.setDuration(emptySlot.getDuration());
        emptySlotDTO.setDateStart(emptySlot.getDateStart());
        emptySlotDTO.setBookedDate(emptySlot.getBookedDate());
        emptySlotDTO.setStatus(emptySlot.getStatus());
        emptySlotDTO.setDescription(emptySlot.getDescription());
        emptySlotDTO.setRoomId(emptySlot.getRoomId());
        emptySlotDTO.setCode(emptySlot.getCode());

        return emptySlotDTO;
    }
    // convert DTO to entity
    public EmptySlot mapToEntity(EmptySlotDTO emptySlotDTO) {
        EmptySlot emptySlot = new EmptySlot();

        emptySlot.setSlotId(emptySlotDTO.getSlotId());
        emptySlot.setTimeStart(emptySlotDTO.getTimeStart());
        emptySlot.setDuration(emptySlotDTO.getDuration());
        emptySlot.setDateStart(emptySlotDTO.getDateStart());
        emptySlot.setBookedDate(emptySlotDTO.getBookedDate());
        emptySlot.setStatus(emptySlotDTO.getStatus());
        emptySlot.setDescription(emptySlotDTO.getDescription());
        emptySlot.setRoomId(emptySlotDTO.getRoomId());
        emptySlot.setCode(emptySlotDTO.getCode());

        return emptySlot;
    }

}
