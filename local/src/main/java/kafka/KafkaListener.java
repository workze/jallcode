package kafka;

import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author wangguize
 * date 2019-09-13
 */
@Data
public class KafkaListener<K, V> {
    private Properties properties;
    private List<String> topics;
    private BiConsumer<K, V> keyValueMessageHandler;
    private Consumer<V> valueMessageHandler;
    private Consumer<ConsumerRecord<K, V>> recordMessageHandler;
    private Consumer beanMessageHandler;

    private volatile boolean running = false;

    public <T> KafkaListener beanMessageHandler(Consumer<T> consumer) {
        System.out.println("123");

        Type type = ((ParameterizedType)consumer.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        System.out.println(type);
        return this;
    }

    public static KafkaListener defaultListener(){
        return null;
    }

    synchronized public KafkaListener start() {
        if (!running) {
            createPollThread();
            running = true;
        }
        return this;
    }

    private void createPollThread() {
        ExecutorService pollService = Executors.newSingleThreadExecutor();
        pollService.execute(() -> {
            KafkaConsumer<K, V> kafkaConsumer = buildKafkaConsumer();
            ExecutorService messageHandlerService = new ThreadPoolExecutor(1, 4, 60, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(10000),
                    new ThreadPoolExecutor.CallerRunsPolicy());

            while (running) {
                ConsumerRecords<K, V> records = kafkaConsumer.poll(Duration.ofDays(Integer.MAX_VALUE));
                for (ConsumerRecord<K, V> record : records) {
                    doHandler(messageHandlerService, record);
                }
            }

            kafkaConsumer.close();
            messageHandlerService.shutdown();
        });
        pollService.shutdown();
    }

    private void doHandler(ExecutorService messageHandlerService, ConsumerRecord<K, V> record) {
        messageHandlerService.execute(() -> {
            try {
                if (recordMessageHandler != null) {
                    recordMessageHandler.accept(record);
                    return;
                }
                if (keyValueMessageHandler != null) {
                    keyValueMessageHandler.accept(record.key(), record.value());
                    return;
                }
                if (valueMessageHandler != null) {
                    valueMessageHandler.accept(record.value());
                    return;
                }


            } catch (Exception e) {
                //
            }
        });
    }

    private KafkaConsumer<K, V> buildKafkaConsumer() {
        KafkaConsumer<K, V> kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(topics);
        return kafkaConsumer;
    }

    synchronized private void stop() {
        if (running) {
            running = false;
        }
    }

    public static void main(String[] args) {
        new KafkaListener<String,String>().beanMessageHandler(new Consumer<Properties>() {
            @Override
            public void accept(Properties o) {
                System.out.println("hi");
            }
        });

        new KafkaListener<String,String>().beanMessageHandler((v)->{

        });
    }

}

