-- DOMAIN OBJECT
INSERT INTO `csdb`.`domainobject`(`description`)VALUES('CATEGORY');

-- DOMAIN VALUE
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('Chemistry','CHEMISTRY',1);
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('Computer Programming','COMPUTER',1);
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('Mathematics','MATH',1);
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('English','ENGLISH',1);
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('Social Science','SOCIAL SCIENCE',1);
INSERT INTO `csdb`.`domainvalue`(`description`,`short_description`,`domain_object_id`)VALUES ('Physical','PHYSICAL',1);


-- SEMESTER
INSERT INTO `csdb`.`semester`(`description`)VALUES('1st Semester 2020-2021');
INSERT INTO `csdb`.`semester`(`description`)VALUES('2nd Semester 2020-2021');

-- COURSES
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`)VALUES('Chem 102','General Chemistry',1,3,3);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`)VALUES('Math 104','Advanced Algebra',3,5,0);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`)VALUES('Math 108','Plane and Spherical Trigonometry',3,3,0);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`)VALUES('Eng 100','English Plus',4,3,0);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`)VALUES('Eng 101','Advanced Grammar and Composition',4,3,0);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`)VALUES('Fil 101','Komunikasyon sa Akademikong Filipino',5,3,0);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`)VALUES('Hum 101','Introduction to Humanities: Art Appreciation',5,3,0);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`)VALUES('PE 101','Physical Fitness, Gymnastics and Aerobics',6,3,0);
INSERT INTO `csdb`.`courses`(`name`,`title`,`domain_value_id`,`lecture_hours`,`lab_hours`)VALUES('SS 101','General Psychology',5,3,0);

-- SCHEDULE
INSERT INTO `csdb`.`schedule`(`semester_id`,`course_id`)VALUES(1,1);


-- USER
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`domain_value_id`,`allocated_hours`)VALUES('Prof Chem 1','abc123',1,1,30);
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`domain_value_id`,`allocated_hours`)VALUES('Prof Chem 2','abc123',1,1,8);
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`domain_value_id`,`allocated_hours`)VALUES('Prof Chem 3','abc123',1,1,15);
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`domain_value_id`,`allocated_hours`)VALUES('Prof IT 1','abc123',1,2,20);
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`domain_value_id`,`allocated_hours`)VALUES('Prof IT 2','abc123',1,2,15);
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`domain_value_id`,`allocated_hours`)VALUES('Prof IT 3','abc123',1,2,10);
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`domain_value_id`,`allocated_hours`)VALUES('Prof Math 1','abc123',1,3,10);
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`domain_value_id`,`allocated_hours`)VALUES('Prof Math 2','abc123',1,3,10);
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`domain_value_id`,`allocated_hours`)VALUES('Prof Math 3','abc123',1,3,12);
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`domain_value_id`,`allocated_hours`)VALUES('Prof Math 5','abc123',1,3,6);
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`domain_value_id`,`allocated_hours`)VALUES('Prof ENG 1','abc123',1,4,10);
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`domain_value_id`,`allocated_hours`)VALUES('Prof ENG 2','abc123',1,4,10);
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`domain_value_id`,`allocated_hours`)VALUES('Prof Social 1','abc123',1,5,20);
INSERT INTO `csdb`.`user`(`name`,`password`,`role`,`domain_value_id`,`allocated_hours`)VALUES('Prof PE 1','abc123',1,6,5);


