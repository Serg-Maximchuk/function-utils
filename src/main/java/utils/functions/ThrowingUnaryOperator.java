package utils.functions;

import java.util.Objects;
import java.util.function.UnaryOperator;

/**
 * {@link UnaryOperator} that may sneaky throw checked {@link Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #applyThrowing(Object)}.
 *
 * @param <T> the type of the operand and result of the operator
 * @see UnaryOperator
 * @see ThrowingFunction
 * @see ThrowingBinaryOperator
 */
@FunctionalInterface
public interface ThrowingUnaryOperator<T> extends UnaryOperator<T>, ThrowingFunction<T, T> {

    /**
     * Unwrap {@link ThrowingUnaryOperator}.
     *
     * @param operator the one to be unwrapped
     * @return unwrapped {@link UnaryOperator}
     */
    static <T1> UnaryOperator<T1> unthrow(ThrowingUnaryOperator<T1> operator) {
        return operator;
    }

    /**
     * Wrap input {@code operator} as a throwing one.
     *
     * @param operator the one to be wrapped
     * @return wrapped {@link ThrowingUnaryOperator}
     * @throws NullPointerException if {@code operator} is null
     */
    static <T1> ThrowingUnaryOperator<T1> wrap(UnaryOperator<T1> operator) {
        return Objects.requireNonNull(operator)::apply;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param operator the one to map and return
     * @return the same operator
     */
    static <T1> ThrowingUnaryOperator<T1> map(ThrowingUnaryOperator<T1> operator) {
        return operator;
    }

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @param <T> the type of the input and output of the operator
     * @return a unary operator that always returns its input argument
     */
    static <T> ThrowingUnaryOperator<T> identity() {
        return t -> t;
    }


    /**
     * Applies this function to the given argument,
     * may throw checked {@link Exception}.
     *
     * @param t the function argument
     * @return the function result
     */
    T applyThrowing(T t) throws Exception;


    @Override
    default T apply(T t) {
        try {
            return applyThrowing(t);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingUnaryOperator}.
     *
     * @return this unwrapped {@link UnaryOperator}
     */
    @Override
    default UnaryOperator<T> unthrow() {
        return this;
    }

}
