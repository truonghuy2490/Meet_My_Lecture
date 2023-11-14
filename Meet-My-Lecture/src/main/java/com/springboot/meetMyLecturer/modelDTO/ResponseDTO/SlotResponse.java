package com.springboot.meetMyLecturer.modelDTO.ResponseDTO;

import com.springboot.meetMyLecturer.ResponseDTO.EmptySlotResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlotResponse {
    private List<EmptySlotResponseDTO> content;
    private int pageNo;
    private int pageSize;
    private int totalOPEN;
    private int totalBOOKED;
    private Long totalElement;
    private int totalPage;
    private boolean last;
}
