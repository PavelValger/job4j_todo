CREATE TABLE todo_users
(
    id       SERIAL PRIMARY KEY,
    name     TEXT        not null,
    login    TEXT unique not null,
    password TEXT        not null
);