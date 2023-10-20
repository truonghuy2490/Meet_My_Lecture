package com.springboot.meetMyLecturer.modelDTO.ResponseDTO;


import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestResponse {
    private List<MeetingRequestDTO> content;
    private int pageNo;
    private int pageSize;
    private Long totalElement;
    private int totalPage;
    private boolean last;
}
