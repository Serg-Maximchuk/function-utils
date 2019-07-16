package utils.functions;

import java.util.Objects;
import java.util.function.DoubleToIntFunction;

/**
 * {@link DoubleToIntFunction} that may sneaky throw checked {@link Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #applyAsIntThrows(double)}.
 *
 * @see DoubleToIntFunction
 * @see ThrowingFunction
 */
@FunctionalInterface
public interface ThrowingDoubleToIntFunction extends DoubleToIntFunction {

    /**
     * Unwrap {@link ThrowingDoubleToIntFunction}.
     *
     * @param function the one to be unwrapped
     * @return unwrapped {@link DoubleToIntFunction}
     */
    static DoubleToIntFunction unthrow(ThrowingDoubleToIntFunction function) {
        return function;
    }

    /**
     * Wrap input {@code function} as a throwing one.
     *
     * @param function the one to be wrapped
     * @return wrapped {@link ThrowingDoubleToIntFunction}
     * @throws NullPointerException if {@code function} is null
     */
    static ThrowingDoubleToIntFunction wrap(DoubleToIntFunction function) {
        return Objects.requireNonNull(function)::applyAsInt;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param function the one to map and return
     * @return the same function
     */
    static ThrowingDoubleToIntFunction map(ThrowingDoubleToIntFunction function) {
        return function;
    }


    /**
     * Applies this function to the given argument,
     * may throw checked {@link Exception}.
     *
     * @param value the function argument
     * @return the function result
     */
    int applyAsIntThrows(double value) throws Exception;


    @Override
    default int applyAsInt(double value) {
        try {
            return applyAsIntThrows(value);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingDoubleToIntFunction}.
     *
     * @return this unwrapped {@link DoubleToIntFunction}
     */
    default DoubleToIntFunction unthrow() {
        return this;
    }
}
