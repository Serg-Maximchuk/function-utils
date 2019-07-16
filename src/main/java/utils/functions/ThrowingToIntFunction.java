package utils.functions;

import java.util.Objects;
import java.util.function.ToIntFunction;

/**
 * {@link ToIntFunction} that can throw checked {@link Exception}.
 *
 * <p>This is a functional interface whose functional method
 * is {@link #applyAsIntThrows(Object)}.
 *
 * @param <T> the type of the first argument to the function
 * @see ToIntFunction
 * @see ThrowingFunction
 */
@FunctionalInterface
public interface ThrowingToIntFunction<T> extends ToIntFunction<T> {

    /**
     * Unwrap {@link ThrowingToIntFunction}.
     *
     * @param function the one to be unwrapped
     * @return unwrapped {@link ToIntFunction}
     */
    @SuppressWarnings("unchecked")
    static <T1> ToIntFunction<T1> unthrow(ThrowingToIntFunction<? super T1> function) {
        return (ThrowingToIntFunction<T1>) function;
    }

    /**
     * Wrap input {@code function} as a throwing one.
     *
     * @param function the one to be wrapped
     * @return wrapped {@link ThrowingToIntFunction}
     * @throws NullPointerException if {@code function} is null
     */
    static <T1> ThrowingToIntFunction<T1> wrap(ToIntFunction<? super T1> function) {
        return Objects.requireNonNull(function)::applyAsInt;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param function the one to map and return
     * @return the same function
     */
    @SuppressWarnings("unchecked")
    static <T1> ThrowingToIntFunction<T1> map(ThrowingToIntFunction<? super T1> function) {
        return (ThrowingToIntFunction<T1>) function;
    }


    /**
     * Applies this function to the given argument,
     * may throw checked {@link Exception}.
     *
     * @param value the function argument
     * @return the function result
     */
    int applyAsIntThrows(T value) throws Exception;


    @Override
    default int applyAsInt(T value) {
        try {
            return applyAsIntThrows(value);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingToIntFunction}.
     *
     * @return this unwrapped {@link ToIntFunction}
     */
    default ToIntFunction<T> unthrow() {
        return this;
    }
}
