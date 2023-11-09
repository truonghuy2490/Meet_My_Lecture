package com.springboot.meetMyLecturer.modelDTO.ResponseDTO;

import com.springboot.meetMyLecturer.ResponseDTO.MajorResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.NotificationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MajorResponse {
    private List<MajorResponseDTO> content;
    private int count;
    private int pageNo;
    private int pageSize;
    private Long totalElement;
    private int totalPage;
    private boolean last;
}
