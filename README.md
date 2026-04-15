# Spring AI Functions

A Spring Boot application that demonstrates how to integrate **Spring AI** with **OpenAI** to build an AI-powered REST API. This project showcases the use of Spring AI's `ChatModel` abstraction to interact with the GPT-4 model through a clean, layered architecture.

## 📚 About

This project is for **educational purposes only** and is part of the hands-on material from the Udemy course:

> 🎓 **[Spring AI: Beginner to Guru](https://www.udemy.com/course/spring-ai-beginner-to-guru/)**
>
> Created by **John Thompson** — [LinkedIn](https://www.linkedin.com/in/springguru/)

All credits for the original course content and project design go to **John Thompson** ([Spring Framework Guru](https://springframework.guru)).

## 🛠️ Tech Stack

| Technology         | Version       |
|--------------------|---------------|
| Java               | 21            |
| Spring Boot        | 3.3.6         |
| Spring AI (OpenAI) | 1.0.0-M5      |
| Lombok             | (managed)     |
| Maven              | Wrapper (mvnw)|

## 📁 Project Structure

```
src/main/java/guru/springframework/springaifunctions/
├── SpringAiFunctionsApplication.java   # Application entry point
├── controllers/
│   └── QuestionController.java         # REST controller exposing /weather endpoint
├── model/
│   ├── Answer.java                     # Response record
│   └── Question.java                   # Request record
└── services/
    ├── OpenAIService.java              # Service interface
    └── OpenAIServiceImpl.java          # Service implementation using ChatModel
```

## 🚀 Getting Started

### Prerequisites

- **Java 21** or later
- An **OpenAI API key** ([get one here](https://platform.openai.com/api-keys))

### Running the Application

1. **Clone the repository:**

   ```bash
   git clone https://github.com/leosonic2/spring-ai-functions-leandro.git
   cd spring-ai-functions
   ```

2. **Set your OpenAI API key** as an environment variable:

   ```bash
   # Linux / macOS
   export OPENAI_API_KEY=your-api-key

   # Windows (PowerShell)
   $env:OPENAI_API_KEY="your-api-key"
   ```

3. **Build and run:**

   ```bash
   ./mvnw spring-boot:run
   ```

   On Windows:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

The application starts on **http://localhost:8080** by default.

### Example Request

```bash
curl -X POST http://localhost:8080/weather \
  -H "Content-Type: application/json" \
  -d '{"question": "What is the weather like in London?"}'
```

**Response:**

```json
{
  "answer": "..."
}
```

## ⚙️ Configuration

The application is configured via `src/main/resources/application.yaml`:

```yaml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      chat:
        options:
          model: gpt-4
```

You can change the model (e.g., `gpt-3.5-turbo`, `gpt-4o`) by updating the `model` property.

## 📝 License

This project is intended for educational use as part of the [Spring AI: Beginner to Guru](https://www.udemy.com/course/spring-ai-beginner-to-guru/) course by **John Thompson**.

