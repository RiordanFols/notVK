INSERT INTO post (id, user_id, creation_date_time, text)
VALUES (6, 1, '2021-01-25 19:10:12+03',
        'Всем привет! Буду выкладывать сюда некоторые события из своей жизни и просто фотографии'),
       (7, 1, '2021-01-26 19:12:12+03',
        'Встретил кота по имени Матроскин, но маме он не нравится'),
       (8, 1, '2021-01-27 19:14:12+03',
        'Приехали с Матроскиным в деревню, встретили пса Шарика'),
       (9, 1, '2021-01-28 19:16:12+03',
        'Завели корову, назвали Гаврюша'),
       (10, 1, '2021-01-29 19:19:12+03',
        'Приболел');
INSERT INTO post_imgs(post_id, img_filename)
VALUES (7, '51486ebe-f963-4ff4-84dd-e6765c8d7c52.Появился кот.jpg'),
       (8, 'aed7690d-9bc2-47e6-b788-65399b5e2ba8.В деревне.jpg'),
       (9, '429835a4-d7fd-414a-9fc6-7bb1583785f3.Гаврюша фото.jpg'),
       (10, 'fa6d43be-c41c-4d36-a855-021b5a51102e.заболел.png');


INSERT INTO post (id, user_id, creation_date_time, text)
VALUES (16, 3, '2021-01-25 19:10:36+03',
        'Какая-то странная птица');
INSERT INTO post_imgs(post_id, img_filename)
VALUES (16, '026f6423-3334-4bd4-a2ef-cd136860e597.Печкин и Галчонок.jpg');


INSERT INTO post (id, user_id, creation_date_time, text)
VALUES (21, 4, '2021-01-25 19:10:45+03',
        'Начал жить с дядей Федором и котом Матроскиным'),
       (22, 4, '2021-01-26 19:12:45+03',
        'Ищем клад'),
       (23, 4, '2021-01-27 19:14:45+03',
        'На фотоохоте'),
       (24, 4, '2021-01-28 19:14:45+03',
        'Познакомился с бобром в необычных обстоятельствах');
INSERT INTO post_imgs(post_id, img_filename)
VALUES (21, 'aed7690d-9bc2-47e6-b788-65399b5e2ba8.В деревне.jpg'),
       (22, '41b491f5-b8fb-4d9a-b504-c8d55a721e75.Клад.jpg'),
       (23, 'c362def8-6e70-4e6d-a269-d15e446489a5.Шарик на фотоохоте.jpg'),
       (24, 'c44314a9-f40d-43a3-aacd-b50c9e21e7a3.Шарик и бобер.png');


INSERT INTO post (id, user_id, creation_date_time, text)
VALUES (26, 5, '2021-01-25 19:10:55+03',
        'Переехали в деревню, подобрали Шарика'),
       (27, 5, '2021-01-26 19:10:55+03',
        'У меня теперь есть своя корова! Гаврюша!'),
       (28, 5, '2021-01-27 19:10:55+03',
        'А живности все больше. Знакомьтесь, галчонок.'),
       (29, 5, '2021-01-28 19:10:55+03',
        'А теперь еще и теленок!');
INSERT INTO post_imgs(post_id, img_filename)
VALUES (26, 'aed7690d-9bc2-47e6-b788-65399b5e2ba8.В деревне.jpg'),
       (27, '429835a4-d7fd-414a-9fc6-7bb1583785f3.Гаврюша фото.jpg'),
       (28, '01894c7a-eaff-4fde-b21f-5a27408848c8.Нашли галчонка.jpg'),
       (29, '4e61f8bf-bc61-4845-b8eb-f8e94a501753.ГаврюшаСТеленком.jpg');