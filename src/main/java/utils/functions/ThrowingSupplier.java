package utils.functions;

import java.util.function.Supplier;

@FunctionalInterface
public interface ThrowingSupplier<T> extends Supplier<T> {

    @Override
    default T get() {
        try {
            return getThrowing();
        } catch (Exception e) {
            throw Functions.sneakyThrow(e);
        }
    }

    T getThrowing() throws Exception;
}
