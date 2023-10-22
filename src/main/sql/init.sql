CREATE TABLE IF NOT EXISTS public.proglang (
    proglang_id SERIAL PRIMARY KEY,
    proglang_name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TYPE public.answer_type AS ENUM ('text', 'code');
CREATE TABLE IF NOT EXISTS public.progquiz (
    progquiz_id SERIAL PRIMARY KEY,
    proglang_id INTEGER,
    question TEXT NOT NULL,
    answer_type public.answer_type NOT NULL,
    answer_value TEXT NULL,

    FOREIGN KEY (proglang_id) REFERENCES proglang (proglang_id) ON DELETE SET NULL
);

CREATE TYPE public.plathform AS ENUM ('TG', 'VK');
CREATE TYPE public.state AS ENUM('QUIZ', 'IDLE');
CREATE TABLE IF NOT EXISTS public.user (
    user_id SERIAL PRIMARY KEY,
    plathform public.plathform NOT NULL,
    plathform_id INTEGER NOT NULL,
    plathfrom_username VARCHAR(255) NULL,
    plathfrom_phone VARCHAR(255) NULL,
    plathfrom_email VARCHAR(255) NULL,
    "state" public.state NOT NULL,
    setting_field_1 TEXT NULL,
    setting_field_2 TEXT NULL,
    setting_field_3 TEXT NULL,
    setting_field_4 TEXT NULL,
    setting_field_5 TEXT NULL
);
