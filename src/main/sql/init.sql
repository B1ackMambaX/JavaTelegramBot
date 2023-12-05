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