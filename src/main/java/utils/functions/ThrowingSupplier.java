package utils.functions;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * {@link Supplier} that can throw checked {@link Exception}.
 *
 * <p>This is a functional interface whose functional method
 * is {@link #getThrowing()}.
 *
 * @param <T> the type of results supplied by this supplier
 * @see Supplier
 */
@FunctionalInterface
public interface ThrowingSupplier<T> extends Supplier<T> {

    /**
     * Unwrap {@link ThrowingSupplier}.
     *
     * @param supplier the one to be unwrapped
     * @return unwrapped {@link Supplier}
     */
    @SuppressWarnings("unchecked")
    static <T1> Supplier<T1> unthrow(ThrowingSupplier<? extends T1> supplier) {
        return (ThrowingSupplier<T1>) supplier;
    }

    /**
     * Wrap input {@code supplier} as a throwing one.
     *
     * @param supplier the one to be wrapped
     * @return wrapped {@link ThrowingSupplier}
     * @throws NullPointerException if {@code supplier} is null
     */
    static <T1> ThrowingSupplier<T1> wrap(Supplier<? extends T1> supplier) {
        return Objects.requireNonNull(supplier)::get;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param supplier the one to map and return
     * @return the same supplier
     */
    @SuppressWarnings("unchecked")
    static <T1> ThrowingSupplier<T1> map(ThrowingSupplier<? extends T1> supplier) {
        return (ThrowingSupplier<T1>) supplier;
    }


    /**
     * Gets a result, may throw checked {@link Exception}.
     *
     * @return a result
     */
    T getThrowing() throws Exception;


    @Override
    default T get() {
        try {
            return getThrowing();
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingSupplier}.
     *
     * @return this unwrapped {@link Supplier}
     */
    default Supplier<T> unthrow() {
        return this;
    }

}
