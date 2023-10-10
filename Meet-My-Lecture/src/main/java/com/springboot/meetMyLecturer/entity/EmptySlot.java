package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;



@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "tbl_emptySlot",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "empty_slot_name",
                    columnNames = "name")
})
public class EmptySlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time_start_slot", nullable = false)
    private Time timeStart; // slot may'

    @Column(name = "period_time_slot", nullable = false)
    private Time duration;

    private String roomId;
    private String status;
    private String description;

    @CreationTimestamp
    private LocalDateTime dateCreate;
    @UpdateTimestamp
    private LocalDateTime dateUpdate;
    private boolean active;


}
