package utils.functions;

import java.util.Objects;
import java.util.function.DoubleFunction;

/**
 * {@link DoubleFunction} that may sneaky throw checked {@code Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #applyThrows(double)}.
 *
 * @param <R> the type of the result of the function
 * @see DoubleFunction
 * @see ThrowingFunction
 */
@FunctionalInterface
public interface ThrowingDoubleFunction<R> extends DoubleFunction<R> {


    /**
     * Unwrap {@link ThrowingDoubleFunction}.
     *
     * @param function the one to be unwrapped
     * @return unwrapped {@link DoubleFunction}
     */
    @SuppressWarnings("unchecked")
    static <T1> DoubleFunction<T1> unthrow(ThrowingDoubleFunction<? extends T1> function) {
        return (ThrowingDoubleFunction<T1>) function;
    }

    /**
     * Wrap input {@code function} as a throwing one.
     *
     * @param function the one to be wrapped
     * @return wrapped {@link ThrowingDoubleFunction}
     * @throws NullPointerException if {@code function} is null
     */
    static <T1> ThrowingDoubleFunction<T1> wrap(DoubleFunction<? extends T1> function) {
        return Objects.requireNonNull(function)::apply;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param function the one to map and return
     * @return the same function
     */
    @SuppressWarnings("unchecked")
    static <T1> ThrowingDoubleFunction<T1> map(ThrowingDoubleFunction<? extends T1> function) {
        return (ThrowingDoubleFunction<T1>) function;
    }

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    R applyThrows(double value) throws Exception;

    @Override
    default R apply(double value) {
        try {
            return applyThrows(value);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingDoubleFunction}.
     *
     * @return this unwrapped {@link DoubleFunction}
     */
    default DoubleFunction<R> unthrow() {
        return this;
    }
}
