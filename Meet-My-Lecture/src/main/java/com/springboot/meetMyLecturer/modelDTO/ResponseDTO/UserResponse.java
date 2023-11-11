package com.springboot.meetMyLecturer.modelDTO.ResponseDTO;

import com.springboot.meetMyLecturer.ResponseDTO.SubjectResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.UserProfileForAdminDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private List<UserProfileForAdminDTO> content;
    private int pageNo;
    private int pageSize;
    private int totalOPEN;
    private int totalCLOSE;
    private Long totalElement;
    private int totalPage;
    private boolean last;
}
