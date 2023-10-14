package com.springboot.meetMyLecturer.service;


import com.springboot.meetMyLecturer.modelDTO.UserDTO;

import java.util.List;

public interface StudentService {

    List<UserDTO> searchLecturers (String name);

}
