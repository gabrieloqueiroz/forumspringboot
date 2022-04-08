
INSERT INTO USUARIO(name, email, password) VALUES('Aluno', 'aluno@email.com', '$2a$10$rKY.JMZbQs9cxprwPY1O1emiHjPLfpH6VfdtHC73v.a/MioiX3Qu6');

INSERT INTO CURSO(name, category) VALUES('Spring Boot', 'Programação');
INSERT INTO CURSO(name, category) VALUES('HTML 5', 'Front-end');

INSERT INTO TOPICO(title, message, creation_date, status, author_id, course_id) VALUES('Dúvida', 'Erro ao criar projeto', '2019-05-05 18:00:00', 'NOT_ANSWERED', 1, 1);
INSERT INTO TOPICO(title, message, creation_date, status, author_id, course_id) VALUES('Dúvida 2', 'Projeto não compila', '2019-05-05 19:00:00', 'NOT_ANSWERED', 1, 1);
INSERT INTO TOPICO(title, message, creation_date, status, author_id, course_id) VALUES('Dúvida 3', 'Tag HTML', '2019-05-05 20:00:00', 'NOT_ANSWERED', 1, 2);