package RocketMQ;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ProducerApp {
    private static MQProducer producer;

    public static void main(String[] args) throws Exception {

        producer = getDefaultProducer("weather_group");
        producer.start();
        WeatherData weatherData = new WeatherData(36f, 25f, 1000f);
        //发送普通消息
        Message message = new Message("weather", JSON.toJSONBytes(weatherData));
        SendResult nomalResult = producer.send(message);
        System.out.println(nomalResult);

        //顺序消息，分区有序性
        Integer hashKey = 123;
        SendResult sortedResult = producer.send(message, new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                Integer id = (Integer) o;
                int index = id % list.size();
                return list.get(index);
            }
        }, hashKey);
        producer.shutdown();

        //延迟消息
       // message.setDelayTimeLevel(4); 1s 5s 10s 30s 1~10m 20m 30m 1~2h 共18个级别

    }


    private static MQProducer getDefaultProducer(String groupName) throws Exception{
        InputStream inputStream = ProducerApp.class.getClassLoader().getResourceAsStream("application.properties");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        StringBuffer url =new StringBuffer();
        while ((line = bufferedReader.readLine()) != null) {
            if (line.startsWith("rocketmq.namesrv.address")){
                String[] str = line.split("=");
                if (str.length <2 || StringUtils.isBlank(str[1])){
                    throw new Exception("地址为空");
                }
                url.append(str[1]).append(":");
            }
            if (line.startsWith("rocketmq.namesrv.port")){
                String[] str2 = line.split("=");
                if (str2.length <2 || StringUtils.isBlank(str2[1])){
                    throw new Exception("端口为空");
                }
                url.append(str2[1]);
            }
        }

        DefaultMQProducer producer = new DefaultMQProducer(groupName);
        producer.setNamesrvAddr(url.toString());
        producer.setRetryTimesWhenSendAsyncFailed(2);
        return  producer;
    }
}
