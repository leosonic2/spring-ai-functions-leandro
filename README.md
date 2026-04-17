# Spring AI Functions - Weather Service

> **Educational Project** — This project is for learning purposes only and is part of the course material for [Spring AI: Beginner to Guru](https://www.udemy.com/course/spring-ai-beginner-to-guru/) on Udemy.

## Credits

- **Course Author:** John Thompson — [LinkedIn](https://www.linkedin.com/in/springguru/)
- **Website:** [Spring Framework Guru](https://springframework.guru)

## Overview

This project demonstrates how to use **Spring AI Function Calling** with OpenAI to integrate external APIs. It exposes a REST endpoint that accepts natural language weather questions and uses OpenAI's function calling capability to fetch real-time weather data from the [API Ninjas Weather API](https://api-ninjas.com/api/weather).

### How It Works

1. The user sends a natural language question (e.g., *"What's the weather in London?"*) via a POST request.
2. Spring AI forwards the question to OpenAI.
3. OpenAI determines that it needs weather data and triggers the `CurrentWeather` function callback.
4. The `WeatherServiceFunction` calls the API Ninjas Weather API and returns the result.
5. OpenAI returns the weather data as a structured JSON object matching the `WeatherResponse` schema.

## Tech Stack

- **Java 21+**
- **Spring Boot 3.x**
- **Spring AI 1.0.0-M5** (OpenAI)
- **API Ninjas Weather API**
- **Lombok**

## Prerequisites

- JDK 21 or later
- An **OpenAI API key**
- An **API Ninjas API key** — get one for free at [api-ninjas.com](https://api-ninjas.com/)

## Configuration

Set the following environment variables before running the application:

| Variable              | Description                  |
|-----------------------|------------------------------|
| `OPENAI_API_KEY`      | Your OpenAI API key          |
| `API_NINJAS_API_KEY`  | Your API Ninjas API key      |

### Example (PowerShell)

```powershell
$env:OPENAI_API_KEY = "sk-..."
$env:API_NINJAS_API_KEY = "your-api-ninjas-key"
```

### Example (Bash)

```bash
export OPENAI_API_KEY="sk-..."
export API_NINJAS_API_KEY="your-api-ninjas-key"
```

## Running the Application

```bash
./mvnw spring-boot:run
```

## Usage

Send a POST request to `/weather`:

```bash
curl -X POST http://localhost:8080/weather \
  -H "Content-Type: application/json" \
  -d '{"question": "What is the current weather in Tokyo, Japan?"}'
```

### Example Response

```json
{
  "weatherResponse": {
    "temp": 23,
    "humidity": 71,
    "sunset": 1776460117,
    "sunrise": 1776414677,
    "wind_speed": 8.24,
    "wind_degrees": 72,
    "min_temp": 23,
    "cloud_pct": 79,
    "feels_like": 23,
    "max_temp": 23
  }
}
```

### Response Fields

| Field          | Type    | Description                         |
|----------------|---------|-------------------------------------|
| `temp`         | Integer | Current temperature in Celsius      |
| `feels_like`   | Integer | "Feels like" temperature in Celsius |
| `min_temp`     | Integer | Minimum temperature in Celsius      |
| `max_temp`     | Integer | Maximum temperature in Celsius      |
| `humidity`     | Integer | Current humidity (%)                |
| `cloud_pct`    | Integer | Cloud coverage percentage           |
| `wind_speed`   | Decimal | Wind speed in KM/H                  |
| `wind_degrees` | Integer | Wind direction in degrees           |
| `sunrise`      | Integer | Sunrise time (GMT epoch)            |
| `sunset`       | Integer | Sunset time (GMT epoch)             |

## Project Structure

```
src/main/java/guru/springframework/springaifunctions/
├── SpringAiFunctionsApplication.java   # Main application entry point
├── controllers/
│   └── QuestionController.java         # REST controller for /weather endpoint
├── functions/
│   └── WeatherServiceFunction.java     # Function calling API Ninjas Weather API
├── model/
│   ├── Answer.java                     # Response model
│   ├── Question.java                   # Request model
│   ├── WeatherRequest.java             # Weather API request DTO
│   └── WeatherResponse.java            # Weather API response DTO
└── services/
    ├── OpenAIService.java              # Service interface
    └── OpenAIServiceImpl.java          # Service implementation with function callbacks
```

## License

This project is for educational purposes only.
