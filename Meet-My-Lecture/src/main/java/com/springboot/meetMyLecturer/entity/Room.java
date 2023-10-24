package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Room")
public class Room {

    @Id
    @Column(name = "room_id")
    private String roomId;

    private String address;

    @JoinColumn(name = "status")
    private String status;
}
