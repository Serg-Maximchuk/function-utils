package utils.functions;

import java.util.Objects;
import java.util.function.IntFunction;

/**
 * {@link IntFunction} that may sneaky throw checked {@link Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #applyThrows(int)}.
 *
 * @param <R> the type of the result of the function
 * @see IntFunction
 * @see ThrowingFunction
 */
@FunctionalInterface
public interface ThrowingIntFunction<R> extends IntFunction<R> {

    /**
     * Unwrap {@link ThrowingIntFunction}.
     *
     * @param function the one to be unwrapped
     * @return unwrapped {@link IntFunction}
     */
    @SuppressWarnings("unchecked")
    static <T1> IntFunction<T1> unthrow(ThrowingIntFunction<? extends T1> function) {
        return (ThrowingIntFunction<T1>) function;
    }

    /**
     * Wrap input {@code function} as a throwing one.
     *
     * @param function the one to be wrapped
     * @return wrapped {@link ThrowingIntFunction}
     * @throws NullPointerException if {@code function} is null
     */
    static <T1> ThrowingIntFunction<T1> wrap(IntFunction<? extends T1> function) {
        return Objects.requireNonNull(function)::apply;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param function the one to map and return
     * @return the same function
     */
    @SuppressWarnings("unchecked")
    static <T1> ThrowingIntFunction<T1> map(ThrowingIntFunction<? extends T1> function) {
        return (ThrowingIntFunction<T1>) function;
    }


    /**
     * Applies this function to the given argument,
     * may throw checked {@link Exception}.
     *
     * @param value the function argument
     * @return the function result
     */
    R applyThrows(int value) throws Exception;


    @Override
    default R apply(int value) {
        try {
            return applyThrows(value);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingIntFunction}.
     *
     * @return this unwrapped {@link IntFunction}
     */
    default IntFunction<R> unthrow() {
        return this;
    }
}
