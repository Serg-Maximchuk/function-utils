package utils.functions;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * {@code Supplier} that can throw checked {@code Exception}
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
     * Unwrap {@code ThrowingSupplier}.
     *
     * @param supplier the one to be unwrapped
     * @return unwrapped {@code Supplier}
     */
    @SuppressWarnings("unchecked")
    static <T1> Supplier<T1> unthrow(ThrowingSupplier<? extends T1> supplier) {
        return (ThrowingSupplier<T1>) supplier;
    }

    /**
     * Wrap input {@code supplier} as a throwing one.
     *
     * @param supplier the one to be wrapped
     * @return wrapped {@code ThrowingSupplier}
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
     * Gets a result, may throw checked {@code Exception}.
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
     * Unwrap this {@code ThrowingSupplier}.
     *
     * @return this unwrapped {@code Supplier}
     */
    default Supplier<T> unthrow() {
        return this;
    }

}
