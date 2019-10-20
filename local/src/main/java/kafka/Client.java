package kafka;


import lombok.Builder;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;

/**
 * @author wangguize
 * date 2019-08-10
 */
public class Client {
    public static void main(String[] args) {
        //KafkaPool.register(new MyHandler());
        //new KafkaListenerBuilder().property(new Properties()).messageHandler((v)->{}).build().startListen();


    }
}

@Builder
class KafkaListen<T> {
    Consumer<T> messageHandler;
    Properties properties;

    public static void main(String[] args) {
    }

}

@Setter
class KafkaListenerBuilder <T> {

    Consumer consumer;
    List<String> topics;
    String groupId;
    String bootstrapServers;

    public KafkaListenerBuilder property(Properties properties) {
        return this;
    }

    public KafkaListenerBuilder messageHandler(Consumer<T> consumer){
        this.consumer = consumer;
        return this;
    }

}

@Kafka(topic = "")
class MyHandler extends Handler<Date> {

    @Override
    public void handle(Date date) {

    }
}
