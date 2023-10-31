package com.springboot.meetMyLecturer.ResponseDTO;

import lombok.Data;

@Data
public class ReportErrorResponseDTO {

    private Long reportErrorId;

    private Long userId;

    private String unique;

    private String errorType;

    private String reportErrorContent;

    private String status;

}
