package com.springboot.meetMyLecturer.modelDTO.ResponseDTO;

import com.springboot.meetMyLecturer.ResponseDTO.SubjectResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectResponse {
    private List<SubjectResponseDTO> content;
    private int pageNo;
    private int pageSize;
    private Long totalElement;
    private int totalPage;
    private boolean last;
}
