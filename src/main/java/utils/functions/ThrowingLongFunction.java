package utils.functions;

import java.util.Objects;
import java.util.function.LongFunction;

/**
 * {@link LongFunction} that may sneaky throw checked {@link Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #applyThrows(long)}.
 *
 * @param <R> the type of the result of the function
 * @see LongFunction
 * @see ThrowingFunction
 */
@FunctionalInterface
public interface ThrowingLongFunction<R> extends LongFunction<R> {

    /**
     * Unwrap {@link ThrowingLongFunction}.
     *
     * @param function the one to be unwrapped
     * @return unwrapped {@link LongFunction}
     */
    @SuppressWarnings("unchecked")
    static <T1> LongFunction<T1> unthrow(ThrowingLongFunction<? extends T1> function) {
        return (ThrowingLongFunction<T1>) function;
    }

    /**
     * Wrap input {@code function} as a throwing one.
     *
     * @param function the one to be wrapped
     * @return wrapped {@link ThrowingLongFunction}
     * @throws NullPointerException if {@code function} is null
     */
    static <T1> ThrowingLongFunction<T1> wrap(LongFunction<? extends T1> function) {
        return Objects.requireNonNull(function)::apply;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param function the one to map and return
     * @return the same function
     */
    @SuppressWarnings("unchecked")
    static <T1> ThrowingLongFunction<T1> map(ThrowingLongFunction<? extends T1> function) {
        return (ThrowingLongFunction<T1>) function;
    }


    /**
     * Applies this function to the given argument,
     * may throw checked {@link Exception}.
     *
     * @param value the function argument
     * @return the function result
     */
    R applyThrows(long value) throws Exception;


    @Override
    default R apply(long value) {
        try {
            return applyThrows(value);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingLongFunction}.
     *
     * @return this unwrapped {@link LongFunction}
     */
    default LongFunction<R> unthrow() {
        return this;
    }
}
