package RocketMQ;

public class WeatherDisplay implements Observer{

    @Override
    public void update(WeatherData weatherData) {
        System.out.println("天气数据更新: " + weatherData.toString());
    }
}
