package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "slot_time")
public class SlotTime {

    @Id
    private int slotTimeId;

    private Time timeStart;

    private Time timeEnd;

    @OneToMany(mappedBy = "slotTime")
    private Set<EmptySlot> emptySlots;

}
