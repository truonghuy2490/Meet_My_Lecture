package com.springboot.meetMyLecturer.entity;

import java.io.Serializable;
import java.util.Objects;

public class SubjectLecturerStudentId implements Serializable {

    private Long lecturerId;

    private Long studentId;

    private String subjectId;

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectLecturerStudentId that = (SubjectLecturerStudentId) o;
        return Objects.equals(lecturerId, that.lecturerId) &&
                Objects.equals(studentId, that.studentId) &&
                Objects.equals(subjectId, that.subjectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lecturerId, studentId, subjectId);
    }*/

}
