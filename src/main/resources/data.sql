insert into roles (name) VALUES
    ('ADMIN'),
    ('USER'),
    ('PRESENTER');

insert into users (email, full_name, username, password) VALUES
    ('example@post.org',	'Peter Parker', 'spiderman', '$2a$12$yOjP4l62G7pGJnlMsHnkfet5KKD9rAkffHE.Yd3QDTrUHkgnhK4G.'),
    ('phil@post.ru',	'Phil Jones', 'defender', '$2a$12$XLG3ltF5WODhDEakXlzy8O4YdmtUQZe1uAkEZ4oTj6g.i4jhtnK8y'),
    ('frankie@chelsea.com',	'Frank Lampard', 'lamps', '$2a$12$w277ON6aNfai6eGCcOYZn.7wvTJEhOjREcPWkqkW1bZbU20BxXI0W'),
    ('stieve@apple.org',	'Stieve Jobs', 'mactop', '$2a$12$Et6sKuE9W23Z2qZcjiax4uL.nKM99L6p96y4pradSRzLL7arPlFwe'),
    ('listener@example.org', 'Listener', 'listener', '$2a$12$ALrWMUlBrS2C//qvvRZawOVnFOcJaXkgU6OC68TmNV3rYVi5Fs0Hm'),
    ('presenter@example.org', 'Presenter', 'presenter', '$2a$12$25Mi/JMf1EuNZRk4ndLRH.HP2CgqNBK7lUive5/hXC0g15V/piDcq'),
    ('administrator@example.org', 'Admin', 'admin', '$2a$12$FEyUa2tnH.sH9Z8JXj0Cw.zSjwAUegrLj8PXddSLf59/AsdLceuoW');


insert into rooms (number)
VALUES (54), (27), (228);

insert into presentations (description, room_id, subject, title, lasts, date, start_time) VALUES
    ('лекция', 1, 'math', 'заголовокMath', '04:15', '2021-06-08', '14:40'),
    ('лекция', 1, 'math2', 'тема', '02:00', '2021-07-25', '13:00'),
    ('лекция', 2, 'history', 'заголовокHistory', '01:30', '2021-07-18', '10:45'),
    ('лекция', 3, 'bio', 'заголовокBio', '01:45', '2021-05-01', '16:00');

insert into presentation_users(presentation_id, user_id) VALUES
    (1, 4), (2, 4), (3, 3), (4, 6);

insert into user_roles (user_id, role_id) VALUES (1, 1), (2, 2), (3, 3), (4, 3), (5, 2), (6, 3), (7, 1);

