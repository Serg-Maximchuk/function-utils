package utils.functions;

import java.util.Objects;
import java.util.function.IntToLongFunction;

/**
 * {@link IntToLongFunction} that may sneaky throw checked {@link Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #applyAsLongThrows(int)}.
 *
 * @see IntToLongFunction
 * @see ThrowingFunction
 */
@FunctionalInterface
public interface ThrowingIntToLongFunction extends IntToLongFunction {

    /**
     * Unwrap {@link ThrowingIntToLongFunction}.
     *
     * @param function the one to be unwrapped
     * @return unwrapped {@link IntToLongFunction}
     */
    static IntToLongFunction unthrow(ThrowingIntToLongFunction function) {
        return function;
    }

    /**
     * Wrap input {@code function} as a throwing one.
     *
     * @param function the one to be wrapped
     * @return wrapped {@link ThrowingIntToLongFunction}
     * @throws NullPointerException if {@code function} is null
     */
    static ThrowingIntToLongFunction wrap(IntToLongFunction function) {
        return Objects.requireNonNull(function)::applyAsLong;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param function the one to map and return
     * @return the same function
     */
    static ThrowingIntToLongFunction map(ThrowingIntToLongFunction function) {
        return function;
    }


    /**
     * Applies this function to the given argument,
     * may throw checked {@link Exception}.
     *
     * @param value the function argument
     * @return the function result
     */
    long applyAsLongThrows(int value) throws Exception;


    @Override
    default long applyAsLong(int value) {
        try {
            return applyAsLongThrows(value);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingIntToLongFunction}.
     *
     * @return this unwrapped {@link IntToLongFunction}
     */
    default IntToLongFunction unthrow() {
        return this;
    }
}
