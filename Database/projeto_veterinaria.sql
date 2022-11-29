SET foreign_key_checks = 0;
drop schema if exists `projeto_veterinaria`;
CREATE DATABASE `projeto_veterinaria` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `mydb` ;

CREATE TABLE IF NOT EXISTS `projeto_veterinaria`.`cliente` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Razao_social` VARCHAR(40) NOT NULL,
  `Cnpj` VARCHAR(18) NOT NULL,
  `Endereco` MEDIUMTEXT NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `Razao_social` (`Razao_social` ASC) VISIBLE,
  UNIQUE INDEX `Cnpj_UNIQUE` (`Cnpj` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `projeto_veterinaria`.`veterinario` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(100) NOT NULL,
  `Cpf` VARCHAR(14) NOT NULL,
  `Senha` VARCHAR(40) NOT NULL,
  `Email` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `Cpf_UNIQUE` (`Cpf` ASC) VISIBLE,
  UNIQUE INDEX `Email_UNIQUE` (`Email` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;



CREATE TABLE IF NOT EXISTS `projeto_veterinaria`.`servico` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Cliente` INT NOT NULL,
  `Veterinario` INT NOT NULL,
  `Tipo` VARCHAR(45) NOT NULL,
  `Horas` INT NOT NULL,
  `Descricao` MEDIUMTEXT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_cliente_idx` (`Cliente` ASC) VISIBLE,
  INDEX `fk_veterinario_idx` (`Veterinario` ASC) VISIBLE,
  CONSTRAINT `fk_cliente`
    FOREIGN KEY (`Cliente`)
    REFERENCES `projeto_veterinaria`.`cliente` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_veterinario`
    FOREIGN KEY (`Veterinario`)
    REFERENCES `projeto_veterinaria`.`veterinario` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `projeto_veterinaria` ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


START TRANSACTION;
USE `projeto_veterinaria`;
INSERT INTO `projeto_veterinaria`.`veterinario` (`ID`, `Nome`, `Cpf`, `Senha`, `Email`) VALUES (DEFAULT, 'Admin', '111.111.111-11', 'admin', 'admin');

SET foreign_key_checks = 1;
COMMIT;

delimiter $

create procedure insertServico(
	nome_vet varchar(100),
    nome_cli varchar(40),
    tipo_serv varchar(45),
    horas_serv int,
    desc_serv mediumtext
)

begin
	set @id_vet = (select id from veterinario where Nome = nome_vet);
    set @id_cli = (select id from cliente where Razao_social = nome_cli);
    
    insert into servico (Cliente, Veterinario, Tipo, Horas, Descricao) values (@id_cli, @id_vet, tipo_serv, horas_serv, desc_serv);
end$

delimiter ;


select * from servico;
select * from cliente;
select * from veterinario;