create table empty_slot
(
    slot_id            int auto_increment
        primary key,
    lecturer_id        int          null,
    student_id         int          null,
    subject_id         varchar(10)  null,
    meeting_request_id int          null,
    weekly_slot_id     int          null,
    time_start         time         null,
    duration           time         null,
    date_start         date         null,
    booked_date        timestamp    null,
    status             varchar(10)  null,
    room_id            varchar(20)  null,
    description        varchar(150) null,
    code               int          null,
    slot_time_id       int          null,
    constraint empty_slot_ibfk_1
        foreign key (lecturer_id) references user (user_id),
    constraint empty_slot_ibfk_2
        foreign key (student_id) references user (user_id),
    constraint empty_slot_ibfk_3
        foreign key (subject_id) references subject (subject_id),
    constraint empty_slot_ibfk_4
        foreign key (weekly_slot_id) references weekly_empty_slot (weekly_slot_id),
    constraint empty_slot_meeting_request_request_id_fk
        foreign key (meeting_request_id) references meeting_request (request_id),
    constraint empty_slot_room_room_id_fk
        foreign key (room_id) references room (room_id),
    constraint empty_slot_slot_time_slot_time_id_fk
        foreign key (slot_time_id) references slot_time (slot_time_id)
);

create index lecturer_id
    on empty_slot (lecturer_id);

create index student_id
    on empty_slot (student_id);

create index subject_id
    on empty_slot (subject_id);

create index weekly_slot_id
    on empty_slot (weekly_slot_id);

--
create table lecturer_subject
(
    lecturer_id int         not null,
    subject_id  varchar(10) not null,
    status      varchar(10) null,
    primary key (lecturer_id, subject_id),
    constraint lecturer_subject_ibfk_1
        foreign key (lecturer_id) references user (user_id),
    constraint lecturer_subject_ibfk_2
        foreign key (subject_id) references subject (subject_id)
);

create index subject_id
    on lecturer_subject (subject_id);

--
create table major
(
    major_id   int auto_increment
        primary key,
    major_name varchar(50) null,
    admin_id   int         null,
    status     varchar(10) null,
    constraint major_ibfk_1
        foreign key (admin_id) references user (user_id)
);

create index admin_id
    on major (admin_id);

--
create table meeting_request
(
    request_id      int auto_increment
        primary key,
    student_id      int          null,
    lecturer_id     int          null,
    subject_id      varchar(10)  null,
    request_content varchar(150) null,
    request_status  varchar(20)  null,
    create_at       timestamp    null,
    constraint meeting_request_ibfk_1
        foreign key (student_id) references user (user_id),
    constraint meeting_request_ibfk_2
        foreign key (lecturer_id) references user (user_id),
    constraint meeting_request_ibfk_4
        foreign key (subject_id) references subject (subject_id)
);

create index lecturer_id
    on meeting_request (lecturer_id);

create index student_id
    on meeting_request (student_id);

create index subject_id
    on meeting_request (subject_id);

--
create table notification
(
    notification_id      int auto_increment
        primary key,
    timestamp            date         null,
    notification_message varchar(150) null,
    user_id              int          null,
    slot_id              int          null,
    constraint notification_ibfk_1
        foreign key (user_id) references user (user_id),
    constraint notification_ibfk_2
        foreign key (slot_id) references empty_slot (slot_id)
);

create index slot_id
    on notification (slot_id);

create index user_id
    on notification (user_id);

--
create table role
(
    role_id   int auto_increment
        primary key,
    role_name varchar(10) null
);

--
create table room
(
    room_id varchar(20) not null
        primary key,
    address varchar(70) null,
    status  varchar(10) null
);


--
create table semester
(
    semester_id   int auto_increment
        primary key,
    semester_name varchar(50) null,
    date_start    date        null,
    date_end      date        null,
    admin_id      int         null,
    year          int         null,
    status        varchar(10) null,
    constraint semester_ibfk_1
        foreign key (admin_id) references user (user_id)
);

create index admin_id
    on semester (admin_id);

--
create table slot_time
(
    slot_time_id int         not null
        primary key,
    start_time   time        null,
    end_time     time        null,
    status       varchar(10) null
);

--
create table subject
(
    subject_id   varchar(10) not null
        primary key,
    subject_name varchar(50) null,
    admin_id     int         null,
    status       varchar(10) null,
    constraint subject_ibfk_1
        foreign key (admin_id) references user (user_id)
);

create index admin_id
    on subject (admin_id);

--
create table subject_lecturer_student
(
    lecturer_id int         not null,
    subject_id  varchar(10) not null,
    student_id  int         not null,
    status      varchar(10) null,
    primary key (lecturer_id, subject_id, student_id),
    constraint subject_lecturer_student_ibfk_1
        foreign key (lecturer_id) references user (user_id),
    constraint subject_lecturer_student_ibfk_2
        foreign key (subject_id) references subject (subject_id),
    constraint subject_lecturer_student_ibfk_3
        foreign key (student_id) references user (user_id)
);

create index student_id
    on subject_lecturer_student (student_id);

create index subject_id
    on subject_lecturer_student (subject_id);

--
create table subject_major
(
    subject_id varchar(10) not null,
    major_id   int         not null,
    primary key (subject_id, major_id),
    constraint subject_major_ibfk_1
        foreign key (subject_id) references subject (subject_id),
    constraint subject_major_ibfk_2
        foreign key (major_id) references major (major_id)
);

create index major_id
    on subject_major (major_id);

--
create table subject_semester
(
    subject_id  varchar(10) not null,
    semester_id int         not null,
    primary key (subject_id, semester_id),
    constraint subject_semester_ibfk_1
        foreign key (subject_id) references subject (subject_id),
    constraint subject_semester_ibfk_2
        foreign key (semester_id) references semester (semester_id)
);

create index semester_id
    on subject_semester (semester_id);

--
create table teaching_schedule
(
    teaching_schedule_id int auto_increment
        primary key,
    lecturer_id          int         null,
    subject_id           varchar(10) null,
    room_id              varchar(10) null,
    date                 date        null,
    time                 time        null,
    date_of_week         varchar(10) null,
    slot_id              int         null,
    status               varchar(10) null,
    constraint teaching_schedule_ibfk_1
        foreign key (lecturer_id) references user (user_id),
    constraint teaching_schedule_ibfk_2
        foreign key (subject_id) references subject (subject_id),
    constraint teaching_schedule_slot_time_slot_time_id_fk
        foreign key (slot_id) references slot_time (slot_time_id)
);

create index lecturer_id
    on teaching_schedule (lecturer_id);

create index subject
    on teaching_schedule (subject_id);

--
create table user
(
    user_id      int auto_increment
        primary key,
    user_name    varchar(100) null,
    email        varchar(50)  null,
    role_id      int          null,
    major_id     int          null,
    absent_count int          null,
    nick_name    varchar(20)  null,
    status       varchar(10)  null,
    constraint user_ibfk_1
        foreign key (role_id) references role (role_id)
);

create index role_id
    on user (role_id);

--
create table weekly_empty_slot
(
    weekly_slot_id    int auto_increment
        primary key,
    semester_id       int         null,
    first_day_of_week date        null,
    last_day_of_week  date        null,
    status            varchar(10) null,
    constraint weekly_empty_slot_ibfk_1
        foreign key (semester_id) references semester (semester_id)
);

create index semester_id
    on weekly_empty_slot (semester_id);


