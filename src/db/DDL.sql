-- MySQL Script generated by MySQL Workbench
-- Sun Oct 24 18:09:42 2021
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema csdb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema csdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `csdb` DEFAULT CHARACTER SET utf8 ;
USE `csdb` ;

-- -----------------------------------------------------
-- Table `csdb`.`classroom`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `csdb`.`classroom` ;

CREATE TABLE IF NOT EXISTS `csdb`.`classroom` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(150) NULL,
  `name` VARCHAR(45) NULL,
  `coursetype` INT NULL COMMENT 'is laboratory',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `csdb`.`courses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `csdb`.`courses` ;

CREATE TABLE IF NOT EXISTS `csdb`.`courses` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `title` VARCHAR(150) NULL,
  `domain_value_id` INT NULL COMMENT 'Specialization\n',
  `lecture_hours` INT NULL DEFAULT 0,
  `lab_hours` INT NULL DEFAULT 0,
  `lecture_room` INT NULL,
  `lab_room` INT NULL,
  `section` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `csdb`.`domainobject`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `csdb`.`domainobject` ;

CREATE TABLE IF NOT EXISTS `csdb`.`domainobject` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `csdb`.`domainvalue`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `csdb`.`domainvalue` ;

CREATE TABLE IF NOT EXISTS `csdb`.`domainvalue` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(150) NULL,
  `short_description` VARCHAR(45) NULL,
  `domain_object_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_domainvalue_domainobject_idx` (`domain_object_id` ASC) VISIBLE,
  CONSTRAINT `fk_domainvalue_domainobject`
    FOREIGN KEY (`domain_object_id`)
    REFERENCES `csdb`.`domainobject` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `csdb`.`roomtype`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `csdb`.`roomtype` ;

CREATE TABLE IF NOT EXISTS `csdb`.`roomtype` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `domainvalue_id` INT NOT NULL,
  `classroom_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_roomtype_domainvalue1_idx` (`domainvalue_id` ASC) VISIBLE,
  INDEX `fk_roomtype_classroom1_idx` (`classroom_id` ASC) VISIBLE,
  CONSTRAINT `fk_roomtype_domainvalue1`
    FOREIGN KEY (`domainvalue_id`)
    REFERENCES `csdb`.`domainvalue` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_roomtype_classroom1`
    FOREIGN KEY (`classroom_id`)
    REFERENCES `csdb`.`classroom` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `csdb`.`schedule`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `csdb`.`schedule` ;

CREATE TABLE IF NOT EXISTS `csdb`.`schedule` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `semester_id` INT NULL,
  `course_id` INT NULL,
  `professor_id` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `csdb`.`scheduledetail`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `csdb`.`scheduledetail` ;

CREATE TABLE IF NOT EXISTS `csdb`.`scheduledetail` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `schedule_id` INT NULL,
  `classroom_id` INT NULL,
  `day` INT NULL COMMENT 'Day of week\n',
  `start_time` TIME NULL,
  `end_time` TIME NULL,
  `coursetype` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `csdb`.`scheduleprocess`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `csdb`.`scheduleprocess` ;

CREATE TABLE IF NOT EXISTS `csdb`.`scheduleprocess` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `status` INT NULL,
  `totalcourses` INT NULL,
  `completedcourses` INT NULL DEFAULT 0,
  `coursenotscheduled` INT NULL DEFAULT 0,
  `semester_id` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `csdb`.`semester`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `csdb`.`semester` ;

CREATE TABLE IF NOT EXISTS `csdb`.`semester` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(150) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `csdb`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `csdb`.`user` ;

CREATE TABLE IF NOT EXISTS `csdb`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(150) NULL,
  `password` VARCHAR(45) NULL,
  `role` INT NULL,
  `allocated_hours` INT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `csdb`.`usertype`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `csdb`.`usertype` ;

CREATE TABLE IF NOT EXISTS `csdb`.`usertype` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `domainvalue_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_usertype_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_usertype_domainvalue1_idx` (`domainvalue_id` ASC) VISIBLE,
  CONSTRAINT `fk_usertype_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `csdb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usertype_domainvalue1`
    FOREIGN KEY (`domainvalue_id`)
    REFERENCES `csdb`.`domainvalue` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
