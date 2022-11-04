DROP DATABASE IF EXISTS projeto_petshop;
CREATE DATABASE projeto_petshop;
USE projeto_petshop;

CREATE TABLE animais(
	ID 			INT PRIMARY KEY AUTO_INCREMENT,
    Nome 		VARCHAR(40) NOT NULL,
    Especie 	VARCHAR(40) NOT NULL,
    Peso 		DECIMAL(10,2) NOT NULL CHECK(Peso > 0),
    Cor 		VARCHAR(50) NOT NULL,
    Data_nasc 	DATE NOT NULL
);

CREATE TABLE veterinario(
	ID 			INT PRIMARY KEY AUTO_INCREMENT,
    Nome		VARCHAR(100) NOT NULL,
    Cpf			VARCHAR(14) NOT NULL,
    Senha		VARCHAR(40) NOT NULL,
    Data_nasc	DATE NOT NULL
);

INSERT INTO `veterinario`(`Nome`, `Cpf`, `Senha`, `Data_nasc`) VALUES ('Admin', '111.111.111-11', '21232f297a57a5a743894a0e4a801fc3', '1999-01-01');