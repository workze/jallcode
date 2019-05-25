package kafkastaff;

public @interface KafkaSimpleConsumer {
    String topic();
    String group();
}
