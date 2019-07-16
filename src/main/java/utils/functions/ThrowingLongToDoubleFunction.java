package utils.functions;

import java.util.Objects;
import java.util.function.LongToDoubleFunction;

/**
 * {@link LongToDoubleFunction} that may sneaky throw checked {@link Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #applyAsDoubleThrows(long)}.
 *
 * @see LongToDoubleFunction
 * @see ThrowingFunction
 */
@FunctionalInterface
public interface ThrowingLongToDoubleFunction extends LongToDoubleFunction {

    /**
     * Unwrap {@link ThrowingLongToDoubleFunction}.
     *
     * @param function the one to be unwrapped
     * @return unwrapped {@link LongToDoubleFunction}
     */
    static LongToDoubleFunction unthrow(ThrowingLongToDoubleFunction function) {
        return function;
    }

    /**
     * Wrap input {@code function} as a throwing one.
     *
     * @param function the one to be wrapped
     * @return wrapped {@link ThrowingLongToDoubleFunction}
     * @throws NullPointerException if {@code function} is null
     */
    static ThrowingLongToDoubleFunction wrap(LongToDoubleFunction function) {
        return Objects.requireNonNull(function)::applyAsDouble;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param function the one to map and return
     * @return the same function
     */
    static ThrowingLongToDoubleFunction map(ThrowingLongToDoubleFunction function) {
        return function;
    }


    /**
     * Applies this function to the given argument,
     * may throw checked {@link Exception}.
     *
     * @param value the function argument
     * @return the function result
     */
    double applyAsDoubleThrows(long value) throws Exception;


    @Override
    default double applyAsDouble(long value) {
        try {
            return applyAsDoubleThrows(value);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingLongToDoubleFunction}.
     *
     * @return this unwrapped {@link LongToDoubleFunction}
     */
    default LongToDoubleFunction unthrow() {
        return this;
    }
}
