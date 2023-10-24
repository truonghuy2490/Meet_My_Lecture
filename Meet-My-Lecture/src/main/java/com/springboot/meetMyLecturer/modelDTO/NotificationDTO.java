package com.springboot.meetMyLecturer.modelDTO;

import com.springboot.meetMyLecturer.entity.EmptySlot;
import lombok.Data;

import java.util.Date;

@Data
public class NotificationDTO {
    private String notiContent;
    private Date notiDate;
}
