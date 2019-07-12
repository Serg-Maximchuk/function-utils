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
