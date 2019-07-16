package utils.functions;

import java.util.Objects;
import java.util.function.ToIntBiFunction;

/**
 * {@link ToIntBiFunction} that can throw checked {@link Exception}.
 *
 * <p>This is a functional interface whose functional method
 * is {@link #applyAsIntThrows(Object, Object)}.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @see ToIntBiFunction
 * @see ThrowingBiFunction
 */
@FunctionalInterface
public interface ThrowingToIntBiFunction<T, U> extends ToIntBiFunction<T, U> {

    /**
     * Unwrap {@link ThrowingToIntBiFunction}.
     *
     * @param function the one to be unwrapped
     * @return unwrapped {@link ToIntBiFunction}
     */
    @SuppressWarnings("unchecked")
    static <T1, U1> ToIntBiFunction<T1, U1> unthrow(ThrowingToIntBiFunction<? super T1, ? super U1> function) {
        return (ThrowingToIntBiFunction<T1, U1>) function;
    }

    /**
     * Wrap input {@code function} as a throwing one.
     *
     * @param function the one to be wrapped
     * @return wrapped {@link ThrowingToIntBiFunction}
     * @throws NullPointerException if {@code function} is null
     */
    static <T1, U1> ThrowingToIntBiFunction<T1, U1> wrap(ToIntBiFunction<? super T1, ? super U1> function) {
        return Objects.requireNonNull(function)::applyAsInt;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param function the one to map and return
     * @return the same function
     */
    @SuppressWarnings("unchecked")
    static <T1, U1> ThrowingToIntBiFunction<T1, U1> map(
            ThrowingToIntBiFunction<? super T1, ? super U1> function
    ) {
        return (ThrowingToIntBiFunction<T1, U1>) function;
    }


    /**
     * Applies this function to the given arguments,
     * may throw checked {@link Exception}.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
    int applyAsIntThrows(T t, U u) throws Exception;


    @Override
    default int applyAsInt(T t, U u) {
        try {
            return applyAsIntThrows(t, u);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingToIntBiFunction}.
     *
     * @return this unwrapped {@link ToIntBiFunction}
     */
    default ToIntBiFunction<T, U> unthrow() {
        return this;
    }
}
