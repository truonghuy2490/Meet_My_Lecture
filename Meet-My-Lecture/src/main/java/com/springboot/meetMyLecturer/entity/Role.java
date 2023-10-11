package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Locale;
import java.util.Set;

@Data
@Entity
@Table(name = "role", uniqueConstraints = {
                @UniqueConstraint(
                    name = "role-name",
                    columnNames = "role_name"
                )
        }
)

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int roleId;

    @Column(name = "role_name", nullable = false)
    private String roleName;
}
