INSERT INTO message (id, user_id, target_id, creation_date_time, text)
VALUES (32, 1, 3, '2021-01-25 23:14:01+03', 'Здравствуйте, почтальон Печкин, нам должна была посылка придти'),
       (33, 3, 1, '2021-01-25 23:14:09+03', 'Да, пришла, но вам я ее не отдам'),
       (34, 1, 3, '2021-01-25 23:14:17+03', 'Почему?'),
       (35, 3, 1, '2021-01-25 23:15:17+03', 'Потому что у вас документов нет!');

INSERT INTO message (id, user_id, target_id, creation_date_time, text)
VALUES (36, 1, 4, '2021-01-26 11:56:02+03', 'Привет, Шарик. Печкин не хочет отдавать нашу посылку с фоторужьем, потому что у нас документов нет'),
       (37, 4, 1, '2021-01-26 11:56:56+03', 'Как говорит Матроскин:'),
       (38, 1, 4, '2021-01-26 11:57:12+03', 'Очень остроумно)');

INSERT INTO message_imgs(message_id, img_filename)
VALUES (37, '5de5cd14-32eb-4527-8db5-bb82676d493d.УшиЛапыХвост.jpg');

INSERT INTO message (id, user_id, target_id, creation_date_time, text)
VALUES (39, 1, 5, '2021-01-26 08:42:11+03', 'Привет, Матроскин'),
       (40, 5, 1, '2021-01-26 08:52:43+03', 'Привет'),
       (41, 1, 5, '2021-01-26 08:57:10+03', 'Как дела?'),
       (42, 5, 1, '2021-01-26 09:01:09+03', 'Как у любого порядочного кота: все замечательно'),
       (43, 5, 1, '2021-01-26 09:01:16+03', 'А у тебя как?'),
       (44, 1, 5, '2021-01-26 09:02:00+03', 'Как у любого порядочного кота)'),
       (45, 5, 1, '2021-01-26 09:02:17+03', 'Вот и отлично)');