-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema projeto_veterinaria
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema projeto_veterinaria
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `projeto_veterinaria`;
CREATE SCHEMA IF NOT EXISTS `projeto_veterinaria` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `projeto_veterinaria` ;

-- -----------------------------------------------------
-- Table `projeto_veterinaria`.`cliente`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `projeto_veterinaria`.`cliente` ;

CREATE TABLE IF NOT EXISTS `projeto_veterinaria`.`cliente` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(40) NOT NULL,
  `Endereco` VARCHAR(40) NOT NULL,
  `Animal` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `projeto_veterinaria`.`veterinario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `projeto_veterinaria`.`veterinario` ;

CREATE TABLE IF NOT EXISTS `projeto_veterinaria`.`veterinario` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(100) NOT NULL,
  `Cpf` VARCHAR(14) NOT NULL,
  `Senha` VARCHAR(40) NOT NULL,
  `Data_nasc` DATE NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `projeto_veterinaria`.`veterinario`
-- -----------------------------------------------------
START TRANSACTION;
USE `projeto_veterinaria`;
INSERT INTO `projeto_veterinaria`.`veterinario` (`ID`, `Nome`, `Cpf`, `Senha`, `Data_nasc`) VALUES (DEFAULT, 'Admin', '111.111.111-11', 'admin', '2000-01-01');

COMMIT;

