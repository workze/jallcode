package kafkastaff;

public interface MessageHandler<T> {
    void handleMessage(T object);
}
