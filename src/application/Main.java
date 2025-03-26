package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.json.JSONObject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.time.LocalTime;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.Pos;

public class Main extends Application {
    private static final String API_KEY = "e953bcf7899d99f7d563c3fd334a023a";
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&units=%s&appid=%s";

    private Label temperatureLabel, humidityLabel, windSpeedLabel, conditionLabel;
    private TextField cityInput;
    private ImageView weatherIcon;
    private ComboBox<String> unitSelector;
    private VBox rootPane;

    private void updateBackground(Pane rootPane, String weatherCondition) {
        LocalTime currentTime = LocalTime.now();
        String imageUrl = "";

        if (weatherCondition.contains("clear")) {
            if (currentTime.isAfter(LocalTime.of(6, 0)) && currentTime.isBefore(LocalTime.of(18, 0))) {
                imageUrl = "/sunny_day.jpg";
            } else {
                imageUrl = "/clear_night.jpg";
            }
        } else if (weatherCondition.contains("cloud")) {
            imageUrl = "/cloudy.jpg";
        } else if (weatherCondition.contains("rain") || weatherCondition.contains("drizzle")) {
            imageUrl = "/rainy.jpg";
        } else if (weatherCondition.contains("thunderstorm")) {
            imageUrl = "/storm.jpg";
        } else if (weatherCondition.contains("snow")) {
            imageUrl = "/snow.jpg";
        } else {
            imageUrl = "/default.jpg";
        }

        try {
        	Image backgroundImage = new Image(getClass().getResourceAsStream(imageUrl));

            rootPane.setBackground(new Background(new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true)
            )));
        } catch (Exception e) {
            System.out.println("❌ Error loading background image: " + imageUrl);
            e.printStackTrace();
        }
    }


    @Override
  public void start(Stage primaryStage) {
        primaryStage.setTitle("Weather App");

        // UI Components
        cityInput = new TextField();
        cityInput.setPromptText("Enter city name");
        cityInput.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-background-radius: 10px;");

        Button searchButton = new Button("Get Weather");
        searchButton.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-background-radius: 10px;");
        
        unitSelector = new ComboBox<>();
        unitSelector.getItems().addAll("metric", "imperial");
        unitSelector.setValue("metric");
        unitSelector.setStyle("-fx-font-size: 14px; -fx-padding: 5px; -fx-background-radius: 10px;");

        // Labels
        temperatureLabel = new Label("Temperature: --");
        humidityLabel = new Label("Humidity: --");
        windSpeedLabel = new Label("Wind Speed: --");
        conditionLabel = new Label("Condition: --");
        
        Label[] labels = {temperatureLabel, humidityLabel, windSpeedLabel, conditionLabel};
        for (Label label : labels) {
            label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            label.setTextFill(Color.WHITE);
        }

        weatherIcon = new ImageView();

        searchButton.setOnAction(e -> fetchWeather());

        // Layout
        rootPane = new VBox(15);
        rootPane.setPadding(new Insets(20));
        rootPane.setAlignment(Pos.CENTER);
        ((VBox) rootPane).getChildren().addAll(cityInput, unitSelector, searchButton, weatherIcon, temperatureLabel, humidityLabel, windSpeedLabel, conditionLabel);

        // Set background color (light blue gradient)
//        rootPane.setStyle("-fx-background-color: linear-gradient(to bottom, #87CEEB, #4682B4);");

        updateBackground(rootPane, "clear");

        Scene scene = new Scene(rootPane, 350, 450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void fetchWeather() {
        String city = cityInput.getText().trim();
        String units = unitSelector.getValue();

        if (city.isEmpty()) {
            showAlert("Error", "Please enter a city name.");
            return;
        }

        try {
            String urlString = String.format(API_URL, city, units, API_KEY);
            HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
            conn.setRequestMethod("GET");

            Scanner scanner = new Scanner(conn.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            parseWeatherData(response.toString());
        } catch (IOException e) {
            showAlert("Error", "Failed to retrieve weather data.");
        }
    }

    private void parseWeatherData(String response) {
        JSONObject json = new JSONObject(response);
        double temperature = json.getJSONObject("main").getDouble("temp");
        int humidity = json.getJSONObject("main").getInt("humidity");
        double windSpeed = json.getJSONObject("wind").getDouble("speed");
        String condition = json.getJSONArray("weather").getJSONObject(0).getString("description");
        String iconCode = json.getJSONArray("weather").getJSONObject(0).getString("icon");

        String unitSystem = unitSelector.getValue();
        String temperatureUnit = unitSystem.equals("metric") ? "°C" : "°F";
        String windSpeedUnit = unitSystem.equals("metric") ? "m/s" : "mph";

        temperatureLabel.setText("Temperature: " + temperature + temperatureUnit);
        humidityLabel.setText("Humidity: " + humidity + "%");
        windSpeedLabel.setText("Wind Speed: " + windSpeed + " " + windSpeedUnit);
        conditionLabel.setText("Condition: " + condition);

        String iconUrl = "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";
        weatherIcon.setImage(new Image(iconUrl));

        updateBackground(rootPane, condition.toLowerCase());
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
