package guru.springframework.springaifunctions.functions;

import guru.springframework.springaifunctions.model.WeatherRequest;
import guru.springframework.springaifunctions.model.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClient;

import java.util.function.Function;
@Slf4j
public class WeatherServiceFunction implements Function<WeatherRequest, WeatherResponse> {

    public static final String WEATHER_URL = "https://api.api-ninjas.com/v1/weather";

    private final String apiNinjasKey;

    public WeatherServiceFunction(String apiNinjasKey) {
        this.apiNinjasKey = apiNinjasKey;
    }

    @Override
    public WeatherResponse apply(WeatherRequest weatherRequest) {
        RestClient restClient = RestClient
                .builder()
                .baseUrl(WEATHER_URL)
                .defaultHeaders(httpHeaders->{
                    httpHeaders.set("X-Api-Key", apiNinjasKey);
                    httpHeaders.set("Accept", "application/json");
                    httpHeaders.set("Content-Type", "application/json");
                })
                .build();

        return restClient.get().uri(uriBuilder -> {
            log.info("Building URI for weather request: {}", weatherRequest);

            if (weatherRequest.location() != null && !weatherRequest.location().isBlank()) {
                // Parse "City, State" or "City, Country" format if present
                String city = weatherRequest.location().trim();
                if (city.contains(",")) {
                    String[] parts = city.split(",", 2);
                    city = parts[0].trim();
                    String extra = parts[1].trim();
                    // If state/country not already set, use the parsed value
                    if ((weatherRequest.state() == null || weatherRequest.state().isBlank())
                            && (weatherRequest.country() == null || weatherRequest.country().isBlank())) {
                        // Two-letter code likely a US state abbreviation or country
                        if (extra.length() == 2) {
                            uriBuilder.queryParam("state", extra);
                        } else {
                            uriBuilder.queryParam("country", extra);
                        }
                    }
                }
                uriBuilder.queryParam("city", city);
            }
            if (weatherRequest.state() != null && !weatherRequest.state().isBlank()) {
                uriBuilder.queryParam("state", weatherRequest.state());
            }
            if (weatherRequest.country() != null && !weatherRequest.country().isBlank()) {
                uriBuilder.queryParam("country", weatherRequest.country());
            }
            if (weatherRequest.latitude() != null && !weatherRequest.latitude().isBlank()) {
                uriBuilder.queryParam("lat", weatherRequest.latitude());
            }
            if (weatherRequest.longitude() != null && !weatherRequest.longitude().isBlank()) {
                uriBuilder.queryParam("lon", weatherRequest.longitude());
            }
            return uriBuilder.build();
        }).retrieve().body(WeatherResponse.class);
    }

}
