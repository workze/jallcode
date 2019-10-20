package kafka.handler;

@FunctionalInterface
public interface MessageHandler<T> {
    void handle(T t);
}
