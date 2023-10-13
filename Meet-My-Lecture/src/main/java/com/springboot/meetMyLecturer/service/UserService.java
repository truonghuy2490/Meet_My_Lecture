package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;

public interface UserService {

    UserDTO registerUser(int roleId, User user);

}
