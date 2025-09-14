# Modern API Testing Framework

Современный фреймворк для автоматизации тестирования REST API, переписанный с нуля с улучшенной архитектурой и использованием современных практик.

## 🎯 Цель проекта

Демонстрация современных подходов к автоматизации тестирования API с использованием:
- Чистой архитектуры и разделения ответственности
- Современных библиотек и инструментов
- Лучших практик написания тестов
- Подробной отчетности

## 🛠 Технологический стек

- **Java 17+** - основной язык программирования
- **JUnit 5** - фреймворк для тестирования
- **REST-assured** - библиотека для тестирования REST API
- **Gradle** - система сборки
- **Lombok** - уменьшение boilerplate кода
- **AssertJ** - fluent assertions
- **Jackson** - сериализация/десериализация JSON
- **Allure** - генерация отчетов

## 📁 Структура проекта

```
src/test/java/com/api/testing/
├── core/
│   └── BaseTest.java              # Базовый класс для всех тестов
├── models/
│   ├── User.java                  # Модель пользователя
│   └── Post.java                  # Модель поста
├── tests/
│   ├── UserManagementTests.java   # Тесты управления пользователями
│   └── PostManagementTests.java   # Тесты управления постами
└── utils/
    └── ApiClient.java             # Утилита для HTTP запросов
```

## 🧪 Тестовое покрытие

### User Management Tests (3 теста)
1. **Получение существующего пользователя по ID** - проверка корректности получения данных пользователя
2. **Получение различных пользователей по ID** - параметризованный тест для проверки разных пользователей
3. **Получение несуществующего пользователя** - проверка обработки ошибки 404

### Post Management Tests (2 теста)
1. **Получение всех постов** - проверка получения списка постов
2. **Получение поста по ID** - проверка получения конкретного поста

## 🚀 Запуск тестов

### Запуск всех тестов
```bash
./gradlew test
```

### Запуск конкретного теста
```bash
./gradlew test --tests "UserManagementTests.shouldGetExistingUserById"
```

### Запуск тестов с генерацией Allure отчета
```bash
./gradlew test allureReport
```

## 📊 Генерация отчетов

### Allure отчет
```bash
./gradlew allureReport
allure serve build/allure-results
```

## 🌐 Тестируемое API

Проект использует **JSONPlaceholder** - бесплатный fake API для тестирования:
- **Base URL**: `https://jsonplaceholder.typicode.com`
- **Endpoints**: `/users`, `/posts`
- **Особенности**: Не требует аутентификации, стабильное API

## 🏗 Архитектурные решения

### 1. Разделение ответственности
- **BaseTest** - общая конфигурация и спецификации
- **ApiClient** - абстракция для HTTP запросов
- **Models** - POJO для сериализации/десериализации
- **Tests** - бизнес-логика тестов

### 2. Использование спецификаций
```java
// Спецификация для запросов
RequestSpecification requestSpec = new RequestSpecBuilder()
    .addFilter(new AllureRestAssured())
    .setContentType(ContentType.JSON)
    .log(ALL)
    .build();

// Спецификация для успешных ответов
ResponseSpecification successResponseSpec = new ResponseSpecBuilder()
    .expectStatusCode(200)
    .log(ALL)
    .build();
```

### 3. Allure интеграция
- Структурированные отчеты с Features, Stories, Severities
- Подробные шаги тестов
- Автоматические скриншоты запросов/ответов

### 4. Современные практики
- **AAA Pattern** (Arrange, Act, Assert)
- **Fluent Assertions** с AssertJ
- **Parameterized Tests** для множественных проверок
- **Builder Pattern** для создания объектов

## 📈 Преимущества нового фреймворка

1. **Простота** - только 5 ключевых тестов, покрывающих основные сценарии
2. **Надежность** - использование стабильного JSONPlaceholder API
3. **Читаемость** - четкая структура и именование
4. **Расширяемость** - легко добавлять новые тесты и модели
5. **Отчетность** - подробные Allure отчеты

## 🔧 Настройка окружения

### Требования
- Java 17+
- Gradle 8.4+

### Установка зависимостей
```bash
./gradlew build
```

## 📝 Примеры использования

### Создание теста
```java
@Test
@DisplayName("Получение пользователя по ID")
void shouldGetUserById() {
    // Given
    int userId = 1;
    
    // When
    User user = apiClient.get("/users/" + userId)
            .then()
            .spec(successResponseSpec)
            .extract().as(User.class);
    
    // Then
    assertThat(user.getId()).isEqualTo(userId);
    assertThat(user.getEmail()).isNotEmpty();
}
```

### Использование Allure шагов
```java
User user = step("Получаем пользователя по ID " + userId, () ->
    apiClient.get("/users/" + userId)
            .then()
            .spec(successResponseSpec)
            .extract().as(User.class));
```

## 🎉 Результат

Получился современный, чистый и эффективный фреймворк для тестирования API, который:
- Легко понимать и поддерживать
- Быстро выполняется (5 тестов за ~5 секунд)
- Генерирует красивые отчеты
- Следует лучшим практикам разработки

---

*Проект создан как демонстрация современных подходов к автоматизации тестирования API*