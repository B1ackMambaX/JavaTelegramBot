CREATE TABLE IF NOT EXISTS public.proglang (
    proglang_id SERIAL PRIMARY KEY,
    proglang_name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS public.progquiz (
    progquiz_id SERIAL PRIMARY KEY,
    proglang_id INTEGER,
    question TEXT NOT NULL,
    answer_type VARCHAR(255) NOT NULL,
    answer_value TEXT NULL,

    FOREIGN KEY (proglang_id) REFERENCES proglang (proglang_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS public.user (
    user_id SERIAL PRIMARY KEY,
    plathform VARCHAR(255) NOT NULL,
    plathform_id INTEGER NOT NULL,
    plathform_username VARCHAR(255) NULL,
    plathform_phone VARCHAR(255) NULL,
    plathform_email VARCHAR(255) NULL,
    "state" VARCHAR(255) NOT NULL,
    setting_field_1 TEXT NULL,
    setting_field_2 TEXT NULL,
    setting_field_3 TEXT NULL,
    setting_field_4 TEXT NULL,
    setting_field_5 TEXT NULL
);
