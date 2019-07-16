package utils.functions;

import java.util.Objects;
import java.util.function.ToLongFunction;

/**
 * {@link ToLongFunction} that can throw checked {@link Exception}.
 *
 * <p>This is a functional interface whose functional method
 * is {@link #applyAsLongThrows(Object)}.
 *
 * @param <T> the type of the first argument to the function
 * @see ToLongFunction
 * @see ThrowingFunction
 */
@FunctionalInterface
public interface ThrowingToLongFunction<T> extends ToLongFunction<T> {

    /**
     * Unwrap {@link ThrowingToLongFunction}.
     *
     * @param function the one to be unwrapped
     * @return unwrapped {@link ToLongFunction}
     */
    @SuppressWarnings("unchecked")
    static <T1> ToLongFunction<T1> unthrow(ThrowingToLongFunction<? super T1> function) {
        return (ThrowingToLongFunction<T1>) function;
    }

    /**
     * Wrap input {@code function} as a throwing one.
     *
     * @param function the one to be wrapped
     * @return wrapped {@link ThrowingToLongFunction}
     * @throws NullPointerException if {@code function} is null
     */
    static <T1> ThrowingToLongFunction<T1> wrap(ToLongFunction<? super T1> function) {
        return Objects.requireNonNull(function)::applyAsLong;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param function the one to map and return
     * @return the same function
     */
    @SuppressWarnings("unchecked")
    static <T1> ThrowingToLongFunction<T1> map(ThrowingToLongFunction<? super T1> function) {
        return (ThrowingToLongFunction<T1>) function;
    }


    /**
     * Applies this function to the given argument,
     * may throw checked {@link Exception}.
     *
     * @param value the function argument
     * @return the function result
     */
    long applyAsLongThrows(T value) throws Exception;


    @Override
    default long applyAsLong(T value) {
        try {
            return applyAsLongThrows(value);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingToLongFunction}.
     *
     * @return this unwrapped {@link ToLongFunction}
     */
    default ToLongFunction<T> unthrow() {
        return this;
    }
}
