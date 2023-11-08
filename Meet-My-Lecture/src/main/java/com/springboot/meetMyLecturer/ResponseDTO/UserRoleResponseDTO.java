package com.springboot.meetMyLecturer.ResponseDTO;

import lombok.Data;

@Data
public class UserRoleResponseDTO {
    private Long userId;
    private String roleName;
    private String userName;
    private String email;
    private Long majorId;
}
