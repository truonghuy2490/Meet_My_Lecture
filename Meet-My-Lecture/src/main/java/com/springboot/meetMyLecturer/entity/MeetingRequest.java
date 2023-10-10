package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(
        name = "tbl_meeting_request"
//        uniqueConstraints = {
//            @UniqueConstraint(
//                    name = "",
//                    columnNames = ""),
//            @UniqueConstraint(
//                    name = "",
//                    columnNames = "")}
)
public class MeetingRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int requestId;

    private String requestContent;
    private String requestStatus;

}
