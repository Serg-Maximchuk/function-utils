package utils.functions;

import java.util.Objects;
import java.util.function.ToDoubleFunction;

/**
 * {@link ToDoubleFunction} that can throw checked {@link Exception}.
 *
 * <p>This is a functional interface whose functional method
 * is {@link #applyAsDoubleThrows(Object)}.
 *
 * @param <T> the type of the first argument to the function
 * @see ToDoubleFunction
 * @see ThrowingFunction
 */
@FunctionalInterface
public interface ThrowingToDoubleFunction<T> extends ToDoubleFunction<T> {

    /**
     * Unwrap {@link ThrowingToDoubleFunction}.
     *
     * @param function the one to be unwrapped
     * @return unwrapped {@link ToDoubleFunction}
     */
    @SuppressWarnings("unchecked")
    static <T1> ToDoubleFunction<T1> unthrow(ThrowingToDoubleFunction<? super T1> function) {
        return (ThrowingToDoubleFunction<T1>) function;
    }

    /**
     * Wrap input {@code function} as a throwing one.
     *
     * @param function the one to be wrapped
     * @return wrapped {@link ThrowingToDoubleFunction}
     * @throws NullPointerException if {@code function} is null
     */
    static <T1> ThrowingToDoubleFunction<T1> wrap(ToDoubleFunction<? super T1> function) {
        return Objects.requireNonNull(function)::applyAsDouble;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param function the one to map and return
     * @return the same function
     */
    @SuppressWarnings("unchecked")
    static <T1> ThrowingToDoubleFunction<T1> map(ThrowingToDoubleFunction<? super T1> function) {
        return (ThrowingToDoubleFunction<T1>) function;
    }


    /**
     * Applies this function to the given argument,
     * may throw checked {@link Exception}.
     *
     * @param value the function argument
     * @return the function result
     */
    double applyAsDoubleThrows(T value) throws Exception;


    @Override
    default double applyAsDouble(T value) {
        try {
            return applyAsDoubleThrows(value);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingToDoubleFunction}.
     *
     * @return this unwrapped {@link ToDoubleFunction}
     */
    default ToDoubleFunction<T> unthrow() {
        return this;
    }

}
