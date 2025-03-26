# JavaFX Weather App

## Overview
The **JavaFX Weather App** is a simple yet visually appealing application that fetches real-time weather data using the OpenWeatherMap API. It allows users to search for weather conditions in different cities while dynamically updating the background and UI elements based on time of day and weather conditions.

## Features
- 🌤 Fetches real-time weather data from OpenWeatherMap API.
- 🌍 Supports both **Metric (°C, m/s)** and **Imperial (°F, mph)** units.
- 🎨 Dynamic background changes based on **time of day** and **weather conditions**.
- 📍 Allows users to search weather by city name.
- ☁ Displays weather conditions with an **icon representation**.
- 🔔 User-friendly error handling with alert dialogs.

## Technologies Used
- **JavaFX** for UI design and layout.
- **OpenWeatherMap API** for fetching weather data.
- **JSON Parsing** using `org.json.JSONObject`.
- **Java Networking (HttpURLConnection)** for API requests.

## Installation & Setup
### Prerequisites
- **Java 11+** installed
- **JavaFX SDK** configured in your IDE (Eclipse/IntelliJ IDEA)
- OpenWeatherMap API Key (Sign up at [OpenWeather](https://openweathermap.org/))

### Steps to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/javafx-weather-app.git
   cd javafx-weather-app
   ```
2. Open the project in your **Java IDE**.
3. Ensure JavaFX is correctly configured.
4. Replace the `API_KEY` in `Main.java` with your OpenWeatherMap API key.
   ```java
   private static final String API_KEY = "your_api_key_here";
   ```
5. Run the application from your IDE.

## Usage
- Enter a **city name** in the search box.
- Select the **unit system** (Metric or Imperial).
- Click **"Get Weather"** to fetch the latest data.
- The background updates based on:
  - **Time of day:** Morning, Afternoon, Evening, Night.
  - **Weather condition:** Clear, Rainy, Cloudy, etc.
- If an error occurs (invalid city, no internet), an alert message will appear.

## Dynamic Background Logic
| Time of Day | Background Image |
|------------|----------------|
| Morning (6 AM - 12 PM) | `morning.jpg` |
| Afternoon (12 PM - 6 PM) | `afternoon.jpg` |
| Evening (6 PM - 8 PM) | `evening.jpg` |
| Night (8 PM - 6 AM) | `night.jpg` |

| Weather Condition | Background |
|------------------|------------|
| Clear Sky | `clear.jpg` |
| Rainy | `rain.jpg` |
| Cloudy | `cloudy.jpg` |
| Snowy | `snow.jpg` |

## Project Structure
```
weather-app/
│── src/
│   ├── application/
│   │   ├── Main.java  # Main JavaFX Application
│   │   ├── WeatherService.java  # Handles API calls
│── resources/
│   ├── images/  # Background images (morning.jpg, night.jpg, etc.)
│── README.md  # Project Documentation
```

## Troubleshooting
- **Background images not loading?** Ensure the images are placed inside `resources/images/`.
- **API not working?** Verify your API key and internet connection.
- **JavaFX errors?** Check if JavaFX is properly added to your project dependencies.

## License
This project is licensed under the **MIT License**.

---

🚀 **Enjoy using the Weather App!** 🌦️

