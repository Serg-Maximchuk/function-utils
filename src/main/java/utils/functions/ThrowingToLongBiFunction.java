package utils.functions;

import java.util.Objects;
import java.util.function.ToLongBiFunction;

/**
 * {@link ToLongBiFunction} that can throw checked {@link Exception}.
 *
 * <p>This is a functional interface whose functional method
 * is {@link #applyAsLongThrows(Object, Object)}.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @see ToLongBiFunction
 * @see ThrowingBiFunction
 */
@FunctionalInterface
public interface ThrowingToLongBiFunction<T, U> extends ToLongBiFunction<T, U> {

    /**
     * Unwrap {@link ThrowingToLongBiFunction}.
     *
     * @param function the one to be unwrapped
     * @return unwrapped {@link ToLongBiFunction}
     */
    @SuppressWarnings("unchecked")
    static <T1, U1> ToLongBiFunction<T1, U1> unthrow(ThrowingToLongBiFunction<? super T1, ? super U1> function) {
        return (ThrowingToLongBiFunction<T1, U1>) function;
    }

    /**
     * Wrap input {@code function} as a throwing one.
     *
     * @param function the one to be wrapped
     * @return wrapped {@link ThrowingToLongBiFunction}
     * @throws NullPointerException if {@code function} is null
     */
    static <T1, U1> ThrowingToLongBiFunction<T1, U1> wrap(ToLongBiFunction<? super T1, ? super U1> function) {
        return Objects.requireNonNull(function)::applyAsLong;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param function the one to map and return
     * @return the same function
     */
    @SuppressWarnings("unchecked")
    static <T1, U1> ThrowingToLongBiFunction<T1, U1> map(ThrowingToLongBiFunction<? super T1, ? super U1> function) {
        return (ThrowingToLongBiFunction<T1, U1>) function;
    }


    /**
     * Applies this function to the given arguments,
     * may throw checked {@link Exception}.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
    long applyAsLongThrows(T t, U u) throws Exception;


    @Override
    default long applyAsLong(T t, U u) {
        try {
            return applyAsLongThrows(t, u);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingToLongBiFunction}.
     *
     * @return this unwrapped {@link ToLongBiFunction}
     */
    default ToLongBiFunction<T, U> unthrow() {
        return this;
    }
}
