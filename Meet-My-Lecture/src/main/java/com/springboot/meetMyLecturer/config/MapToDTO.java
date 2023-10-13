package com.springboot.meetMyLecturer.config;

import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MapToDTO {


    public MeetingRequestDTO mapMeetingRequestToDto(MeetingRequest meetingRequest){
        MeetingRequestDTO meetingRequestDTO = new MeetingRequestDTO();
        meetingRequestDTO.setRequestContent(meetingRequest.getRequestContent());
        meetingRequestDTO.setRequestStatus(meetingRequest.getRequestStatus());
        return meetingRequestDTO;
    }

    public UserDTO mapUserToDto (User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setUserName(user.getUserName());
        userDTO.setUserId(user.getUserId());
        return userDTO;
    }

}
