package com.im.concurrent.service.rocketmq;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeatherData {
    private float temperature;
    private float humidity;
    private float pressure;
}
