package com.springboot.meetMyLecturer.modelMapper;

import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class MapDTO {
    public UserDTO mapToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUserName(user.getUserName());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}
