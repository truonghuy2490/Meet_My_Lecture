package com.springboot.meetMyLecturer.ResponseDTO;

import lombok.Data;

@Data
public class UserProfileForAdminDTO {
    private long userId;
    private String userName;
    private String email;
    private String unique;
    private int roleId;
    private int absentCount;
    private String status;
}
