package com.springboot.meetMyLecturer.ResponseDTO;

import lombok.Data;

@Data
public class ReportErrorResponseDTO {

    private String userName;

    private String reportErrorContent;

    private String status;

}
