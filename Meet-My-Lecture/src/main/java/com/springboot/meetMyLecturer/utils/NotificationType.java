package com.springboot.meetMyLecturer.utils;

public enum NotificationType {
    RequestApprovedRejected, // student - done
    RequestCreateSuccessful, // student - done
    RequestDeleteSuccessful, // student - done

    SlotAssign, // done
    SlotStart,  // when assigned and approved -> student
    SlotCreate, // done
    SlotUpdate, // done
    SlotDelete // done
}
