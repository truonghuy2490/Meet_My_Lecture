package com.springboot.meetMyLecturer.utils;

import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.entity.User;
import org.modelmapper.AbstractConverter;

public class ConvertLecturerName extends AbstractConverter<EmptySlot, String> {

    @Override
    protected String convert(EmptySlot emptySlot) {
        return null;
    }
}
