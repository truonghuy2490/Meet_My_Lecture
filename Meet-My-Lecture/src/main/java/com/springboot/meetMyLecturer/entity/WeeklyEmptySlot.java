package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



import java.util.Date;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "weekly_empty_slot")

public class WeeklyEmptySlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weekly_slot_id")
    private Long weeklySlotId;

    @Column(name = "first_day_of_week", nullable = false)
    private Date firstDayOfWeek;

    @Column(name = "last_day_of_week", nullable = false)
    private Date lastDayOfWeek;

    @OneToMany(mappedBy = "weeklySlot")
    private Set<EmptySlot> emptySlots;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;

}

