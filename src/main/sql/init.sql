CREATE TABLE IF NOT EXISTS public.proglang (
    id SERIAL PRIMARY KEY,
    proglang_name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TYPE public.answer_type AS ENUM ('text', 'code');
CREATE TABLE IF NOT EXISTS public.progquiz (
    id SERIAL PRIMARY KEY,
    proglang_id INTEGER,
    question TEXT NOT NULL,
    answer_type public.answer_type NOT NULL,
    answer_value TEXT NULL,

    FOREIGN KEY (proglang_id) REFERENCES proglang (id) ON DELETE SET NULL
);

CREATE TYPE public.plathform AS ENUM ('TG', 'VK');
CREATE TYPE public.state AS ENUM('QUIZ', 'IDLE');
CREATE TABLE IF NOT EXISTS public.user (
    id SERIAL PRIMARY KEY,
    plathform public.plathform NOT NULL,
    plathform_id INTEGER NOT NULL,
    plathform_username VARCHAR(255) NULL,
    plathform_phone VARCHAR(255) NULL,
    plathform_email VARCHAR(255) NULL,
    "state" public.state NOT NULL
);

CREATE TABLE IF NOT EXISTS public.quizstate(
    id SERIAL PRIMARY KEY,
    user_id INTEGER,
    current_proglang_id INTEGER,
    current_question_index INTEGER,
    current_quiz_stats INTEGER,


    FOREIGN KEY (user_id) REFERENCES public.user (id) ON DELETE CASCADE
)
