package utils.functions;

import java.util.Objects;
import java.util.function.IntToDoubleFunction;

/**
 * {@link IntToDoubleFunction} that may sneaky throw checked {@link Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #applyAsDoubleThrows(int)}.
 *
 * @see IntToDoubleFunction
 * @see ThrowingFunction
 */
@FunctionalInterface
public interface ThrowingIntToDoubleFunction extends IntToDoubleFunction {

    /**
     * Unwrap {@link ThrowingIntToDoubleFunction}.
     *
     * @param function the one to be unwrapped
     * @return unwrapped {@link IntToDoubleFunction}
     */
    static IntToDoubleFunction unthrow(ThrowingIntToDoubleFunction function) {
        return function;
    }

    /**
     * Wrap input {@code function} as a throwing one.
     *
     * @param function the one to be wrapped
     * @return wrapped {@link ThrowingIntToDoubleFunction}
     * @throws NullPointerException if {@code function} is null
     */
    static ThrowingIntToDoubleFunction wrap(IntToDoubleFunction function) {
        return Objects.requireNonNull(function)::applyAsDouble;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param function the one to map and return
     * @return the same function
     */
    static ThrowingIntToDoubleFunction map(ThrowingIntToDoubleFunction function) {
        return function;
    }


    /**
     * Applies this function to the given argument,
     * may throw checked {@link Exception}.
     *
     * @param value the function argument
     * @return the function result
     */
    double applyAsDoubleThrows(int value) throws Exception;


    @Override
    default double applyAsDouble(int value) {
        try {
            return applyAsDoubleThrows(value);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingIntToDoubleFunction}.
     *
     * @return this unwrapped {@link IntToDoubleFunction}
     */
    default IntToDoubleFunction unthrow() {
        return this;
    }
}
