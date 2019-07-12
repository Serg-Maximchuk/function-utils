package utils.functions;

import java.util.Objects;
import java.util.function.Function;

/**
 * Function that can throw checked {@code Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #applyThrowing(Object)}.
 *
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 * @see Function
 */
@FunctionalInterface
public interface ThrowingFunction<T, R> extends Function<T, R> {

    /**
     * Unwrap {@code ThrowingFunction}.
     *
     * @param function the one to be unwrapped
     * @return unwrapped {@code Function}
     */
    @SuppressWarnings("unchecked")
    static <T1, R1> Function<T1, R1> unthrow(ThrowingFunction<? super T1, ? extends R1> function) {
        return (ThrowingFunction<T1, R1>) function;
    }

    /**
     * Wrap input {@code function} as a throwing one.
     *
     * @param function the one to be wrapped
     * @return wrapped {@code ThrowingFunction}
     * @throws NullPointerException if {@code function} is null
     */
    @SuppressWarnings("unchecked")
    static <T1, R1> ThrowingFunction<T1, R1> wrap(Function<? super T1, ? extends R1> function) {
        return Objects.requireNonNull((Function<T1, R1>) function)::apply;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param function the one to map and return
     * @return the same function
     */
    @SuppressWarnings("unchecked")
    static <T1, U1> ThrowingFunction<T1, U1> map(ThrowingFunction<? super T1, ? extends U1> function) {
        return (ThrowingFunction<T1, U1>) function;
    }


    /**
     * Applies this function to the given argument,
     * may throw checked {@code Exception}.
     *
     * @param t the function argument
     * @return the function result
     */
    R applyThrowing(T t) throws Exception;


    @Override
    default R apply(T t) {
        try {
            return applyThrowing(t);
        } catch (Exception e) {
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V>    the type of input to the {@code before} function, and to the
     *               composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     * @see #compose(Function)
     * @see #andThen(Function)
     * @see #andThenThrowing(ThrowingFunction)
     */
    default <V> ThrowingFunction<V, R> composeThrowing(ThrowingFunction<? super V, ? extends T> before) {
        return compose(before);
    }

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V>    the type of input to the {@code before} function, and to the
     *               composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     * @see #composeThrowing(ThrowingFunction)
     * @see #andThen(Function)
     * @see #andThenThrowing(ThrowingFunction)
     */
    @Override
    default <V> ThrowingFunction<V, R> compose(Function<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
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
     * @see #compose(Function)
     * @see #composeThrowing(ThrowingFunction)
     * @see #andThen(Function)
     */
    default <V> ThrowingFunction<T, V> andThenThrowing(ThrowingFunction<? super R, ? extends V> after) {
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
     * @see #compose(Function)
     * @see #composeThrowing(ThrowingFunction)
     * @see #andThenThrowing(ThrowingFunction)
     */
    @Override
    default <V> ThrowingFunction<T, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

    /**
     * Unwrap this {@code ThrowingFunction}.
     *
     * @return this unwrapped {@code Function}
     */
    default Function<T, R> unthrow() {
        return this;
    }

}
