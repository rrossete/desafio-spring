CREATE TABLE IF NOT EXISTS CP_Cidade(
	id SERIAL PRIMARY KEY ,
	nome varchar(200) not null,
	estado varchar(200) not null
);



CREATE TABLE IF NOT EXISTS CP_Cliente(
	id SERIAL PRIMARY KEY ,
	nome varchar(200) not null,
	sexo varchar(50) null,
	data_nascimento date null,
	idade int null,
	cidade_id int not null
);

ALTER TABLE CP_Cliente ADD CONSTRAINT FK_Cliente_Cidade FOREIGN KEY (cidade_id) REFERENCES Cp_Cidade(id);

INSERT INTO Cp_Cidade (id, nome, estado) values (1, 'São Paulo', 'SP');
INSERT INTO CP_Cliente (id, nome, sexo, data_nascimento, idade, cidade_id) values (1, 'José Maria', 'Masculino', '1995-08-13', 25, 1);