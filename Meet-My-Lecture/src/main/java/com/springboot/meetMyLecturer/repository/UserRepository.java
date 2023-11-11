package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("select u.userId from User u where u.unique like %:unique%")
    List<Long> findUserByUniqueContains(@Param("unique") String unique);

    @Query("select u.userId from User u where u.email =:email")
    Long findUserIdByEmail(String email);

    @Query("select u.userId from User  u join LecturerSubject ls on ls.lecturer.userId = u.userId and ls.subject.subjectId =:subjectId")
    List<Long> findLecturerIdBySubjectId(String subjectId);

    @Query("select u.userName from User u where u.userId =:userId")
    String findUserNameByUserId(Long userId);

    @Query("select u.unique from User u where u.userId =:userId")
    String findUniqueByUserId(Long userId);

    @Query("select u.unique from User u where u.userId =:userId and u.status =:status")
    String findNickNameByUserId(Long userId, String status);

    Page<User> findUserByStatus(String status, Pageable pageable);
    List<User> findUserByStatus(String status);

    User findUserByUnique(String unique);

    List<User> findUsersByUserNameContains(String userName);
}
