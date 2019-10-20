package kafka;

/**
 * @author wangguize
 * date 2019-08-10
 */
public abstract class Handler<T> {
    public abstract void handle(T t);
}
