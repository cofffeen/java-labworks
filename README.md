# Лабораторные работы по Java

Репозиторий учебных проектов на Java: модель автосалона и банковская система с REST API, базой данных и отдельным сервисом валютных курсов.

## Структура

```text
root/
├── lab1/                  # Автосалон
├── lab2/                  # Банковская система
│   ├── domain/            # Доменные модели и интерфейсы репозиториев
│   ├── application/       # Бизнес-логика
│   ├── data-access/       # JPA-сущности и доступ к PostgreSQL
│   ├── presentation/      # REST API, безопасность и Swagger
│   ├── rates-service/     # Сервис валютных курсов
│   └── docker-compose.yml # PostgreSQL, Kafka и ZooKeeper
├── gradle/wrapper/
├── build.gradle.kts
└── settings.gradle.kts
```

Сборка в `root/` включает `lab1`. У `lab2` собственные настройки сборки и Gradle Wrapper.

## Лабораторная № 1 - автосалон

Модель автомобилей и комплектующих, клиентов, сотрудников и заказов. Реализованы стандартные и индивидуальные заказы, переходы между состояниями заказа, фильтрация, хранение данных в памяти и преобразование моделей в DTO.

Технологии: Java, Lombok, JUnit 5, Gradle Kotlin DSL. Тесты проверяют модели, сервисы, репозиторий, маппинг и состояния заказов. Метод `Main` пока пустой; основная функциональность представлена классами и тестами.

Из корня репозитория:

```bash
cd root
sh gradlew :lab1:build
sh gradlew :lab1:test
```

## Лабораторная № 2 - банковская система

Многомодульное приложение с пользователями и администраторами, банковскими счетами, пополнением, снятием, переводами и историей операций. Отдельный сервис генерирует учебные валютные курсы и передаёт их через Kafka, банковское приложение использует курсы для представления операций в другой валюте.

Технологии: Java 21, Spring Boot, Spring Web, Spring Security, Spring Data JPA, PostgreSQL, Apache Kafka, Springdoc OpenAPI, Lombok и Gradle Kotlin DSL

### Сборка и тесты

Нужны JDK 21 и доступ к сети для первой загрузки Gradle и зависимостей. Для инфраструктуры нужны Docker и Docker Compose.

Из корня репозитория:

```bash
cd root/lab2
sh gradlew build
sh gradlew test
```

### Локальный запуск

В каталоге `root/lab2` запустите инфраструктуру:

```bash
docker compose up -d
```

После готовности PostgreSQL и Kafka запустите сервис курсов:

```bash
sh gradlew :rates-service:bootRun
```

В другом терминале, также из `root/lab2`, запустите банковское приложение:

```bash
SPRING_DATASOURCE_PASSWORD=password sh gradlew :presentation:bootRun
```

Адреса при успешном запуске:

- Банковское API: `http://localhost:8080`.
- Swagger UI: `http://localhost:8080/swagger-ui.html`.
- OpenAPI: `http://localhost:8080/api-docs`.
- Сервис курсов: `http://localhost:8081`.

Остановка инфраструктуры:

```bash
docker compose down
```