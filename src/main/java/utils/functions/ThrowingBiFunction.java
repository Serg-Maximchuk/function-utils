package utils.functions;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * {@code BiFunction} that may sneaky throw checked {@code Exception}.
 *
 * This is a functional interface whose functional method
 * is {@link #applyThrows(Object, Object)}.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <R> the type of the result of the function
 *
 * @see ThrowingFunction
 * @see BiFunction
 */
@FunctionalInterface
public interface ThrowingBiFunction<T, U, R> extends BiFunction<T, U, R> {

    /**
     * Unwrap {@code ThrowingBiFunction}.
     *
     * @param function the one to be unwrapped
     * @return unwrapped {@code ThrowingBiFunction}
     */
    @SuppressWarnings("unchecked")
    static <T1, U1, R1> BiFunction<T1, U1, R1> unthrow(
            ThrowingBiFunction<? super T1, ? super U1, ? extends R1> function
    ) {
        return (BiFunction<T1, U1, R1>) function;
    }

    /**
     * Wrap input {@code function} as a throwing one.
     *
     * @param function the one to be wrapped
     * @return wrapped {@code ThrowingBiFunction}
     * @throws NullPointerException if {@code function} is null
     */
    static <T1, U1, R1> ThrowingBiFunction<T1, U1, R1> wrap(
            BiFunction<? super T1, ? super U1, ? extends R1> function
    ) {
        return Objects.requireNonNull(function)::apply;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param function the one to map and return
     * @return the same function
     */
    @SuppressWarnings("unchecked")
    static <T1, U1, R1> ThrowingBiFunction<T1, U1, R1> map(
            ThrowingBiFunction<? super T1, ? super U1, ? extends R1> function
    ) {
        return (ThrowingBiFunction<T1, U1, R1>) function;
    }


    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
    R applyThrows(T t, U u) throws Exception;


    @Override
    default R apply(T t, U u) {
        try {
            return applyThrows(t, u);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Returns a composed {@code ThrowingBiFunction} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code ThrowingBiFunction} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default <V> ThrowingBiFunction<T, U, V> throwingAndThen(ThrowingFunction<? super R, ? extends V> after) {
        return andThen(after);
    }

    /**
     * Returns a composed function that first applies this function to
     * its input, and then applies the {@code after} function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V>   the type of output of the {@code after} function, and of the
     *              composed function
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then
     * applies the {@code after} function
     * @throws NullPointerException if after is null
     */
    @Override
    default <V> ThrowingBiFunction<T, U, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t, U u) -> after.apply(apply(t, u));
    }

    /**
     * Unwrap this {@code ThrowingBiFunction}.
     *
     * @return this unwrapped {@code BiFunction}
     */
    default BiFunction<T, U, R> unthrow() {
        return this;
    }

}
