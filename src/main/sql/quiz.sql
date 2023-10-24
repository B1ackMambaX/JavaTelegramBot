INSERT INTO public.proglang(proglang_name)
VALUES
    ('JavaScript');

INSERT INTO public.progquiz(proglang_id, question, answer_type, answer_value)
VALUES
    (1, 'Какой метод используется для фильтрации массива?', 'TEXT', 'filter'),
    (1, 'Какое ключевое слово используется для обозначения наследования классов?', 'TEXT', 'extends'),
    (1, 'Какой метод добавляет элемент в конец массива?', 'TEXT', 'push'),
    (1, 'Какой метод удаляет последний элемент массива?', 'TEXT', 'pop'),
    (1, 'Какой метод используется для разбиения строки на массив подстрок?', 'TEXT', 'split');