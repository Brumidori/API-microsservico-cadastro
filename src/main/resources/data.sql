INSERT INTO turma (nome) VALUES
('junho'),
('julho'),
('agosto');

INSERT INTO gestor (id, email, nome, senha, permissao, cor, data_cadastro) VALUES
(usuario_key_generator.NEXTVAL, 'maria@gmail.com', 'Maria Gestora', '$2a$10$TbeB6MyZk30F/E1O1EQgT.FLQU88HIwvVkO.bMjN/55B.GsHWpmMq', 'ADM', 'ROSA', '2022-01-01'),
(usuario_key_generator.NEXTVAL, 'carlos@gmail.com', 'Carlos Gestor', '$2a$10$TbeB6MyZk30F/E1O1EQgT.FLQU88HIwvVkO.bMjN/55B.GsHWpmMq', 'GESTOR', 'AZUL_PASTEL', '2022-01-01');

INSERT INTO coach (id, email, nome, senha, permissao, atuacao, data_cadastro, id_gestor) VALUES
(usuario_key_generator.NEXTVAL, 'paulo@gmail.com', 'Paulo Coach', '$2a$10$TbeB6MyZk30F/E1O1EQgT.FLQU88HIwvVkO.bMjN/55B.GsHWpmMq', 'ADM', 'BACK_END', '2022-01-01', 1),
(usuario_key_generator.NEXTVAL, 'pedro@gmail.com', 'Pedro Coach', '$2a$10$TbeB6MyZk30F/E1O1EQgT.FLQU88HIwvVkO.bMjN/55B.GsHWpmMq', 'COACH', 'BACK_END', '2022-01-01', 51),
(usuario_key_generator.NEXTVAL, 'flavia@gmail.com', 'Flavia Coach', '$2a$10$TbeB6MyZk30F/E1O1EQgT.FLQU88HIwvVkO.bMjN/55B.GsHWpmMq', 'COACH', 'BACK_END', '2022-01-01', 51);

INSERT INTO usuario_start (id, email, nome, senha, permissao, atuacao, data_cadastro, billable, id_turma, id_gestor, id_coach, id_ultima_avaliacao) VALUES
(usuario_key_generator.NEXTVAL, 'prisciala@gmail.com', 'Priscila', '$2a$10$TbeB6MyZk30F/E1O1EQgT.FLQU88HIwvVkO.bMjN/55B.GsHWpmMq', 'START', 'BACK_END', '2022-01-01', FALSE, 2, 1, 151, NULL),
(usuario_key_generator.NEXTVAL, 'bia@gmail.com', 'Bia', '$2a$10$TbeB6MyZk30F/E1O1EQgT.FLQU88HIwvVkO.bMjN/55B.GsHWpmMq', 'START', 'BACK_END', '2022-01-01', FALSE, 2, 1, 151, NULL),
(usuario_key_generator.NEXTVAL, 'joao@gmail.com', 'João', '$2a$10$TbeB6MyZk30F/E1O1EQgT.FLQU88HIwvVkO.bMjN/55B.GsHWpmMq', 'START', 'BACK_END', '2022-01-01', FALSE, 3, 51, 201, NULL);

INSERT INTO avaliacao (data, farol, parecer, id_start) VALUES
('2022-01-01', 'AMARELO', 'primeiro contato', 251),
('2022-01-11', 'AMARELO', 'mantém uma média nos estudos', 251),
('2022-01-21', 'VERMELHO', 'se perdeu um pouco', 251),
('2022-02-01', 'AMARELO', 'mudou a atitude', 251),
('2022-02-11', 'VERDE', 'está evoluindo bem', 251),
('2022-02-21', 'VERDE', 'evoluindo bem', 251),
('2022-03-01', 'VERDE', 'iniciou Sprin Boot', 251),
('2022-03-11', 'VERDE', 'boa evolução', 251),
('2022-03-21', 'VERDE', 'em evolução', 251),
('2022-05-30', 'AZUL', 'pronto para assumir um projeto', 251);

UPDATE usuario_start SET id_ultima_avaliacao = 10 WHERE id = 251;

