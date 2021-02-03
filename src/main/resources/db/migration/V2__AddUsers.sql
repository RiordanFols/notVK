INSERT INTO usr (id, is_active, username, password, name, surname, status, birthday)
VALUES (1, TRUE, 'pavel', '123', 'Павел', 'Чернов', 'Скажи статусу нет', '1999-09-24'),
       (2, TRUE, 'anton', '123', 'Антон', 'Янушевский', 'Alcohol is my love', '2001-10-08'),
       (3, TRUE, 'anna', '123', 'Анна', 'Каренина', 'Люблю свою кошку', '1993-01-23'),
       (4, TRUE, 'vlad', '123', 'Влад', 'Вишневский', 'На спорте', '2000-04-11'),
       (5, TRUE, 'dasha', '123', 'Дарья', 'Катюшкина', 'Девочки, записываемся на ноготочки', '1991-09-12');

INSERT INTO user_role(user_id, role)
VALUES (1, 'USER'),
       (2, 'USER'),
       (3, 'USER'),
       (4, 'USER'),
       (5, 'USER');