package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(
        name = "tbl_users"

)
public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long userId;

    @Column(name = "user_name", nullable = false)
    private String userName;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "role_id", nullable = false)
    private String roleId;
    @Column(name = "gender", nullable = false)
    private String gender;

}
