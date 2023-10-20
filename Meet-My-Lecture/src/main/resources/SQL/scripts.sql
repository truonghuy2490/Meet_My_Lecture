

CREATE TABLE role (
                      role_id INT PRIMARY KEY,
                      role_name VARCHAR(10)
);

CREATE TABLE user (
                      user_id INT PRIMARY KEY,
                      user_name VARCHAR(100),
                      email VARCHAR(50),
                      role_id INT,
                      absent_count INT,
                      FOREIGN KEY (role_id) REFERENCES role(role_id)
);

CREATE TABLE major (
                       major_id INT PRIMARY KEY,
                       major_name VARCHAR(50),
                       admin_id INT,
                       FOREIGN KEY (admin_id) REFERENCES user(user_id)
);

CREATE TABLE semester (
                          semester_id INT PRIMARY KEY,
                          semester_name VARCHAR(50),
                          date_start DATE,
                          date_end DATE,
                          admin_id INT,
                          FOREIGN KEY (admin_id) REFERENCES user(user_id)
);

CREATE TABLE subject (
                         subject_id VARCHAR(10) PRIMARY KEY,
                         major_id INT,
                         subject_name VARCHAR(50),
                         admin_id INT,
                         FOREIGN KEY (major_id) REFERENCES major(major_id),
                         FOREIGN KEY (admin_id) REFERENCES user(user_id)
);

CREATE TABLE empty_slot (
                            slot_id INT PRIMARY KEY,
                            lecturer_id INT,
                            student_id INT,
                            subject_id VARCHAR(10),
                            weekly_slot_id INT,
                            time_start TIME,
                            duration TIME,
                            date_start DATE,
                            booked_date TIMESTAMP,
                            status VARCHAR(10),
                            room_id INT,
                            description VARCHAR(150),
                            code INT(4),
                            FOREIGN KEY (lecturer_id) REFERENCES user(user_id),
                            FOREIGN KEY (student_id) REFERENCES user(user_id),
                            FOREIGN KEY (subject_id) REFERENCES subject(subject_id),
                            FOREIGN KEY (weekly_slot_id) REFERENCES weekly_empty_slot(weekly_slot_id)
);

CREATE TABLE weekly_empty_slot (
                                   weekly_slot_id INT PRIMARY KEY,
                                   semester_id INT,
                                   first_day_of_week DATE,
                                   last_day_of_week DATE,
                                   year INT,
                                   FOREIGN KEY (semester_id) REFERENCES semester(semester_id)
);

CREATE TABLE meeting_request (
                                 request_id INT PRIMARY KEY,
                                 student_id INT,
                                 lecturer_id INT,
                                 slot_id INT,
                                 request_content VARCHAR(150),
                                 request_status VARCHAR(20),
                                 FOREIGN KEY (student_id) REFERENCES user(user_id),
                                 FOREIGN KEY (lecturer_id) REFERENCES user(user_id),
                                 FOREIGN KEY (slot_id) REFERENCES empty_slot(slot_id)
);

CREATE TABLE notification (
                              noti_id INT PRIMARY KEY,
                              noti_date DATE,
                              noti_content VARCHAR(150),
                              user_id INT,
                              slot_id INT,
                              FOREIGN KEY (user_id) REFERENCES user(user_id),
                              FOREIGN KEY (slot_id) REFERENCES empty_slot(slot_id)
);

CREATE TABLE teaching_schedule (
                                   teaching_schedule_id INT PRIMARY KEY,
                                   lecturer_id INT,
                                   subject_id VARCHAR(10),
                                   room_id INT,
                                   date DATE,
                                   time TIME,
                                   FOREIGN KEY (lecturer_id) REFERENCES user(user_id),
                                   FOREIGN KEY (subject_id) REFERENCES subject(subject_id)
);

CREATE TABLE subject_lecturer_student (
                                          lecturer_id INT,
                                          subject_id VARCHAR(10),
                                          student_id INT,
                                          FOREIGN KEY (lecturer_id) REFERENCES user(user_id),
                                          FOREIGN KEY (subject_id) REFERENCES subject(subject_id),
                                          FOREIGN KEY (student_id) REFERENCES user(user_id)
);

CREATE TABLE lecturer_subject (
                                  lecturer_id INT,
                                  subject_id VARCHAR(10),
                                PRIMARY KEY (lecturer_id,subject_id),
                                  FOREIGN KEY (lecturer_id) REFERENCES user(user_id),
                                  FOREIGN KEY (subject_id) REFERENCES subject(subject_id)
);

CREATE TABLE subject_semester (
                                  subject_id VARCHAR(10),
                                  semester_id INT,
                                  FOREIGN KEY (subject_id) REFERENCES subject(subject_id),
                                  FOREIGN KEY (semester_id) REFERENCES semester(semester_id)
);




-- Insert data into the role table
INSERT INTO role (role_id, role_name) VALUES
                                          (1, 'Admin'),
                                          (2, 'Lecturer'),
                                          (3, 'Student');

-- Insert data into the user table
INSERT INTO user (user_id, user_name, email, role_id, absent_count) VALUES
                                                                        (1, 'Admin User', 'admin@example.com', 1, 0),
                                                                        (2, 'Lecturer 1', 'lecturer1@example.com', 2, 0),
                                                                        (3, 'Lecturer 2', 'lecturer2@example.com', 2, 0),
                                                                        (4, 'Student 1', 'student1@example.com', 3, 0),
                                                                        (5, 'Student 2', 'student2@example.com', 3, 0);

-- Insert data into the major table
INSERT INTO major (major_id, major_name, admin_id) VALUES
                                                       (1, 'Computer', 1),
                                                       (2, 'Math', 1);

-- Insert data into the semester table
INSERT INTO semester (semester_id, semester_name, date_start, date_end, admin_id) VALUES
                                                                                      (1, 'Spring 2023', '2023-01-01', '2023-05-31', 1),
                                                                                      (2, 'Fall 2023', '2023-08-01', '2023-12-31', 1);

-- Insert data into the subject table
INSERT INTO subject (subject_id, major_id, subject_name, admin_id) VALUES
                                                                       ('CS101', 1, 'Introduction to Computer Science', 1),
                                                                       ('MATH101', 2, 'Calculus I', 1),
                                                                       ('CS201', 1, 'Data Structures',1),
                                                                       ('MATH201', 2, 'Linear Algebra', 1);

-- Insert data into the weekly_empty_slot table
INSERT INTO weekly_empty_slot (weekly_slot_id, semester_id, first_day_of_week, last_day_of_week, year) VALUES
                                                                                                           (1, 1, '2023-01-01', '2023-01-07', 2023),
                                                                                                           (2, 1, '2023-01-08', '2023-01-14', 2023);

-- Insert data into the empty_slot table
INSERT INTO empty_slot (slot_id, lecturer_id, student_id, subject_id, weekly_slot_id, time_start, duration, date_start, booked_date, status, room_id, description, code) VALUES
                                                                                                                                                                             (1, 2, 4, 'CS101', 1, '08:00:00', '01:00:00', '2023-01-02', '2023-01-01 10:00:00', 'Open', 101, 'Lecture 1', 1234),
                                                                                                                                                                             (2, 2, 4, 'CS201', 2, '10:00:00', '01:00:00', '2023-01-09', '2023-01-08 09:00:00', 'Open', 102, 'Lecture 2', 5678);

-- Insert data into the meeting_request table
INSERT INTO meeting_request (request_id, student_id, lecturer_id, slot_id, request_content, request_status) VALUES
                                                                                                                (1, 4, 2, 1, 'Request for Office Hours', 'Pending'),
                                                                                                                (2, 4, 2, 2, 'Request for Clarification', 'Approved');

-- Insert data into the notification table
INSERT INTO notification (noti_id, noti_date, noti_content, user_id, slot_id) VALUES
                                                                                  (1, '2023-01-05', 'Meeting request approved.', 2, 1),
                                                                                  (2, '2023-01-10', 'Meeting request approved.', 2, 2);

-- Insert data into the teaching_schedule table
INSERT INTO teaching_schedule (teaching_schedule_id, lecturer_id, subject_id, room_id, date, time)VALUES
                                                                                                    (1, 2, 'CS101', 101, '2023-01-02', '08:00:00'),
                                                                                                    (2, 2, 'CS201', 102, '2023-01-09', '10:00:00');

-- Insert data into the subject_lecturer_student table
INSERT INTO subject_lecturer_student (lecturer_id, subject_id, student_id) VALUES
                                                                               (2, 'CS101', 4),
                                                                               (2, 'CS201', 4);

-- Insert data into the lecturer_subject table
INSERT INTO lecturer_subject (lecturer_id, subject_id) VALUES
                                                           (2, 'CS101'),
                                                           (2, 'CS201');

-- Insert data into the subject_semester table
INSERT INTO subject_semester (subject_id, semester_id) VALUES
                                                           ('CS101', 1),
                                                           ('CS201', 1);







