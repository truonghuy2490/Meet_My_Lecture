package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.UserMajor;
import com.springboot.meetMyLecturer.entity.UserMajorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserMajorRepository extends JpaRepository<UserMajor, UserMajorId> {

    @Query("select m.majorName from Major m join UserMajor um on um.major.majorId = m.majorId and um.user.userId =:userId")
    Set<String> getMajorName(Long userId);

    @Query("select m.majorId from Major m join UserMajor um on um.major.majorId = m.majorId and um.user.userId =:userId")
    List<Long> findId(Long userId);

}
