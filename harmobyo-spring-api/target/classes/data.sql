INSERT INTO endereco (logradouro, numero, complemento, cidade, bairro, estado, cep)
VALUES ('Rua A', '100', 'Apto 101', 'São Paulo', 'Vila Madalena', 'SP', '05410-001'),
       ('Rua B', '200', NULL, 'Belo Horizonte', 'Savassi', 'MG', '30140-010'),
       ('Avenida C', '500', 'Sala 501', 'Rio de Janeiro', 'Copacabana', 'RJ', '22020-001'),
       ('Rua D', '300', 'Apto-101', 'Porto Alegre', 'Moinhos de Vento', 'RS', '90430-090'),
       ('Rua E', '150', NULL, 'Fortaleza', 'Aldeota', 'CE', '60140-160'),
       ('Rua F', '400', 'Casa 1', 'Curitiba', 'Batel', 'PR', '80240-000');

-- Inserts para usuários com categoria "Aluno"
INSERT INTO usuario (nome, email, cpf, sexo, senha, data_nasc, telefone, endereco_id, categoria, bibliografia, ativo, autenticado, data_criacao)
VALUES ('João Silva', 'joao.silva@gmail.com', '123.456.789-10', 'Masculino', '$2a$10$/5q27KS5Pq3BsrEFoxwPbuVE2v0PZJg.c6MCi2yKu8j574oR63YqG', '1999-01-01', '(11) 99999-9999', 1, 'Aluno', '', true, true, CURRENT_TIMESTAMP),
       ('Maria Souza', 'maria.souza@hotmail.com', '987.654.321-10', 'Feminino', '$2a$10$/5q27KS5Pq3BsrEFoxwPbuVE2v0PZJg.c6MCi2yKu8j574oR63YqG', '1998-05-20', '(11) 99999-9999', 2, 'Aluno', '', true, true, CURRENT_TIMESTAMP),
       ('Pedro Santos', 'pedro.santos@yahoo.com.br', '456.789.123-10', 'Masculino', '$2a$10$/5q27KS5Pq3BsrEFoxwPbuVE2v0PZJg.c6MCi2yKu8j574oR63YqG', '2000-10-15', '(11) 99999-9999', 3, 'Aluno', '', true, true,  CURRENT_TIMESTAMP);


-- Inserts para usuários com categoria "Professor"
INSERT INTO usuario (nome, email, cpf, sexo, senha, data_nasc, telefone, endereco_id, categoria, bibliografia, ativo, autenticado, data_criacao)
VALUES
    ('Luciana Lima', 'luciana.lima@gmail.com', '471.380.628-50', 'Feminino', '$2a$10$/5q27KS5Pq3BsrEFoxwPbuVE2v0PZJg.c6MCi2yKu8j574oR63YqG', '1985-03-10', '(11) 99999-9999', 4, 'Professor', '', true, true, CURRENT_TIMESTAMP),
    ('Carlos Silva', 'carlos.silva@hotmail.com', '260.973.558-82', 'Masculino', '$2a$10$/5q27KS5Pq3BsrEFoxwPbuVE2v0PZJg.c6MCi2yKu8j574oR63YqG', '1978-12-31', '(11) 99999-9999', 5, 'Professor', '', true, true,  CURRENT_TIMESTAMP),
    ('Patrícia Almeida', 'patricia.almeida@yahoo.com.br', '999.888.777-66', 'Feminino', '$2a$10$/5q27KS5Pq3BsrEFoxwPbuVE2v0PZJg.c6MCi2yKu8j574oR63YqG', '1980-07-05', '(11) 99999-9999', 6, 'Professor', '', true, true,  CURRENT_TIMESTAMP);

-- Inserts para usuários com categoria "Administrador"
INSERT INTO usuario (nome, email, cpf, sexo, senha, data_nasc, telefone, endereco_id, categoria, bibliografia, ativo, autenticado)
VALUES
    ('Administrador', 'admin@gmail.com', '123.456.789-00', 'Masculino', '$2a$10$/5q27KS5Pq3BsrEFoxwPbuVE2v0PZJg.c6MCi2yKu8j574oR63YqG', '1999-01-01', '(11) 99999-9999', 1, 'Administrador', '', true, true);

-- Inserts para Experiencias --
INSERT INTO experiencia (titulo, descricao, professor_fk)
VALUES ('Violinista - Orquestra Harmonyo', 'Violinista da orquestra Harmonyo de 2021 a 2023', 4),
       ('Violinista - Orquestra Harmonyo 2.0', 'Violinista da orquestra Harmonyo de 2021 a 2023', 4);

-- Inserts para naipe --
INSERT INTO naipe (descricao)
VALUES ('Corda'),
       ('Madeira'),
       ('Metal'),
       ('Percurssão');

-- Inserts para intrumento --
INSERT INTO instrumento (nome, naipe_id)
       -- Cordas --
VALUES ('Violão', 1),
       ('Cavaquinho', 1),
       ('Guitarra', 1),
       ('Bandolim', 1),
       ('Mandolin', 1),
       ('Contrabaixo', 1),
       ('Viola', 1),
       ('Banjo', 1),
       ('Ukulele', 1),
       ('Rabeca', 1),
       ('Piano', 1),
       -- Madeiras --
       ('Flauta', 2),
       ('Flautim', 2),
       ('Oboé', 2),
       ('Fagote', 2),
       ('Contrafagote', 2),
       ('Clarinete', 2),
       ('Corne inglês', 2),
       ('Saxofone', 2),
       -- Metal --
       ('Trompete', 3),
       ('Trompa', 3),
       ('Trombone', 3),
       ('Tuba', 3),
       -- Percussão --
       ('Agogô', 4),
       ('Bateria', 4),
       ('Berimbau de boca', 4),
       ('Beringuelo', 4),
       ('Caixa', 4),
       ('Cajon', 4),
       ('Caxixi', 4),
       ('Chocalho', 4),
       ('Cuíca', 4),
       ('Djembe', 4),
       ('Ganzá', 4),
       ('Kalimba', 4),
       ('Maracá', 4),
       ('Pandeiro', 4),
       ('Tambor', 4),
       ('Tamborim', 4),
       ('Tímpanos', 4),
       ('Xequeré', 4),
       ('Conga', 4),
       ('Bongô', 4),
       ('Djembê', 4),
       ('Surdo', 4),
       ('Timbales', 4),
       ('Xilofone', 4),
       ('Marimba', 4),
       ('Triângulo', 4),
       ('Cowbell', 4);

-- Insert para os intrumentos do professor --
INSERT INTO professor_instrumento (professor_fk, instrumento_fk, nivel_conhecimento, empresta_instrumento)
VALUES
    (4, 2, 'Avançado', false),
    (4, 3, 'Intermediário', false),
    (4, 7, 'Intermediário', true),
    (5, 4, 'Intermediário', true),
    (5, 1, 'Avançado', true),
    (5, 12, 'Avançado', false),
    (6, 11, 'Avançado', true),
    (6, 10, 'Intermediário', false);

-- Insert para os intrumentos do aluno --
INSERT INTO aluno_instrumento (aluno_fk, instrumento_fk, nivel_conhecimento)
VALUES (1, 2, 'Basico'),
       (1, 3, 'Intermediário'),
       (2, 12, 'Basico'),
       (2, 11, 'Intermediário'),
       (2, 10, 'Intermediário'),
       (3, 4, 'Basico');


INSERT INTO status (descricao)
VALUES
    ('Pendente'),
    ('Confirmado'),
    ('Cancelado'),
    ('Recusado'),
    ('Concluído'),
    ('Aguardando Pagamento');
INSERT INTO aula (professor_fk, valor_aula, instrumento_fk )
VALUES
    (4,'50.00', 1),
    (4, '75.00',2),
    (4,'100.00',3),

    (5, '60.00', 2),
    (6, '70.00', 3);

INSERT INTO Pedido (aluno_fk, professor_fk, status_fk, aula_fk, hora_criacao, hora_resposta, data_aula)
VALUES
    (1, 4, 5, 1, '2023-08-10 10:00:00', '2023-08-10 15:00:00', '2023-08-20 10:00:00'),
    (1, 4, 5, 1, '2023-08-10 10:00:00', '2023-08-10 15:00:00', '2023-08-27 20:00:00'),
    (1, 5, 4, 2, '2023-08-10 10:00:00', '2023-08-10 15:00:00', '2023-08-20 10:00:00'),
    (1, 5, 5, 2, '2023-08-10 10:00:00', '2023-08-10 15:00:00', '2023-08-27 20:00:00'),
    (1, 6, 4, 3, '2023-08-10 10:00:00', '2023-08-10 15:00:00', '2023-08-20 10:00:00'),
    (1,4, 5, 1, '2023-08-01 10:00:00', '2023-08-01 15:00:00', '2023-08-15 10:00:00'),
    (1, 4, 3, 1, '2023-09-03 10:00:00', '2023-09-03 15:00:00', '2023-09-18 10:00:00'),
    (1,4,4, 2, '2023-09-05 10:00:00', '2023-09-05 15:00:00', '2023-09-20 10:00:00'),
    (1,4,5, 2, '2023-09-09 10:00:00', '2023-09-09 15:00:00', '2023-09-25 20:00:00'),
    (1,4, 3, 3, '2023-09-10 10:00:00', '2023-09-10 15:00:00', '2023-09-27 10:00:00'),
    (1, 4, 4, 3, '2023-09-12 10:00:00', '2023-09-12 15:00:00', '2023-09-28 20:00:00'),
    (1, 6, 5, 3, '2023-09-10 10:00:00', '2023-09-10 15:00:00', '2023-09-27 20:00:00');

INSERT
INTO
    Avaliacao(valor, comentario, usuario_avaliado_fk, usuario_avaliador_fk, data_avaliacao, pedido_fk)
VALUES
    (5, 'Ótimo professor!', 4, 1, '2023-05-20', 2),
    (3, 'Ótimo professor!', 5, 1, '2023-05-20', 4),
    (1.5, 'Ótimo professor!', 6, 1, '2023-05-20', 6);