package com.im.concurrent.service.rocketmq;

import lombok.Data;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
@Data
public class Weather implements Subject{
    private Queue<Observer> observerQueue = new ConcurrentLinkedQueue<>();
    private WeatherData weatherData;

    @Override
    public void registryObservers(Observer o) {
        observerQueue.offer(o);
    }

    @Override
    public void removeObservers(Observer o) {
        observerQueue.remove();
    }

    @Override
    public void notifyObservers() {
        observerQueue.forEach(queue -> {
            queue.update(weatherData);
        });
    }

    @Override
    public void setData(float temperature,float humidity,float pressure) {
        this.weatherData = new WeatherData(temperature, humidity, pressure);
        notifyObservers();
    }

}
