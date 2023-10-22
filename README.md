# Java Telegram Bot

**Authors:** Артемий Чудиновских([@b1ackmambax](https://t.me/b1ackmambax)) and Перминов Никита([@begenFys](https://t.me/begenFys))

## Completed Task

1. [Task 1](https://github.com/B1ackMambaX/JavaTelegramBot/issues/2) -  Реализовать бота в Telegram, который задает тестовые вопросы по программированию, проверяет их правильность.

## How to run bot

### Local
1. Склонировать репозиторий.
2. Создать файл `.env` в корне проекта.
3. Заполнить по образцу `.env-example`
4. Запустить `install` в жизненном цикле Maven.
5. Создать базу данных PostgreSQL и необходимые таблицы(файл `sql/init.sql`)
6. Перейти в файл `App.java` и запустить бота.

### Docker