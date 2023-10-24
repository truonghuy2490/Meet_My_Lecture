package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query( "select u from User u join Role r on u.role.roleId = r.roleId where u.userName like %:name% and r.roleName ='LECTURER' and u.status =:status ")
    List<User> findLecturerByUserNameAndStatus(String name, String status);

    @Query("select u from User u join Role r on r.roleId = u.role.roleId and r.roleName !='ADMIN' ")
    List<User> findUserNotAdmin();

    User findUserByEmail(String email);

    User findUserByUserIdAndStatus(Long lecturerId, String status);

    List<User> findUserByUniqueContains(String unique);




}
