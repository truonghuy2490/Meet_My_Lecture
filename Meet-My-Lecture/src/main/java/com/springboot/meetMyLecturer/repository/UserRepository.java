package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.modelDTO.UserProfileDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query( "select u from User u join Role r on u.role.roleId = r.roleId where u.userName like %:name% and r.roleName ='LECTURER' ")
    List<User> findLecturerByUserName(String name);

    @Query("select u.email from User u join Role r on r.roleId = u.role.roleId and r.roleName !='Admin' ")
    List<String> findUserNotAdmin();

    Optional<User> findUserByEmail(String email);


}
