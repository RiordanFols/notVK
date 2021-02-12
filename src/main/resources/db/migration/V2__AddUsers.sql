INSERT INTO usr (id, is_active, username, gender, avatar_filename, email, password, name, surname, status, birthday)
VALUES (1, TRUE, 'pavel', 'MALE', 'stock_m.png', 'pavel@gmail.com' , 123, 'Павел', 'Чернов', 'Скажи статусу нет', '1999-09-24'),
       (2, TRUE, 'anton', 'MALE','stock_m.png', 'anton@gmail.com' , '123', 'Антон', 'Янушевский', 'Alcohol is my love', '2001-10-08'),
       (3, TRUE, 'anna', 'FEMALE','stock_f.png', 'anna@gmail.com', '123', 'Анна', 'Каренина', 'Люблю свою кошку', '1993-01-23'),
       (4, TRUE, 'vlad', 'MALE','stock_m.png', 'vlad@gmail.com', '123', 'Влад', 'Вишневский', 'На спорте', '2000-04-11'),
       (5, TRUE, 'noname', 'UNDEFINED','stock_undefined.png', 'noname@gmail.com', '123', 'Нечто', 'Нечтовично', 'Девочки, записываемся на ноготочки', '1991-09-12');

INSERT INTO user_role(user_id, role)
VALUES (1, 'USER'),
       (2, 'USER'),
       (3, 'USER'),
       (4, 'USER'),
       (5, 'USER');