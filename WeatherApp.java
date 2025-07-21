import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherApp {

    // Replace with your OpenWeatherMap API key
    private static final String API_KEY = "YOUR_API_KEY";
    private static final String BASE_URL =
        "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";

    public static void main(String[] args) {
        String city = "London"; // You can change the city
        String urlString = String.format(BASE_URL, city, API_KEY);

        try {
            // Create URL object
            URL url = new URL(urlString);

            // Open connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Check response code
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                // Read response
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder responseContent = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    responseContent.append(inputLine);
                }
                in.close();

                // Parse JSON
                JSONObject json = new JSONObject(responseContent.toString());

                // Extract and print data
                String weather = json.getJSONArray("weather").getJSONObject(0).getString("description");
                JSONObject main = json.getJSONObject("main");
                double temp = main.getDouble("temp");
                int humidity = main.getInt("humidity");

                System.out.println("Weather in " + city + ":");
                System.out.println("Description: " + weather);
                System.out.println("Temperature: " + temp + "°C");
                System.out.println("Humidity: " + humidity + "%");

            } else {
                System.out.println("Error: Unable to fetch weather data. Response code: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
