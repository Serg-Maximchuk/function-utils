package utils.functions;

import java.util.function.Consumer;

@FunctionalInterface
public interface ThrowingConsumer<T> extends Consumer<T> {

    static <T1> Consumer<T1> unthrow(ThrowingConsumer<T1> consumer) {
        return consumer;
    }

    static <T1> ThrowingConsumer<T1> wrap(Consumer<T1> consumer) {
        return consumer::accept;
    }

    /**
     * Use it when you don't want to cast lambda by yourself
     *
     * @param consumer the one to map and return
     * @return the same consumer
     */
    static <T1> ThrowingConsumer<T1> map(ThrowingConsumer<T1> consumer) {
        return consumer;
    }


    void acceptThrows(T t) throws Exception;


    @Override
    default void accept(T t) {
        try {
            acceptThrows(t);
        } catch (Exception e) {
            Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    default Consumer<T> unthrow() {
        return this;
    }

}
