
    --- INSERT --- INSERT --- INSERT --- INSERT

    -- Insert data into the semester table
    INSERT INTO semester (semester_id, semester_name, date_start, date_end, admin_id) VALUES
                                                                                          (1, 'Spring 2023', '2023-01-01', '2023-05-31', 1),
                                                                                          (2, 'Fall 2023', '2023-08-01', '2023-12-31', 1),
                                                                                          (3, 'Summer 2023', '2023-06-01', '2023-07-31', 1);
    --                                                                                       (4, 'Spring 2022', '2022-01-01', '2022-05-31', 1),
    --                                                                                       (5, 'Summer 2022', '2022-06-01', '2022-07-31', 1),
    --                                                                                       (6, 'Fall 2022', '2022-08-01', '2022-12-31', 1),
    --                                                                                       (7, 'Summer 2023', '2023-06-01', '2023-07-31', 1);

    INSERT INTO major (major_id, major_name, admin_id) VALUES
                                                           (1, 'Chinese', 1),
                                                           (2, 'Computer Science', 1),
                                                           (3, 'Computing Fundamental', 1),
                                                           (4, 'English', 1),
                                                           (5, 'English Preparation Course', 1),
                                                           (6, 'Finance', 1),
                                                           (7, 'Graduate', 1),
                                                           (8, 'Graphic Design', 1),
                                                           (9, 'Hospitality Management', 1),
                                                           (10, 'Information Assurance', 1),
                                                           (11, 'Information Technology Specialization', 1),
                                                           (12, 'International Business', 1),
                                                           (13, 'Japanese', 1),
                                                           (14, 'LAB', 1),
                                                           (15, 'Management', 1),
                                                           (16, 'Mathematics', 1),
                                                           (17, 'Multimedia Communication', 1),
                                                           (18, 'On the job training', 1),
                                                           (19, 'Physical Training', 1),
                                                           (20, 'Soft Skill', 1),
                                                           (21, 'Start Your Business', 1),
                                                           (22, 'Traditional Instrument', 1),
                                                           (23, 'Vietnamese', 1);

    INSERT INTO subject_major (subject_id, major_id, status)
    SELECT s.subject_id, m.major_id, 'OPEN'
    FROM subject s
             CROSS JOIN major m;
    DELETE FROM subject_major;

    INSERT INTO subject_major (subject_id, major_id, status) VALUES
                                                                 ('MMA301', 1, 'OPEN'),
                                                                 ('PMG201c', 2, 'OPEN'),
                                                                 ('PMG202c', 2, 'OPEN'),
                                                                 ('PRN211', 3, 'OPEN'),
                                                                 ('PRN221', 3, 'OPEN'),
                                                                 ('PRU211m', 2, 'OPEN'),
                                                                 ('SAP331', 6, 'OPEN'),
                                                                 ('SAP341', 6, 'OPEN'),
                                                                 ('SDN301m', 2, 'OPEN'),
                                                                 ('SWD392', 8, 'OPEN'),
                                                                 ('SWE201c', 2, 'OPEN'),
                                                                 ('SWP391', 2, 'OPEN'),
                                                                 ('SWR302', 2, 'OPEN'),
                                                                 ('SWT301', 2, 'OPEN'),
                                                                 ('SYB302c', 7, 'OPEN'),
                                                                 ('WDU203c', 8, 'OPEN');


    -- Insert data into the subject table
    INSERT INTO subject (subject_id, subject_name, admin_id) VALUES
                                                                 ('MMA301',  'Multiplatform Mobile App Development (MMA301)', 1),
                                                                 ('PMG201c',  'Project Management (PMG201c)', 1),
                                                                 ('PMG202c', 'Project Management (PMG202c)', 1),
                                                                 ('PRN211', 'Basic Cross-Platform Application (PRN211)', 1),
                                                                 ('PRN221',  'Advanced Cross-Platform Application (PRN221)', 1),
                                                                 ('PRU211m','C# Programming and Unity (PRU211m)', 1),
                                                                 ('SAP331',  'SAP Configuration (SAP331)', 1),
                                                                 ('SAP341',  'SAP Application Development with ABAP (SAP341)', 1),
                                                                 ('SDN301m', 'Server-Side development (SDN301m)', 1),
                                                                 ('SWD392',  'SW Architecture and Design (SWD392)', 1),
                                                                 ('SWE201c',  'Introduction to Software Engineering (SWE201c)', 1),
                                                                 ('SWP391',  'Software development project (SWP391)', 1),
                                                                 ('SWR302',  'Software Requirements (SWR302)', 1),
                                                                 ('SWT301',  'Software Testing (SWT301)', 1),
                                                                 ('SYB302c',  'Entrepreneurship (SYB302c)', 1),
                                                                 ('WDU203c', 'The UI/UX Design (WDU203c)', 1);


    INSERT INTO subject_semester (subject_id, semester_id) VALUES
                                                               ('MMA301', 1),       -- Spring 2023
                                                               ('PMG201c', 2),      -- Fall 2023
                                                               ('PMG202c', 2),      -- Fall 2023
                                                               ('PRN211', 1),       -- Spring 2023
                                                               ('PRN221', 2),       -- Fall 2023
                                                               ('PRU211m', 1),      -- Spring 2023
                                                               ('SAP331', 3),       -- Summer 2023
                                                               ('SAP341', 3),       -- Summer 2023
                                                               ('SDN301m', 2),      -- Fall 2023
                                                               ('SWD392', 2),       -- Fall 2023
                                                               ('SWE201c', 1),      -- Spring 2023
                                                               ('SWP391', 2),       -- Fall 2023
                                                               ('SWR302', 1),       -- Spring 2023
                                                               ('SWT301', 1),       -- Spring 2023
                                                               ('SYB302c', 2),      -- Fall 2023
                                                               ('WDU203c', 2);      -- Fall 2023

    -- Insert data into the teaching_schedule table
    INSERT INTO teaching_schedule (lecturer_id, subject_id, room_id, date, time, date_of_week, slot_id, status)
    VALUES
    -- Row 1
    (2, 'MMA301', 1, '2023-10-25', '09:30:00', 'Monday', 2, 'Scheduled'),

    -- Row 2
    (3, 'PMG201c', 2, '2023-10-25', '12:30:00', 'Monday', 3, 'Scheduled'),

    -- Row 3
    (4, 'SAP331', 3, '2023-10-25', '15:00:00', 'Monday', 4, 'Scheduled'),

    -- Row 4
    (5, 'PRU211m', 1, '2023-10-26', '09:30:00', 'Tuesday', 2, 'Scheduled'),

    -- Row 5
    (6, 'SWE201c', 2, '2023-10-26', '12:30:00', 'Tuesday', 3, 'Scheduled');
    -- Continue to insert more rows as needed
    -- Insert 10 more rows into the teaching_schedule table
    -- Insert 20 more rows into the teaching_schedule table with specific room_id values
    INSERT INTO teaching_schedule (lecturer_id, subject_id, room_id, date, time, date_of_week, slot_id, status)
    VALUES
    -- Row 21
    (2, 'MMA301', '101', '2023-11-01', '09:30:00', 'Monday', 2, 'Scheduled'),

    -- Row 22
    (3, 'PMG201c', '102', '2023-11-01', '12:30:00', 'Monday', 3, 'Scheduled'),

    -- Row 23
    (4, 'SAP331', '103', '2023-11-01', '15:00:00', 'Monday', 4, 'Scheduled'),

    -- Row 24
    (5, 'PRU211m', '104', '2023-11-02', '09:30:00', 'Tuesday', 2, 'Scheduled'),

    -- Row 25
    (6, 'SWE201c', '201', '2023-11-02', '12:30:00', 'Tuesday', 3, 'Scheduled'),

    -- Row 26
    (7, 'SWR302', '202', '2023-11-02', '15:00:00', 'Tuesday', 4, 'Scheduled'),

    -- Row 27
    (2, 'SWT301', '203', '2023-11-03', '09:30:00', 'Wednesday', 2, 'Scheduled'),

    -- Row 28
    (3, 'SYB302c', '204', '2023-11-03', '12:30:00', 'Wednesday', 3, 'Scheduled'),

    -- Row 29
    (4, 'WDU203c', '301', '2023-11-03', '15:00:00', 'Wednesday', 4, 'Scheduled'),

    -- Row 30
    (5, 'PRN221', '302', '2023-11-04', '09:30:00', 'Thursday', 2, 'Scheduled'),

    -- Row 31
    (6, 'SWR302', '303', '2023-11-04', '12:30:00', 'Thursday', 3, 'Scheduled'),

    -- Row 32
    (7, 'SWT301', '304', '2023-11-04', '15:00:00', 'Thursday', 4, 'Scheduled'),

    -- Row 33
    (2, 'PRU211m', '401NVH', '2023-11-05', '09:30:00', 'Friday', 2, 'Scheduled'),

    -- Row 34
    (3, 'SAP341', '402NVH', '2023-11-05', '12:30:00', 'Friday', 3, 'Scheduled'),

    -- Row 35
    (4, 'SWE201c', '403NVH', '2023-11-05', '15:00:00', 'Friday', 4, 'Scheduled'),

    -- Row 36
    (5, 'MMA301', '404NVH', '2023-11-06', '09:30:00', 'Saturday', 2, 'Scheduled'),

    -- Row 37
    (6, 'PRN211', '601NVH', '2023-11-06', '12:30:00', 'Saturday', 3, 'Scheduled'),

    -- Row 38
    (7, 'SWP391', '602NVH', '2023-11-06', '15:00:00', 'Saturday', 4, 'Scheduled'),

    -- Row 39
    (2, 'SDN301m', '603NVH', '2023-11-07', '09:30:00', 'Sunday', 2, 'Scheduled'),

    -- Row 40
    (3, 'SAP341', '604NVH', '2023-11-07', '12:30:00', 'Sunday', 3, 'Scheduled');
    -- Continue to insert more rows as needed





