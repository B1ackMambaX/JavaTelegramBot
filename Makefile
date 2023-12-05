copy_env:
	cp .env.example .env

open_env:
	nano .env

init_docker:
	docker network create javabot_net
	docker volume create javabot_database_data

up:
	docker compose up -d

down:
	docker compose down

init_db:
	docker compose exec -it database psql -U $DB_USER -d $DB_NAME -f /docker-entrypoint-initdb.d/init.sql
