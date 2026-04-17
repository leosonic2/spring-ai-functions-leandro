package guru.springframework.springaifunctions.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.springaifunctions.functions.WeatherServiceFunction;
import guru.springframework.springaifunctions.model.Answer;
import guru.springframework.springaifunctions.model.WeatherRequest;
import guru.springframework.springaifunctions.model.Question;
import guru.springframework.springaifunctions.model.WeatherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jt, Spring Framework Guru.
 */
@RequiredArgsConstructor
@Service
public class OpenAIServiceImpl implements OpenAIService {

    @Value("${api-ninjas.api-key}")
    private String apiNinjasKey;

    final OpenAiChatModel openAiChatModel;
    final ObjectMapper objectMapper;

    @Override
    public Answer getAnswer(Question question) {

        String jsonSchema = ModelOptionsUtils.getJsonSchema(WeatherResponse.class, false);

        SystemMessage systemMessage = new SystemMessage(
                "You are a weather assistant. When you receive weather data from the function call, " +
                "respond ONLY with a valid JSON object matching this schema (no markdown, no extra text):\n" +
                jsonSchema);

        OpenAiChatOptions promptOptions = OpenAiChatOptions.builder()
                .functionCallbacks(List.of(FunctionCallback.builder()
                        .function("CurrentWeather", new WeatherServiceFunction(apiNinjasKey))
                        .description("Get the current weather for a location")
                        .inputType(WeatherRequest.class)
                                .responseConverter(response -> {
                                    String schema = ModelOptionsUtils.getJsonSchema(WeatherResponse.class, false);
                                    String json = ModelOptionsUtils.toJsonString(response);
                                    return schema + "\n" + json;
                                })
                        .build()))
                .build();

        Message userMessage = new PromptTemplate(question.question()).createMessage();

        ChatResponse response = openAiChatModel.call(new Prompt(List.of(systemMessage, userMessage), promptOptions));

        String content = response.getResult().getOutput().getContent();

        try {
            // Strip markdown code fences if present
            String jsonContent = content.trim();
            if (jsonContent.startsWith("```")) {
                jsonContent = jsonContent.replaceAll("^```\\w*\\n?", "").replaceAll("```$", "").trim();
            }
            WeatherResponse weatherResponse = objectMapper.readValue(jsonContent, WeatherResponse.class);
            return new Answer(weatherResponse);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse weather response: " + content, e);
        }
    }
}
