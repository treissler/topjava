DELETE FROM user_role;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, dateTime, description, calories)
VALUES (100000, DATE_TRUNC('hour', TIMESTAMP '2022-03-17 02:09:30'), 'User breakfast', 1000),
       (100000, '2022-03-17 02:09:30', 'User lunch', 3000),
       (100001, '2021-03-17 02:09:30', 'Admin breakfast', 1500),
       (100001, now(), 'Admin lunch', 2000);