INSERT INTO user (name, surname, birthday, email)
values ('Petya', 'Petrov', '1996-08-29', 'petya96@mail.ru'),
       ('Aleksandr', 'Ivanov', '1998-07-18', 'aleksandr.ivanov@mail.ru'),
       ('Philip', 'Ivanov', '1967-02-26', 'philip.ivanov@gmail.com'),
       ('Ivan', 'Sidorov', '2001-06-19', 'sidorov@gmail.com'),
       ('Vadim', 'Rybalko', '2000-07-14', 'vadimrybalko@gmail.com');

GO

INSERT INTO wall (user_id)
values (1),
       (2),
       (3),
       (4);

GO

INSERT INTO role (name)
values ('ADMIN'),
       ('USER'),
       ('MODERATOR');

GO

INSERT INTO user_has_role
values (1, 1),
       (2, 2),
       (3, 3),
       (3, 2),
       (4, 2),
       (5, 2),
       (5, 3);

GO

INSERT INTO community (name, topic, moderator_id)
values ('Sport', 'Volleyball', 3),
       ('IT', 'Java', 5),
       ('Books', 'Science', 3);

GO

INSERT INTO community_has_user (community_id, user_id)
values (1, 3),
       (2, 5),
       (3, 3);

GO

INSERT INTO community_message (text, date, user_id, community_id)
values ('hi', '2018-05-23 22:06:18', 2, 2),
       ('nice post', '2019-06-21 11:08:26', 4, 3),
       ('I want to see more posts', '2020-09-01', 2, 1);

GO

INSERT INTO post (header, text, wall_id, date, user_id)
values ('Spring', 'Spring is the most popular application development framework for enterprise Java',
        2, '2018-05-23', 5),
       ( 'Physics'
       , 'Physics, science that deals with the structure of matter and the interactions between the fundamental constituents of the observable universe.'
       , 3, '2019-06-21', 2),
       ('Valleyball', 'Volleyball is a team sport in which two teams of six players are separated by a net.',
        1, '2020-09-01', 3);

GO

INSERT INTO message (date, text, sender_id, receiver_id)
values ('2016-08-14 22:12:46', 'hi', 2, 3),
       ('2016-08-14 23:00:09', 'hi', 3, 2),
       ('2016-08-15 09:12:48', 'nice to meet you', 3, 2),
       ('2018-09-28 11:44:59', 'what is up,dude', 4, 5),
       ('2018-09-30 22:18:36', 'nice', 5, 4);

GO

INSERT INTO credentials (login, password, user_id)
VALUES ('petya_admin', '$2a$10$qJCaz4mz.tzyH3dnmj07LOv4a9TSkxX1cEEYfL2JC.4wyqSFb6EU6', 1),
       ('Alex Ivanov', '$2y$12$hu8FhA5cVA/Ns5TXuE5Ipe/aUkEy2GAxJ33PWaJLmgYT8nRexmaO.', 2),
       ('Phil67', '$2y$12$odPMXUyhZcg.eU4j3KLB7.Iywuk0FpHR4yGxQlBT0CSxieVzt50Ay', 3),
       ('Vanya Sidorov', '$2y$12$SrkLjR.EcUsz151y.39jzeUXYBXjYLnkIRfK8fIQoRGjaPY.7S8/C', 4),
       ('Vadim Rybalko', '$2y$12$s2pIqCdCrmu1f.OIeEdSdeXpPlfAEbzv7KNAAZwztHOtnv35s79uS', 5);

GO

INSERT INTO user_has_friends (user_id, friend_id, status, date)
VALUES (2, 3, 'ADDED', '2015-09-21'),
       (3, 2, 'ADDED', '2015-09-21'),
       (4, 5, 'ADDED', '2017-06-07'),
       (5, 4, 'ADDED', '2017-06-07'),
       (3, 4, 'REQUEST', '2020-06-19'),
       (4, 3, 'REQUEST', '2020-06-19');


