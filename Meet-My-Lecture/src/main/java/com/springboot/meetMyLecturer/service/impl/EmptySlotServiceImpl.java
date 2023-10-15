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
    ModelMapper modelMapper;

    @Autowired
    EmptySlotRepository emptySlotRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<EmptySlotDTO> getAllEmptySlot() {
        List<EmptySlot> emptySlots = emptySlotRepository.findAll();
        return emptySlots.stream().map(emptySlot -> mapToDTO(emptySlot)).collect(Collectors.toList());
    }

    @Override
    public EmptySlotDTO creatEmptySlot(Long userId, EmptySlot emptySlot) {
        EmptySlotDTO emptySlotDTO = mapToDTO(emptySlot);
        User user = userRepository.findByUserId(userId);
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
        return modelMapper.map(emptySlot, EmptySlotDTO.class);
    }
    // convert DTO to entity
    public EmptySlot mapToEntity(EmptySlotDTO emptySlotDTO) {
        return modelMapper.map(emptySlotDTO, EmptySlot.class);
    }

}
