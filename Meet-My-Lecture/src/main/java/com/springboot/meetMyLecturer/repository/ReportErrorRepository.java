package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.ReportError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ReportErrorRepository extends JpaRepository<ReportError, Long> {

    @Query("select r.reportErrorId from ReportError r")
    List<Long> getReportErrorId();

    @Query("select r.reportErrorId from ReportError r where r.user.userId =:userId")
    List<Long> getReportErrorByUserId(Long userId);

    @Query("select r.reportErrorContent from ReportError r where r.reportErrorId =:reportErrorId")
    String getReportContent(Long reportErrorId);

    @Query("select r.status from ReportError r where r.reportErrorId =:reportErrorId")
    String getStatus(Long reportErrorId);

    @Query("select r.createAt from ReportError r where r.reportErrorId =:reportErrorId")
    Date getCreateAt(Long reportErrorId);

    @Query("select r.user.userId from ReportError r where r.reportErrorId =:reportErrorId")
    Long getUserId(Long reportErrorId);

}
