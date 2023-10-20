package com.springboot.meetMyLecturer.utils;

public class DayUtils {
    // Convert java.util.Date to java.sql.Date
    public static java.sql.Date convertUtilToSqlDate(java.util.Date utilDate) {
        if (utilDate != null) {
            return new java.sql.Date(utilDate.getTime());
        }
        return null;
    }

    // Convert java.sql.Date to java.util.Date
    public static java.util.Date convertSqlToUtilDate(java.sql.Date sqlDate) {
        if (sqlDate != null) {
            return new java.util.Date(sqlDate.getTime());
        }
        return null;
    }
}
