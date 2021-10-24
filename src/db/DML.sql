-- DOMAIN OBJECT
INSERT INTO `csdb`.`domainobject`(`description`)VALUES('CATEGORY');

-- DOMAIN VALUE
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('GENERAL','Math',1);
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('GENERAL','Sciences (Chem, Physics, Bio)',1);
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('GENERAL','Education (English, Filipino etc.)',1);
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('GENERAL','Drafting',1);
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('GENERAL','Basic Programming',1);
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('SCIENCE','Architecture',1);
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('SOCIAL SCIENCE','Social Science',1);
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('ARTS','Fine Arts',1);
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('ARTS','Interior Design',1);
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('ENGINEERING','Chemical Eng',1);
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('ENGINEERING','Civil Eng',1);
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('ENGINEERING','Computer Eng',1);
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('ENGINEERING','Electrical Eng',1);
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('ENGINEERING','Electronics and Communications Eng',1);
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('ENGINEERING','Food Eng',1);
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('ENGINEERING','Industrial Eng',1);
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('ENGINEERING','Instrumentation and Control Eng',1);
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('ENGINEERING','Mechanical Eng',1);
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('ENGINEERING','Mecatronics Eng',1);
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('ENGINEERING','Petroleum Eng',1);
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('ENGINEERING','Sanitary Eng',1);


-- CLASSROOM
INSERT INTO `csdb`.`classroom`(`description`,`name`,`coursetype`)VALUES('Chemical Engineering','Laboratory I',1);
INSERT INTO `csdb`.`classroom`(`description`,`name`,`coursetype`)VALUES('Chemical Engineering','Laboratory II',1);
INSERT INTO `csdb`.`classroom`(`description`,`name`,`coursetype`)VALUES('Chemical Engineering','Laboratory III',1);
INSERT INTO `csdb`.`classroom`(`description`,`name`,`coursetype`)VALUES('Chemical Engineering','SFIHM',1);
INSERT INTO `csdb`.`classroom`(`description`,`name`,`coursetype`)VALUES('Chemical Engineering','FIC',1);
INSERT INTO `csdb`.`classroom`(`description`,`name`,`coursetype`)VALUES('Chemical Engineering','UOPS Laboratory',1);
INSERT INTO `csdb`.`classroom`(`description`,`name`,`coursetype`)VALUES('Civil Engineering','Environmental Lab',1);
INSERT INTO `csdb`.`classroom`(`description`,`name`,`coursetype`)VALUES('Civil Engineering','Computer Lab',1);
INSERT INTO `csdb`.`classroom`(`description`,`name`,`coursetype`)VALUES('Civil Engineering','Soil and Construction Materials Testing Lab',1);
INSERT INTO `csdb`.`classroom`(`description`,`name`,`coursetype`)VALUES('Civil Engineering','Hydraulics Lab',1);
INSERT INTO `csdb`.`classroom`(`description`,`name`,`coursetype`)VALUES('Electrical and Computer Engineering','Circuits Laboratory',1);
INSERT INTO `csdb`.`classroom`(`description`,`name`,`coursetype`)VALUES('Electrical and Computer Engineering','Machines Laboratory',1);
INSERT INTO `csdb`.`classroom`(`description`,`name`,`coursetype`)VALUES('Electrical and Computer Engineering','Computer Engineering / Hardware Laboratory',1);
INSERT INTO `csdb`.`classroom`(`description`,`name`,`coursetype`)VALUES('Industrial Engineering','Methods Engineering and Ergonomics Lab',1);
INSERT INTO `csdb`.`classroom`(`description`,`name`,`coursetype`)VALUES('Mechanical and Petroleum Engineering','Boiler Laboratory',1);
INSERT INTO `csdb`.`classroom`(`description`,`name`,`coursetype`)VALUES('Mechanical and Petroleum Engineering','Geology Laboratory',1);
INSERT INTO `csdb`.`classroom`(`description`,`name`,`coursetype`)VALUES('Architecture, Interior Design and Fine Arts','Gallery',1);
INSERT INTO `csdb`.`classroom`(`description`,`name`,`coursetype`)VALUES('General Room','Room 101',0);
INSERT INTO `csdb`.`classroom`(`description`,`name`,`coursetype`)VALUES('General Room','Room 102',0);

-- room type
INSERT INTO `csdb`.`roomtype`(`domainvalue_id`,`classroom_id`)VALUES(1,18);
INSERT INTO `csdb`.`roomtype`(`domainvalue_id`,`classroom_id`)VALUES(2,18);
INSERT INTO `csdb`.`roomtype`(`domainvalue_id`,`classroom_id`)VALUES(3,18);
INSERT INTO `csdb`.`roomtype`(`domainvalue_id`,`classroom_id`)VALUES(1,19);
INSERT INTO `csdb`.`roomtype`(`domainvalue_id`,`classroom_id`)VALUES(2,19);
INSERT INTO `csdb`.`roomtype`(`domainvalue_id`,`classroom_id`)VALUES(3,19);

-- SEMESTER
INSERT INTO `csdb`.`semester`(`description`)VALUES('1st Semester 2020-2021');
INSERT INTO `csdb`.`semester`(`description`)VALUES('2nd Semester 2020-2021');

-- USER
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`allocated_hours`)VALUES('Prof Chem 1','abc123',1,30);
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`allocated_hours`)VALUES('Prof Chem 2','abc123',1,8);
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`allocated_hours`)VALUES('Prof Chem 3','abc123',1,15);
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`allocated_hours`)VALUES('Prof IT 1','abc123',1,20);
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`allocated_hours`)VALUES('Prof IT 2','abc123',1,15);
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`allocated_hours`)VALUES('Prof IT 3','abc123',1,10);
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`allocated_hours`)VALUES('Prof Math 1','abc123',1,10);
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`allocated_hours`)VALUES('Prof Math 2','abc123',1,10);
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`allocated_hours`)VALUES('Prof Math 3','abc123',1,12);
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`allocated_hours`)VALUES('Prof Math 5','abc123',1,6);
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`allocated_hours`)VALUES('Prof ENG 1','abc123',1,10);
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`allocated_hours`)VALUES('Prof ENG 2','abc123',1,10);
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`allocated_hours`)VALUES('Prof Social 1','abc123',1,20);
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`allocated_hours`)VALUES('Prof General Edu','abc123',1,20);

-- USER TYPE
INSERT INTO `csdb`.`usertype`(`user_id`,`domainvalue_id`)VALUES(1,10);
INSERT INTO `csdb`.`usertype`(`user_id`,`domainvalue_id`)VALUES(2,10);
INSERT INTO `csdb`.`usertype`(`user_id`,`domainvalue_id`)VALUES(3,10);
INSERT INTO `csdb`.`usertype`(`user_id`,`domainvalue_id`)VALUES(3,2);
INSERT INTO `csdb`.`usertype`(`user_id`,`domainvalue_id`)VALUES(4,5);
INSERT INTO `csdb`.`usertype`(`user_id`,`domainvalue_id`)VALUES(5,5);
INSERT INTO `csdb`.`usertype`(`user_id`,`domainvalue_id`)VALUES(5,12);
INSERT INTO `csdb`.`usertype`(`user_id`,`domainvalue_id`)VALUES(6,5);
INSERT INTO `csdb`.`usertype`(`user_id`,`domainvalue_id`)VALUES(7,1);
INSERT INTO `csdb`.`usertype`(`user_id`,`domainvalue_id`)VALUES(8,1);
INSERT INTO `csdb`.`usertype`(`user_id`,`domainvalue_id`)VALUES(9,1);
INSERT INTO `csdb`.`usertype`(`user_id`,`domainvalue_id`)VALUES(10,1);
INSERT INTO `csdb`.`usertype`(`user_id`,`domainvalue_id`)VALUES(10,3);
INSERT INTO `csdb`.`usertype`(`user_id`,`domainvalue_id`)VALUES(11,3);
INSERT INTO `csdb`.`usertype`(`user_id`,`domainvalue_id`)VALUES(12,1);
INSERT INTO `csdb`.`usertype`(`user_id`,`domainvalue_id`)VALUES(12,3);
INSERT INTO `csdb`.`usertype`(`user_id`,`domainvalue_id`)VALUES(13,7);
INSERT INTO `csdb`.`usertype`(`user_id`,`domainvalue_id`)VALUES(14,1);
INSERT INTO `csdb`.`usertype`(`user_id`,`domainvalue_id`)VALUES(14,2);
INSERT INTO `csdb`.`usertype`(`user_id`,`domainvalue_id`)VALUES(14,3);


-- COURSES
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('Chem 102','General Chemistry',1,3,3,null,null,'CE101');
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('Chem 102','General Chemistry',1,3,3,null,null,'ComE 101');
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('Math 104','Advanced Algebra',1,5,0,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('Math 108','Plane and Spherical Trigonometry',1,3,0,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('Eng 100','English Plus',3,3,0,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('Eng 101','Advanced Grammar and Composition',3,3,0,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('Fil 101','Komunikasyon sa Akademikong Filipino',3,3,0,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('Hum 101','Introduction to Humanities: Art Appreciation',8,3,0,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('SS 101','General Psychology',3,3,0,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('Chem 103','Chemistry Calculation with Organic Chemistry',1,3,0,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('Draw 101','Engineering Drawing',4,0,3,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('Math 109','Solid Mensuration',1,3,0,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('Math 110','Analytic Geometry',1,3,0,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('Eng 102','Study and Thinking Skills',3,3,0,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('Fil 102','Pagbasa at Pagsulat Tungo sa Pananaliksik',3,3,0,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('Hum 102','Introduction to Philosophy',6,3,0,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('SS 102','Philippine History',3,3,0,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('Math 114','Differential Calculus',1,5,0,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('CAD 201','Computer Aided Drafting',4,0,3,null,8,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('Math 115','Integral Calculus',1,5,0,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('Phy 106','Physicas 1',1,4,3,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('Eng 106','Technical Communication',3,3,0,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('SS 103','Society and Culture with Responsible Parenthood',3,3,0,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('Comp 201','Computer Fundamentals and Programming',5,2,3,null,8,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('Phy 107','Physics 2',1,4,3,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('Hum 104','Logic',6,3,0,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('SS 104','Life and Works of Rizal',6,3,0,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('SS 106','Politics and Governance with Philippine Constitution',6,3,0,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('CpE 301','Discrete Mathematics',12,3,0,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('CpE 302','Computer Hardware Fundamentals',12,0,3,null,8,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('CpE 303','Data Structures and Algorithm Analysis',12,3,3,null,8,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('ECE 301','Electronics Devices and Circuits',14,3,3,null,11,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('EE 301','Electrical Circuits',13,3,3,null,11,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('Math 120','Differential Equations',1,3,0,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('IE 307','Engineering Economy',16,3,0,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('Mech 301','Statics of Rigid Bodies',18,3,0,null,null,null);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`,`lecture_room`,`lab_room`,`section`)VALUES('Cpe 304','Logic Circuits and Switching Theory',12,3,3,null,null,null);














