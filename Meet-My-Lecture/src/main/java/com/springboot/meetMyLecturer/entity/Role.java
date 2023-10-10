package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

@Data
@Entity
@Table(
        name = "tbl_role",
        uniqueConstraints = {
                @UniqueConstraint(
                    name = "role-name",
                    columnNames = "name"
                )
        }
)

public class Role {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private String roleId;

    @Column(name = "name", nullable = false)
    private String roleName;
}
