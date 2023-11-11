package com.springboot.meetMyLecturer.modelDTO.ResponseDTO;

import com.springboot.meetMyLecturer.ResponseDTO.SubjectResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectResponseForAdminDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectResponse {
    private List<SubjectResponseForAdminDTO> content;
    private int pageNo;
    private int pageSize;
    private int totalOpen;
    private Long totalElement;
    private int totalPage;
    private boolean last;
}
