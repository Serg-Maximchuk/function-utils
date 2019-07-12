package utils.functions;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface ThrowingBiConsumer<T, U> extends BiConsumer<T, U> {

    static <T1, U1> BiConsumer<T1, U1> unthrow(ThrowingBiConsumer<T1, U1> consumer) {
        return consumer;
    }

    static <T1, U1> ThrowingBiConsumer<T1, U1> wrap(BiConsumer<T1, U1> consumer) {
        return consumer::accept;
    }

    /**
     * Use it when you don't want to cast lambda by yourself
     *
     * @param consumer the one to map and return
     * @return the same consumer
     */
    static <T1, U1> ThrowingBiConsumer<T1, U1> map(ThrowingBiConsumer<T1, U1> consumer) {
        return consumer;
    }


    void acceptThrows(T t, U u) throws Exception;


    @Override
    default void accept(T t, U u) {
        try {
            acceptThrows(t, u);
        } catch (Exception e) {
            Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    default BiConsumer<T, U> unthrow() {
        return this;
    }

}

