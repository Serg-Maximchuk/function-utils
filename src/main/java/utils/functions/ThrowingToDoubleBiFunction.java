package utils.functions;

import java.util.Objects;
import java.util.function.ToDoubleBiFunction;

/**
 * {@link ToDoubleBiFunction} that can throw checked {@link Exception}.
 *
 * <p>This is a functional interface whose functional method
 * is {@link #applyAsDoubleThrows(Object, Object)}.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @see ToDoubleBiFunction
 * @see ThrowingBiFunction
 */
@FunctionalInterface
public interface ThrowingToDoubleBiFunction<T, U> extends ToDoubleBiFunction<T, U> {

    /**
     * Unwrap {@link ThrowingToDoubleBiFunction}.
     *
     * @param function the one to be unwrapped
     * @return unwrapped {@link ToDoubleBiFunction}
     */
    @SuppressWarnings("unchecked")
    static <T1, U1> ToDoubleBiFunction<T1, U1> unthrow(ThrowingToDoubleBiFunction<? super T1, ? super U1> function) {
        return (ThrowingToDoubleBiFunction<T1, U1>) function;
    }

    /**
     * Wrap input {@code function} as a throwing one.
     *
     * @param function the one to be wrapped
     * @return wrapped {@link ThrowingToDoubleBiFunction}
     * @throws NullPointerException if {@code function} is null
     */
    static <T1, U1> ThrowingToDoubleBiFunction<T1, U1> wrap(ToDoubleBiFunction<? super T1, ? super U1> function) {
        return Objects.requireNonNull(function)::applyAsDouble;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param function the one to map and return
     * @return the same function
     */
    @SuppressWarnings("unchecked")
    static <T1, U1> ThrowingToDoubleBiFunction<T1, U1> map(
            ThrowingToDoubleBiFunction<? super T1, ? super U1> function
    ) {
        return (ThrowingToDoubleBiFunction<T1, U1>) function;
    }


    /**
     * Applies this function to the given arguments,
     * may throw checked {@link Exception}.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
    double applyAsDoubleThrows(T t, U u) throws Exception;


    @Override
    default double applyAsDouble(T t, U u) {
        try {
            return applyAsDoubleThrows(t, u);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingToDoubleBiFunction}.
     *
     * @return this unwrapped {@link ToDoubleBiFunction}
     */
    default ToDoubleBiFunction<T, U> unthrow() {
        return this;
    }
}
