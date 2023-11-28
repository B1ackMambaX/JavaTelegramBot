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