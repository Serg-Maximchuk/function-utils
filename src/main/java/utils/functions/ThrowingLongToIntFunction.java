package utils.functions;

import java.util.Objects;
import java.util.function.LongToIntFunction;

/**
 * {@link LongToIntFunction} that may sneaky throw checked {@link Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #applyAsIntThrows(long)}.
 *
 * @see LongToIntFunction
 * @see ThrowingFunction
 */
@FunctionalInterface
public interface ThrowingLongToIntFunction extends LongToIntFunction {

    /**
     * Unwrap {@link ThrowingLongToIntFunction}.
     *
     * @param function the one to be unwrapped
     * @return unwrapped {@link LongToIntFunction}
     */
    static LongToIntFunction unthrow(ThrowingLongToIntFunction function) {
        return function;
    }

    /**
     * Wrap input {@code function} as a throwing one.
     *
     * @param function the one to be wrapped
     * @return wrapped {@link ThrowingLongToIntFunction}
     * @throws NullPointerException if {@code function} is null
     */
    static ThrowingLongToIntFunction wrap(LongToIntFunction function) {
        return Objects.requireNonNull(function)::applyAsInt;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param function the one to map and return
     * @return the same function
     */
    static ThrowingLongToIntFunction map(ThrowingLongToIntFunction function) {
        return function;
    }


    /**
     * Applies this function to the given argument,
     * may throw checked {@link Exception}.
     *
     * @param value the function argument
     * @return the function result
     */
    int applyAsIntThrows(long value) throws Exception;


    @Override
    default int applyAsInt(long value) {
        try {
            return applyAsIntThrows(value);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingLongToIntFunction}.
     *
     * @return this unwrapped {@link LongToIntFunction}
     */
    default LongToIntFunction unthrow() {
        return this;
    }
}
