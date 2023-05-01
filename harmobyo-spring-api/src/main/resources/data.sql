INSERT INTO endereco (logradouro, numero, complemento, cidade, bairro, estado, cep)
VALUES ('Rua A', '100', 'Apto 101', 'São Paulo', 'Vila Madalena', 'SP', '05410-001'),
       ('Rua B', '200', NULL, 'Belo Horizonte', 'Savassi', 'MG', '30140-010'),
       ('Avenida C', '500', 'Sala 501', 'Rio de Janeiro', 'Copacabana', 'RJ', '22020-001'),
       ('Rua D', '300', NULL, 'Porto Alegre', 'Moinhos de Vento', 'RS', '90430-090'),
       ('Rua E', '150', NULL, 'Fortaleza', 'Aldeota', 'CE', '60140-160'),
       ('Rua F', '400', 'Casa 1', 'Curitiba', 'Batel', 'PR', '80240-000');

-- Inserts para usuários com categoria "Aluno"
INSERT INTO usuario (nome, email, cpf, sexo, senha, data_nasc, telefone, endereco_id, categoria, ativo, autenticado, ultima_vez_online)
VALUES ('João Silva', 'joao.silva@gmail.com', '123.456.789-10', 'Masculino', '123456', '1999-01-01', '(11) 99999-9999', 1, 'Aluno', true, true, CURRENT_TIMESTAMP);

INSERT INTO usuario (nome, email, cpf, sexo, senha, data_nasc, telefone, endereco_id, categoria, ativo, autenticado, ultima_vez_online)
VALUES ('Maria Souza', 'maria.souza@hotmail.com', '987.654.321-10', 'Feminino', '123456', '1998-05-20', '(11) 99999-9999', 2, 'Aluno', true, true, CURRENT_TIMESTAMP);

INSERT INTO usuario (nome, email, cpf, sexo, senha, data_nasc, telefone, endereco_id, categoria, ativo, autenticado, ultima_vez_online)
VALUES ('Pedro Santos', 'pedro.santos@yahoo.com.br', '456.789.123-10', 'Masculino', '123456', '2000-10-15', '(11) 99999-9999', 3, 'Aluno', true, true, CURRENT_TIMESTAMP);


-- Inserts para usuários com categoria "Professor"
INSERT INTO usuario (nome, email, cpf, sexo, senha, data_nasc, telefone, endereco_id, categoria, ativo, autenticado, ultima_vez_online)
VALUES ('Luciana Lima', 'luciana.lima@gmail.com', '111.222.333-44', 'Feminino', '123456', '1985-03-10', '(11) 99999-9999', 4, 'Professor', true, true, CURRENT_TIMESTAMP);

INSERT INTO usuario (nome, email, cpf, sexo, senha, data_nasc, telefone, endereco_id, categoria, ativo, autenticado, ultima_vez_online)
VALUES ('Carlos Silva', 'carlos.silva@hotmail.com', '555.666.777-88', 'Masculino', '123456', '1978-12-31', '(11) 99999-9999', 5, 'Professor', true, true, CURRENT_TIMESTAMP);

INSERT INTO usuario (nome, email, cpf, sexo, senha, data_nasc, telefone, endereco_id, categoria, ativo, autenticado, ultima_vez_online)
VALUES ('Patrícia Almeida', 'patricia.almeida@yahoo.com.br', '999.888.777-66', 'Feminino', '123456', '1980-07-05', '(11) 99999-9999', 6, 'Professor', true, true, CURRENT_TIMESTAMP);
