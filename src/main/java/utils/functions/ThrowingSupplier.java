package utils.functions;

import java.util.function.Supplier;

@FunctionalInterface
public interface ThrowingSupplier<T> extends Supplier<T> {

    static <T1> Supplier<T1> unthrow(ThrowingSupplier<T1> supplier) {
        return supplier;
    }

    static <T1> ThrowingSupplier<T1> wrap(Supplier<T1> supplier) {
        return supplier::get;
    }

    /**
     * Use it when you don't want to cast lambda by yourself
     *
     * @param supplier the one to map and return
     * @return the same supplier
     */
    static <T1> ThrowingSupplier<T1> map(ThrowingSupplier<T1> supplier) {
        return supplier;
    }


    T getThrowing() throws Exception;


    @Override
    default T get() {
        try {
            return getThrowing();
        } catch (Exception e) {
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    default Supplier<T> unthrow() {
        return this;
    }

}
