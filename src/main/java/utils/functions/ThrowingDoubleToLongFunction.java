package utils.functions;

import java.util.Objects;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;

/**
 * {@link DoubleToLongFunction} that may sneaky throw checked {@code Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #applyAsLongThrows(double)}.
 *
 * @see DoubleToIntFunction
 * @see ThrowingFunction
 */
@FunctionalInterface
public interface ThrowingDoubleToLongFunction extends DoubleToLongFunction {

    /**
     * Unwrap {@link ThrowingDoubleToLongFunction}.
     *
     * @param function the one to be unwrapped
     * @return unwrapped {@link DoubleToLongFunction}
     */
    static DoubleToLongFunction unthrow(ThrowingDoubleToLongFunction function) {
        return function;
    }

    /**
     * Wrap input {@code function} as a throwing one.
     *
     * @param function the one to be wrapped
     * @return wrapped {@link ThrowingDoubleToLongFunction}
     * @throws NullPointerException if {@code function} is null
     */
    static ThrowingDoubleToLongFunction wrap(DoubleToLongFunction function) {
        return Objects.requireNonNull(function)::applyAsLong;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param function the one to map and return
     * @return the same function
     */
    static ThrowingDoubleToLongFunction map(ThrowingDoubleToLongFunction function) {
        return function;
    }


    /**
     * Applies this function to the given argument,
     * may throw checked {@code Exception}.
     *
     * @param value the function argument
     * @return the function result
     */
    long applyAsLongThrows(double value) throws Exception;


    @Override
    default long applyAsLong(double value) {
        try {
            return applyAsLongThrows(value);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingDoubleToLongFunction}.
     *
     * @return this unwrapped {@link DoubleToLongFunction}
     */
    default DoubleToLongFunction unthrow() {
        return this;
    }
}
