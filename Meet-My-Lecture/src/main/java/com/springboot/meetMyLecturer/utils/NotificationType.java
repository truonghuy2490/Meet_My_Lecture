package com.springboot.meetMyLecturer.utils;

public enum NotificationType {
    RequestApprovedRejected, // student
    RequestCreateSuccessful, // student
    RequestDeleteSuccessful, // student
    SlotStart,  // when assigned and approved -> student
    SlotCreate, // done
    SlotUpdate, // done
    SlotDelete // done
}
