package com.example.login;

public class CurrentWeather {
    final String location;
    final int conditionId;
    final String weatherCondition;
    final double tempKelvin;

    final int pressure;
    final int humidity;
    final double windspeed;
    final double temp_max;
    final double temp_min;

    public CurrentWeather(final String location,
                          final int conditionId,
                          final String weatherCondition,
                          final double tempKelvin, int pressure, int humidity, double windspeed, double temp_max, double temp_min) {
        this.location = location;
        this.conditionId = conditionId;
        this.weatherCondition = weatherCondition;
        this.tempKelvin = tempKelvin;

        this.pressure = pressure;
        this.humidity = humidity;
        this.windspeed = windspeed;
        this.temp_max = temp_max;
        this.temp_min = temp_min;
    }


    public int getTempCelsius() {
        return (int) (tempKelvin - 273.15);
    }
    public int getTempMaxCelsius() {
        return (int) (temp_max - 273.15);
    }
    public int getTempMinCelsius() {
        return (int) (temp_min - 273.15);
    }
}


