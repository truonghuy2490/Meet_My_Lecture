package com.springboot.meetMyLecturer.modelDTO;

import lombok.Data;

@Data
public class UserDTO {
    private long userId;
    private String userName;
    private String email;
    private String role;
}
