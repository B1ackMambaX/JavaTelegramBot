# JavaTelegramBot

**Authors:** Артемий Чудиновских([@b1ackmambax](https://t.me/b1ackmambax)) and Перминов Никита([@begenFys](https://t.me/begenFys))

## Completed Task

1. [Task 1](https://github.com/B1ackMambaX/JavaTelegramBot/issues/2) -  Реализовать бота в Telegram, который задает тестовые вопросы по программированию, проверяет их правильность.

2. [Task 2](https://github.com/B1ackMambaX/JavaTelegramBot/issues/5) - Переработка архитектуры и подключение базы данных.

3. [Task 3](https://github.com/B1ackMambaX/JavaTelegramBot/issues/11) - Реализованы квизы по другим ЯП и подсчет статистики после квиза. 
4. [Task 4](https://github.com/B1ackMambaX/JavaTelegramBot/issues/15) - Реализованы статистика и лидерборд.  

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

### Deploy
#### Docker Compose

1. Клонировать репозиторий
2. Клонировать .env.example и заполнить его
```shell
cp .env.example .env
```
```shell
nano .env
```
3. Настройка networks и volumes
```shell
docker network create javabot_net
```
```shell
docker volume create javabot_database_data
```
4. Запуск
```shell
docker compose up -d
```

5. Иницилизация базы данных
```shell
docker compose run database psql -U $DB_USER -d $DB_NAME -f init.sql
```


