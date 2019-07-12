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

