package com.springboot.meetMyLecturer.utils;

import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.entity.User;
import org.modelmapper.AbstractConverter;

public class ConvertLecturerName extends AbstractConverter<EmptySlot, String> {
    @Override
    protected String convert(EmptySlot source) {
        if (source.getLecturer() != null) {
            User lecturer = source.getLecturer();
            if (lecturer.getUserName() != null) {
                return lecturer.getUserName();
            } else if (lecturer.getNickName() != null) {
                return lecturer.getNickName();
            }
        }
        return null;
    }
}
