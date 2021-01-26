INSERT INTO usr (id, is_active, username, password, name, surname)
VALUES (1, TRUE, 'pavel', '123', 'Павел', 'Чернов'),
       (2, TRUE, 'anton', '123', 'Антон', 'Янушевский'),
       (3, TRUE, 'anna', '123', 'Анна', 'Каренина'),
       (4, TRUE, 'vlad', '123', 'Влад', 'Вишневский'),
       (5, TRUE, 'dasha', '123', 'Дарья', 'Катюшкина');

INSERT INTO user_role(user_id, role)
VALUES (1, 'USER'),
       (2, 'USER'),
       (3, 'USER'),
       (4, 'USER'),
       (5, 'USER');