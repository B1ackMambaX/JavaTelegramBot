# JavaTelegramBot

**Authors:** Артемий Чудиновских([@b1ackmambax](https://t.me/b1ackmambax)) and Перминов Никита([@begenFys](https://t.me/begenFys))

## Completed Task

1. [Task 1](https://github.com/B1ackMambaX/JavaTelegramBot/issues/2) -  Реализовать бота в Telegram, который задает тестовые вопросы по программированию, проверяет их правильность.

2. [Task 2](https://github.com/B1ackMambaX/JavaTelegramBot/issues/5) - Переработка архитектуры и подключение базы данных.

3. [Task 3](https://github.com/B1ackMambaX/JavaTelegramBot/issues/11) - Реализованы квизы по другим ЯП и подсчет статистики после квиза.

## How to run bot

### Local
#### Настройка окружения
- Создать файл `.env` в корне проекта. 
- Заполнить по образцу `.env-example`
- Запустить `install` в жизненном цикле Maven.

#### Настройка базы данных
- Создать базу данных PostgreSQL и необходимые таблицы(файлы `sql/init.sql` `sql/quiz.sql`)

#### Запуск
Сделать билд, запустить проект и радоваться!

### Docker
#### Docker Compose
1. Настройка networks и volums
```shell
docker network create javabot_net
```
```shell
docker volume create javabot_database_data
```
2. Запуск
```shell
docker compose up -d
```
#### Docker, каждый контейнер по отдельности