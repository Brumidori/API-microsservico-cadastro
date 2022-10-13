INSERT INTO turma (nome) VALUES
('junho'),
('julho'),
('agosto');

INSERT INTO squad (nome, nome_abreviado, funcionalidade, descricao) VALUES
('Capitão América', 'CA', 'Cadastro de Filmes', NULL),
('Capitã Marvel', 'CM', 'Cadastro de Usuários', NULL),
('Pantera Negra', 'PN', 'Cadastro de Contas', NULL),
('Pantera Negra Estrutural', 'PNE', 'Estrutural', NULL),
('Viúva Negra', 'VN', 'Mobile', NULL),
('Homem de Ferro', 'HF', 'CapSkill Simulador de Perguntas', NULL);

INSERT INTO gestor (id, email, nome, senha, permissao, cor, data_cadastro) VALUES
(usuario_key_generator.NEXTVAL, 'maria@gmail.com', 'Maria Gestora', '$2a$10$TbeB6MyZk30F/E1O1EQgT.FLQU88HIwvVkO.bMjN/55B.GsHWpmMq', 'ADM', 'ROSA', '2022-01-01'),
(usuario_key_generator.NEXTVAL, 'carlos@gmail.com', 'Carlos Gestor', '$2a$10$TbeB6MyZk30F/E1O1EQgT.FLQU88HIwvVkO.bMjN/55B.GsHWpmMq', 'GESTOR', 'AZUL_PASTEL', '2022-01-01');

INSERT INTO coach (id, email, nome, senha, permissao, atuacao, data_cadastro, id_gestor) VALUES
(usuario_key_generator.NEXTVAL, 'paulo@gmail.com', 'Paulo Coach', '$2a$10$TbeB6MyZk30F/E1O1EQgT.FLQU88HIwvVkO.bMjN/55B.GsHWpmMq', 'ADM', 'BACK_END', '2022-01-01', 1),
(usuario_key_generator.NEXTVAL, 'pedro@gmail.com', 'Pedro Coach', '$2a$10$TbeB6MyZk30F/E1O1EQgT.FLQU88HIwvVkO.bMjN/55B.GsHWpmMq', 'COACH', 'BACK_END', '2022-01-01', 51),
(usuario_key_generator.NEXTVAL, 'flavia@gmail.com', 'Flavia Coach', '$2a$10$TbeB6MyZk30F/E1O1EQgT.FLQU88HIwvVkO.bMjN/55B.GsHWpmMq', 'COACH', 'BACK_END', '2022-01-01', 51);

INSERT INTO usuario_start (id, email, nome, senha, permissao, atuacao, data_cadastro, billable, id_turma, id_gestor, id_coach, id_ultima_avaliacao) VALUES
(usuario_key_generator.NEXTVAL, 'priscila@gmail.com', 'Priscila Start', '$2a$10$TbeB6MyZk30F/E1O1EQgT.FLQU88HIwvVkO.bMjN/55B.GsHWpmMq', 'START', 'BACK_END', '2022-01-01', FALSE, 2, 1, 151, NULL),
(usuario_key_generator.NEXTVAL, 'bia@gmail.com', 'Bia Start', '$2a$10$TbeB6MyZk30F/E1O1EQgT.FLQU88HIwvVkO.bMjN/55B.GsHWpmMq', 'START', 'BACK_END', '2022-01-01', FALSE, 2, 1, 151, NULL),
(usuario_key_generator.NEXTVAL, 'joao@gmail.com', 'João Start', '$2a$10$TbeB6MyZk30F/E1O1EQgT.FLQU88HIwvVkO.bMjN/55B.GsHWpmMq', 'START', 'BACK_END', '2022-01-01', FALSE, 3, 51, 201, NULL),
(usuario_key_generator.NEXTVAL, 'ana@gmail.com', 'Ana Start', '$2a$10$TbeB6MyZk30F/E1O1EQgT.FLQU88HIwvVkO.bMjN/55B.GsHWpmMq', 'START', 'BACK_END', '2022-01-01', FALSE, 2, 1, 151, NULL),
(usuario_key_generator.NEXTVAL, 'andre@gmail.com', 'André Start', '$2a$10$TbeB6MyZk30F/E1O1EQgT.FLQU88HIwvVkO.bMjN/55B.GsHWpmMq', 'START', 'BACK_END', '2022-01-01', FALSE, 2, 1, 151, NULL),
(usuario_key_generator.NEXTVAL, 'andreia@gmail.com', 'Andréia Start', '$2a$10$TbeB6MyZk30F/E1O1EQgT.FLQU88HIwvVkO.bMjN/55B.GsHWpmMq', 'START', 'BACK_END', '2022-01-01', FALSE, 2, 1, 151, NULL),
(usuario_key_generator.NEXTVAL, 'thiago@gmail.com', 'Thiago Start', '$2a$10$TbeB6MyZk30F/E1O1EQgT.FLQU88HIwvVkO.bMjN/55B.GsHWpmMq', 'START', 'BACK_END', '2022-01-01', FALSE, 2, 1, 151, NULL),
(usuario_key_generator.NEXTVAL, 'hugo@gmail.com', 'Hugo Start', '$2a$10$TbeB6MyZk30F/E1O1EQgT.FLQU88HIwvVkO.bMjN/55B.GsHWpmMq', 'START', 'BACK_END', '2022-01-01', FALSE, 2, 1, 151, NULL),
(usuario_key_generator.NEXTVAL, 'natalia@gmail.com', 'Natália Start', '$2a$10$TbeB6MyZk30F/E1O1EQgT.FLQU88HIwvVkO.bMjN/55B.GsHWpmMq', 'START', 'BACK_END', '2022-01-01', FALSE, 2, 1, 151, NULL),
(usuario_key_generator.NEXTVAL, 'augusto@gmail.com', 'Augusto Start', '$2a$10$TbeB6MyZk30F/E1O1EQgT.FLQU88HIwvVkO.bMjN/55B.GsHWpmMq', 'START', 'BACK_END', '2022-01-01', FALSE, 3, 51, 201, NULL),
(usuario_key_generator.NEXTVAL, 'jose@gmail.com', 'José Start', '$2a$10$TbeB6MyZk30F/E1O1EQgT.FLQU88HIwvVkO.bMjN/55B.GsHWpmMq', 'START', 'BACK_END', '2022-01-01', FALSE, 3, 51, 201, NULL);

--Augusto Start
INSERT INTO avaliacao (data, farol, parecer, id_start) VALUES
('2022-01-01', 'AMARELO', 'primeiro contato', 701),
('2022-01-11', 'AMARELO', 'mantém uma média nos estudos', 701),
('2022-01-21', 'VERMELHO', 'se perdeu um pouco', 701),
('2022-02-01', 'AMARELO', 'mudou a atitude', 701),
('2022-02-11', 'VERDE', 'está evoluindo bem', 701),
('2022-02-21', 'VERDE', 'evoluindo bem', 701),
('2022-03-01', 'VERDE', 'iniciou Sprin Boot', 701),
('2022-03-11', 'VERDE', 'boa evolução', 701),
('2022-03-21', 'VERDE', 'em evolução', 701),
('2022-05-30', 'AZUL', 'pronto para assumir um projeto', 701);

--Augusto Start
UPDATE usuario_start SET id_ultima_avaliacao = 10 WHERE id = 701;

--Grafico
INSERT INTO avaliacao (data, farol, parecer, id_start) VALUES
('2022-05-30', 'AMARELO', 'pronto para assumir um projeto', 451),
('2022-05-30', 'VERMELHO', 'pronto para assumir um projeto', 501),
('2022-05-30', 'VERMELHO', 'pronto para assumir um projeto', 301),
('2022-05-30', 'VERDE', 'pronto para assumir um projeto', 601),
('2022-05-30', 'VERDE', 'pronto para assumir um projeto', 651),
('2022-05-30', 'AZUL', 'pronto para assumir um projeto', 251),
('2022-05-30', 'AZUL', 'pronto para assumir um projeto', 551);

--Grafico
UPDATE usuario_start SET id_ultima_avaliacao = 11 WHERE id = 451;
UPDATE usuario_start SET id_ultima_avaliacao = 12 WHERE id = 501;
UPDATE usuario_start SET id_ultima_avaliacao = 13 WHERE id = 301;
UPDATE usuario_start SET id_ultima_avaliacao = 14 WHERE id = 601;
UPDATE usuario_start SET id_ultima_avaliacao = 15 WHERE id = 651;
UPDATE usuario_start SET id_ultima_avaliacao = 16 WHERE id = 251;
UPDATE usuario_start SET id_ultima_avaliacao = 17, billable = TRUE, data_billable = '2022-06-30' WHERE id = 551;

