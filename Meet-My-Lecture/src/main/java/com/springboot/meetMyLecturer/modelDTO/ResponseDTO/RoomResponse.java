package com.springboot.meetMyLecturer.modelDTO.ResponseDTO;

import com.springboot.meetMyLecturer.ResponseDTO.ReportErrorResponseDTO;
import com.springboot.meetMyLecturer.modelDTO.RoomDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
    private List<RoomDTO> content;
    private int pageNo;
    private int pageSize;
    private int totalOpen;
    private Long totalElement;
    private int totalPage;
    private boolean last;
}
