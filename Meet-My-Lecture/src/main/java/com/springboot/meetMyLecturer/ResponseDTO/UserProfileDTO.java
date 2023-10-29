package com.springboot.meetMyLecturer.ResponseDTO;

import com.springboot.meetMyLecturer.entity.Major;
import com.springboot.meetMyLecturer.entity.Role;
import lombok.Data;

@Data
public class UserProfileDTO {
    private long userId;
    private String userName;
    private String email;
    private String unique;
}
