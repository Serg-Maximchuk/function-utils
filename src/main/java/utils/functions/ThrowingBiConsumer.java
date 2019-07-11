package utils.functions;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface ThrowingBiConsumer<T, U> extends BiConsumer<T, U> {

    void acceptThrows(T t, U u) throws Exception;

    @Override
    default void accept(T t, U u) {
        try {
            acceptThrows(t, u);
        } catch (Exception e) {
            Functions.sneakyThrow(e);
        }
    }
}

