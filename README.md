# job4j_todo

В проекте "job4j_todo" разрабатывается сайт со списком текущих задач.

Функционал проекта позволяет:
* добавлять, выполнять, редактировать или удалять задачи;
* просматривать все задачи, только выполненные или только новые.

### Стек технологий :technologist:.
Основные :man_technologist:: 
- Java 17
- Spring Boot 2.7.6
- Hibernate 5.5
- Thymeleaf
- Bootstrap CSS 4.1
- Liquibase 4.15.0
- PostgreSQL 15.1 (драйвер JDBC 42.5.1)
- checkstyle 10.0.

Тестирование :mechanic::
- H2database 2.1.214
- Jacoco 0.8.8
- Spring boot starter test (JUnit 5 + AssertJ, Mockito).

### Требования к окружению :black_circle:.
- Java 17
- Maven 3.8
- PostgreSQL 15.

### Запуск проекта :running:.
```Скачать проект job4j_todo в IntelliJ Idea```

```Создать БД "todo" (с помощью pgAdmin4)```

```Cоздайте таблицу БД  "tasks". Откройте закладку Maven -> plugins -> liquibase. Найдите задачу liquibase:update и выполните ее.```

```Запустите приложение в классе Main (ru/job4j/todo/Main.java)```

```Откройте страницу http://localhost:8080/ в браузере```

### Screenshots работы с приложением Кинотеатр :cinema:.

- [x] Главная страница

![](https://raw.githubusercontent.com/PavelValger/job4j_todo/e0d3478dc097e91aa67d63b0845a4f0c745068e6/img/1.jpg)

- [x] Страница со списком всех заданий

![](https://raw.githubusercontent.com/PavelValger/job4j_todo/e0d3478dc097e91aa67d63b0845a4f0c745068e6/img/2.jpg)

- [x] Страница со списком только выполненных заданий

![](https://raw.githubusercontent.com/PavelValger/job4j_todo/e0d3478dc097e91aa67d63b0845a4f0c745068e6/img/3.jpg)

- [x] Страница со списком новых заданий

![](https://raw.githubusercontent.com/PavelValger/job4j_todo/e0d3478dc097e91aa67d63b0845a4f0c745068e6/img/4.jpg)

- [x] Страница со списком создания задания

![](https://github.com/PavelValger/job4j_todo/blob/master/img/5.jpg?raw=true)

- [x] Страница с подробным описанием нового задания

![](https://raw.githubusercontent.com/PavelValger/job4j_todo/e0d3478dc097e91aa67d63b0845a4f0c745068e6/img/6.jpg)

- [x] Страница с подробным описанием выполненного задания

![](https://raw.githubusercontent.com/PavelValger/job4j_todo/e0d3478dc097e91aa67d63b0845a4f0c745068e6/img/7.jpg)

- [x] Страница редактирования задания

![](https://github.com/PavelValger/job4j_todo/blob/master/img/8.jpg?raw=true)

- [x] Страница ошибки 404

![](https://raw.githubusercontent.com/PavelValger/job4j_todo/e0d3478dc097e91aa67d63b0845a4f0c745068e6/img/9.jpg)

- [x] Страница регистрации пользователя

![](https://raw.githubusercontent.com/PavelValger/job4j_todo/1ebefe2053ab1e37c16364c667919f2ade488ade/img/страница%20регистрации.jpg)

- [x] Страница входа на сайт

![](https://raw.githubusercontent.com/PavelValger/job4j_todo/1ebefe2053ab1e37c16364c667919f2ade488ade/img/страница%20входа.jpg)

- [x] Вход выполнен

![](https://raw.githubusercontent.com/PavelValger/job4j_todo/1ebefe2053ab1e37c16364c667919f2ade488ade/img/вход%20выполнен.jpg)

- [x] Ошибка регистрации

![](https://raw.githubusercontent.com/PavelValger/job4j_todo/2644be77e2d133d806990cac0adcff7cfbb8b62b/img/ошибка%20регистрации.jpg)

- [x] Ошибка логина

![](https://raw.githubusercontent.com/PavelValger/job4j_todo/1ebefe2053ab1e37c16364c667919f2ade488ade/img/ошибка%20логина.jpg)


#### Контакты для связи :iphone::
* Вальгер Павел Иванович;
* +79920045094 telegram, whatsapp;
* pavelwalker@mail.ru.