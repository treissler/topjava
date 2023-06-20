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

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2023-06-01 07:01', 'User breakfast edge', 500),
       (100000, '2023-06-01 12:09', 'User lunch edge', 500),
       (100000, '2023-06-01 18:09', 'User dinner edge', 700),
       (100000, '2023-06-01 22:09', 'User second dinner edge', 300),
       (100000, '2023-06-02 00:00', 'User breakfast midnight', 550),
       (100000, '2023-06-02 07:09', 'User breakfast exceeded', 500),
       (100000, '2023-06-02 11:09', 'User workstart latte exceeded', 200),
       (100000, '2023-06-02 12:09', 'User pizza with colleague exceeded', 1000),
       (100000, '2023-06-02 10:09', 'User antistress sugar coffee exceeded', 200),
       (100000, '2023-06-02 15:09', 'User antistress two full choco bars exceeded', 700),
       (100000, '2023-06-02 16:09', 'User antistress croissant exceeded', 200),
       (100000, '2023-06-02 17:09', 'User colleague''s birthday sweets exceeded', 100),
       (100000, '2023-06-02 19:09', 'User subway cappuccino exceeded', 100),
       (100000, '2023-06-02 23:09', 'User dinner exceeded', 200),
       (100000, '2023-06-03 12:09', 'User breakfast', 300),
       (100000, '2023-06-03 18:09', 'User lunch', 1000),
       (100001, '2023-06-01 07:01', 'Admin breakfast', 100),
       (100001, '2023-06-01 12:09', 'Admin lunch', 200),
       (100001, '2023-06-01 18:09', 'Admin dinner', 300),
       (100001, '2023-06-02 10:09', 'Admin breakfast  edge', 500),
       (100001, '2023-06-02 15:09', 'Admin lunch  edge', 1300),
       (100001, '2023-06-02 22:09', 'Admin dinner  edge', 200),
       (100001, '2023-06-03 12:09', 'Admin breakfast exceeded', 300),
       (100001, '2023-06-03 18:09', 'Admin lunch exceeded', 1700),
       (100001, '2023-06-03 20:09', 'Admin second lunch exceeded', 1000);