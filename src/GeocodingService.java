import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;

public class GeocodingService {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 YaBrowser/24.12.0.0 Safari/537.36";  // Ваш User-Agent

    // Метод для получения координат по адресу
    public static double[] getCoordinates(String address) {
        try {
            // кодировка адреса в корректном формате
            String encodedAddress = URLEncoder.encode(address, "UTF-8");

            // формирование URL
            String urlString = "https://nominatim.openstreetmap.org/search?q=" +
                    encodedAddress +
                    "&format=json&limit=1";

            // создание HttpClient для выполнения запроса
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .header("User-Agent", USER_AGENT)  // Устанавливаем заголовок User-Agent
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // преобразование строки ответа в json объект
            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonArray().get(0).getAsJsonObject();

            // извлечение координат
            if (jsonResponse != null && jsonResponse.has("lat") && jsonResponse.has("lon")) {
                double lat = jsonResponse.get("lat").getAsDouble();
                double lon = jsonResponse.get("lon").getAsDouble();
                return new double[]{lat, lon};
            } else {
                System.out.println("Не удалось найти координаты для данного адреса.");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;  // В случае ошибки
    }
}
