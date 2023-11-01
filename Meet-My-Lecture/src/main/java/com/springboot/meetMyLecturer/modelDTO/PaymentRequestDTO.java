package com.springboot.meetMyLecturer.modelDTO;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private String description;
    private String url;
    private int price;

}
