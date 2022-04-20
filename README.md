# User service

## Инструкция по установке
1. Скопируйте в папку файл docker-compose.yaml
2. В консоли запустите команду docker compose up

## Инсрукция пользования

Одновременно страртуют 3 сервиса
 - Postgres;
 - UserService на порту 3000;
 - SecondService на порту 3001.

 Для налача работы необходимо перейти по адресу http://localhost:3000/ и произвести вход по одним из предопределенных пользователей
 - mira_soft_1@mail.ru пароль: tVA$5upypU2Y Пользователь имеет роль "ADMIN";
 - mira_soft_2@mail.ru пароль: RUiU%Rat4ue9 Пользователь имеет роль "USER".

По пользователем с ролью "ADMIN" доступны CRUD операции над пользователями и ролями пользователей.
Также, на странице сервиса представлены запросы к API UserService и SecondService