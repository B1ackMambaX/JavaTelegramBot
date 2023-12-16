CREATE TABLE IF NOT EXISTS public.proglang (
   id SERIAL PRIMARY KEY,
   proglangName VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS public.progquiz (
   id SERIAL PRIMARY KEY,
   proglangId INTEGER,
   question TEXT NOT NULL,
   answerType VARCHAR(255) NOT NULL,
   answerValue TEXT NULL,

   FOREIGN KEY (proglangId) REFERENCES proglang (id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS public.user (
   id SERIAL PRIMARY KEY,
   plathform VARCHAR(255) NOT NULL,
   plathformId INTEGER NOT NULL,
   plathformUsername VARCHAR(255) NULL,
   plathformPhone VARCHAR(255) NULL,
   plathformEmail VARCHAR(255) NULL,
   "state" VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.quizstate(
    id SERIAL PRIMARY KEY,
    userId INTEGER,
    currentProglangId INTEGER,
    currentQuestionIndex INTEGER,
    currentQuizStats INTEGER,

    FOREIGN KEY (userId) REFERENCES public.user (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.statistics(
    id SERIAL PRIMARY KEY,
    userId INTEGER,
    proglangId INTEGER,
    score INTEGER,

    FOREIGN KEY (userId) REFERENCES public.user (id) ON DELETE CASCADE,
    FOREIGN KEY (proglangId) REFERENCES public.proglang (id) ON DELETE CASCADE
);

INSERT INTO public.proglang(proglangName)
VALUES
    ('JavaScript'),
    ('Python');

INSERT INTO public.progquiz(proglangId, question, answerType, answerValue)
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