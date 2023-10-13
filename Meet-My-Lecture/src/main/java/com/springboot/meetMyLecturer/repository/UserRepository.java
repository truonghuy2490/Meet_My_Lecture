package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query( "select u from User u join Role r on u.role.roleId = r.roleId where u.userName like %:name% and r.roleName ='LECTURER' ")
    List<User> findLecturerByUserName(String name);

    User findUserByUserId(int id);





}
