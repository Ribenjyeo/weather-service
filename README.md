# Проект Weather Service

Проект `weather-service` предоставляет функциональность по получению текущей погоды для указанных координат с использованием внешнего сервиса погоды.

## Зависимости

- Java 17
- Spring Boot 3.3.0
- Lombok
- Spring WebFlux

## Установка и запуск

1. Клонируйте репозиторий:
   ```bash
   git clone https://github.com/ribenjyeo/weather-service.git
   cd weather-service

2. Соберите проект с помощью Gradle:
    ```bash
    ./gradlew build

3. Запустите приложение:
    ```bash
    ./gradlew bootRun

Сервис погоды будет доступен по адресу `http://localhost:8081`.

## Эндпоинты API

### Получение текущей погоды

- **URL:** `weather/current`
- **Метод:** GET
- **Параметры запроса:**
- `city` (обязательный, по умолчанию Москва): Название города, для которого запрашивается информация о погоде.
- `source` (обязательный, по умолчанию берется из конфигурации приложения): Имя вызываемого ресурса (yandex, weatherapi, all). Если указано значение all, то вызываются все возможные сервисы прогноза погоды.

#### Примеры запроса

```json
[
    {
        "source": "weatherapi",
        "city": "Москва",
        "weatherInfo": [
            {
            "date": "2024-06-17",
            "temperature": 18,
            "windSpeed": 16.2,
            "weatherCondition": "Patchy rain nearby"
            }
        ]
    },
    {
        "source": "yandex",
        "city": "Москва",
        "weatherInfo": [
            {
                "date": "2024-06-17",
                "temperature": 23,
                "windSpeed": 5.1,
                "weatherCondition": "light-rain"
            }
        ]
    }
]
```
### Получение прогноза погоды на неделю

- **URL:** `weather/weekly`
- **Метод:** GET
- **Параметры запроса:**
- `city` (обязательный, по умолчанию Москва): Название города, для которого запрашивается информация о погоде.
- `source` (обязательный, по умолчанию берется из конфигурации приложения): Имя вызываемого ресурса (yandex, weatherapi, all). Если указано значение all, то вызываются все возможные сервисы прогноза погоды.

#### Примеры запроса
```json
[
    {
        "source": "weatherapi",
        "city": "Москва",
        "weatherInfo": [
            {
                "date": "2024-06-17",
                "temperature": 18,
                "windSpeed": 16.2,
                "weatherCondition": "Patchy rain nearby"
            },
            {
                "date": "2024-06-18",
                "temperature": 18,
                "windSpeed": 15.5,
                "weatherCondition": "Patchy rain nearby"
            },
            {
                "date": "2024-06-19",
                "temperature": 20,
                "windSpeed": 11.2,
                "weatherCondition": "Sunny"
            },
            {
                "date": "2024-06-20",
                "temperature": 15,
                "windSpeed": 14.0,
                "weatherCondition": "Patchy rain nearby"
            },
            {
                "date": "2024-06-21",
                "temperature": 14,
                "windSpeed": 23.8,
                "weatherCondition": "Patchy rain nearby"
            },
            {
                "date": "2024-06-22",
                "temperature": 10,
                "windSpeed": 18.0,
                "weatherCondition": "Light drizzle"
            },
            {
                "date": "2024-06-23",
                "temperature": 12,
                "windSpeed": 13.3,
                "weatherCondition": "Overcast "
            }
        ]
    },
    {
        "source": "yandex",
        "city": "Москва",
        "weatherInfo": [
            {
                "date": "2024-06-17",
                "temperature": 23,
                "windSpeed": 5.1,
                "weatherCondition": "light-rain"
            },
            {
                "date": "2024-06-18",
                "temperature": 22,
                "windSpeed": 4.8,
                "weatherCondition": "rain"
            },
            {
                "date": "2024-06-19",
                "temperature": 26,
                "windSpeed": 5.1,
                "weatherCondition": "cloudy"
            },
            {
                "date": "2024-06-20",
                "temperature": 20,
                "windSpeed": 5.8,
                "weatherCondition": "light-rain"
            },
            {
                "date": "2024-06-21",
                "temperature": 20,
                "windSpeed": 5.8,
                "weatherCondition": "light-rain"
            },
            {
                "date": "2024-06-22",
                "temperature": 19,
                "windSpeed": 5.2,
                "weatherCondition": "cloudy"
            },
            {
                "date": "2024-06-23",
                "temperature": 19,
                "windSpeed": 3.1,
                "weatherCondition": "light-rain"
            }
        ]
    }
]
```
