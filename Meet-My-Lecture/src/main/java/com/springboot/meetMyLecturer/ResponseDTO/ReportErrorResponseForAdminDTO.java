package com.springboot.meetMyLecturer.ResponseDTO;

import lombok.Data;

import java.sql.Date;

@Data
public class ReportErrorResponseForAdminDTO {
    private Long reportErrorId;
    private String unique;
    private String reportErrorContent;
    private String status;
    private Date createAt;

}
