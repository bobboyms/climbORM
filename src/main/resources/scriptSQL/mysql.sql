CREATE TABLE `teste`.`tb_pessoa` ( `id` INT NOT NULL AUTO_INCREMENT , `nome` VARCHAR(200) NULL , `endereco_comercial` VARCHAR(200) NULL , `idade` INT NULL , `altura` DECIMAL(10,5) NULL , `quantidade_quilos` DOUBLE NULL , `casado` BOOLEAN NULL , `id_endereco` INT NULL , `foto` BINARY BINARY NULL , `lista_emails` JSON NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;

CREATE TABLE `teste`.`tb_cidade` ( `id` INT NOT NULL AUTO_INCREMENT , `nome_da_cidade` VARCHAR(100) NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;

CREATE TABLE `teste`.`tb_endereco` ( `id` INT NOT NULL AUTO_INCREMENT , `nome_da_rua` TEXT NULL , `id_cidade` INT NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;

