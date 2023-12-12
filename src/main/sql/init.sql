CREATE TABLE IF NOT EXISTS public.proglang (
   id SERIAL PRIMARY KEY,
   proglang_name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS public.progquiz (
   id SERIAL PRIMARY KEY,
   proglang_id INTEGER,
   question TEXT NOT NULL,
   answer_type VARCHAR(255) NOT NULL,
   answer_value TEXT NULL,

   FOREIGN KEY (proglang_id) REFERENCES proglang (id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS public.user (
   id SERIAL PRIMARY KEY,
   plathform VARCHAR(255) NOT NULL,
   plathform_id INTEGER NOT NULL,
   plathform_username VARCHAR(255) NULL,
   plathform_phone VARCHAR(255) NULL,
   plathform_email VARCHAR(255) NULL,
   "state" VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.quizstate(
    id SERIAL PRIMARY KEY,
    user_id INTEGER,
    current_proglang_id INTEGER,
    current_question_index INTEGER,
    current_quiz_stats INTEGER,

    FOREIGN KEY (user_id) REFERENCES public.user (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.statistics(
    id SERIAL PRIMARY KEY,
    user_id INTEGER,
    proglang_id INTEGER,
    score INTEGER,

    FOREIGN KEY (user_id) REFERENCES public.user (id) ON DELETE CASCADE,
    FOREIGN KEY (proglang_id) REFERENCES public.proglang (id) ON DELETE CASCADE
);

INSERT INTO public.proglang(proglang_name)
VALUES
    ('JavaScript'),
    ('Python');

INSERT INTO public.progquiz(proglang_id, question, answer_type, answer_value)
VALUES
    (1, 'Какой метод используется для фильтрации массива?', 'TEXT', 'filter'),
    (1, 'Какое ключевое слово используется для обозначения наследования классов?', 'TEXT', 'extends'),
    (1, 'Какой метод добавляет элемент в конец массива?', 'TEXT', 'push'),
    (1, 'Какой метод удаляет последний элемент массива?', 'TEXT', 'pop'),
    (1, 'Какой метод используется для разбиения строки на массив подстрок?', 'TEXT', 'split'),
    (2, 'Какое ключевое слово используется для объявления функции в Python?', 'TEXT', 'def'),
    (2, 'Какой тип данных используется для хранения неизменяемых последовательностей в Python?', 'TEXT', 'tuple'),
    (2, 'Как называется оператор, используемый для объединения двух или более строк в Python?', 'TEXT', '+'),
    (2, 'Какой метод используется для добавления элемента в конец списка в Python?', 'TEXT', 'append'),
    (2, 'Как называется стандартная библиотека для работы с регулярными выражениями в Python?', 'TEXT', 're');