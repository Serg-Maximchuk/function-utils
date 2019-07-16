package utils.functions;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.BinaryOperator;

/**
 * {@link BinaryOperator} that may sneaky throw checked {@link Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #applyThrows(Object, Object)}.
 *
 * @param <T> the type of the operands and result of the operator
 * @see BinaryOperator
 * @see ThrowingBiFunction
 * @see ThrowingUnaryOperator
 */
@FunctionalInterface
public interface ThrowingBinaryOperator<T> extends BinaryOperator<T>, ThrowingBiFunction<T, T, T> {

    /**
     * Unwrap {@link ThrowingBinaryOperator}.
     *
     * @param operator the one to be unwrapped
     * @return unwrapped {@link BinaryOperator}
     */
    static <T1> BinaryOperator<T1> unthrow(ThrowingBinaryOperator<T1> operator) {
        return operator;
    }

    /**
     * Wrap input {@code operator} as a throwing one.
     *
     * @param operator the one to be wrapped
     * @return wrapped {@link ThrowingBinaryOperator}
     * @throws NullPointerException if {@code operator} is null
     */
    static <T1> ThrowingBinaryOperator<T1> wrap(BinaryOperator<T1> operator) {
        return Objects.requireNonNull(operator)::apply;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param operator the one to map and return
     * @return the same operator
     */
    static <T1> ThrowingBinaryOperator<T1> map(ThrowingBinaryOperator<T1> operator) {
        return operator;
    }

    /**
     * Returns a {@link ThrowingBinaryOperator} which returns the lesser of two elements
     * according to the specified {@link Comparator}.
     *
     * @param <T>        the type of the input arguments of the comparator
     * @param comparator a {@link Comparator} for comparing the two values
     * @return a {@link ThrowingBinaryOperator} which returns the lesser of its operands,
     * according to the supplied {@link Comparator}
     * @throws NullPointerException if the argument is null
     */
    static <T> ThrowingBinaryOperator<T> minBy(Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator);
        return (a, b) -> comparator.compare(a, b) <= 0 ? a : b;
    }

    /**
     * Returns a {@link ThrowingBinaryOperator} which returns the greater of two elements
     * according to the specified {@link Comparator}.
     *
     * @param <T>        the type of the input arguments of the comparator
     * @param comparator a {@link Comparator} for comparing the two values
     * @return a {@link ThrowingBinaryOperator} which returns the greater of its operands,
     * according to the supplied {@link Comparator}
     * @throws NullPointerException if the argument is null
     */
    static <T> ThrowingBinaryOperator<T> maxBy(Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator);
        return (a, b) -> comparator.compare(a, b) >= 0 ? a : b;
    }


    /**
     * Applies this function to the given arguments,
     * may throw checked {@link Exception}.
     *
     * @param t1 the first function argument
     * @param t2 the second function argument
     * @return the function result
     */
    @Override
    T applyThrows(T t1, T t2) throws Exception;


    @Override
    default T apply(T t1, T t2) {
        try {
            return applyThrows(t1, t2);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingBinaryOperator}.
     *
     * @return this unwrapped {@link BinaryOperator}
     */
    default BinaryOperator<T> unthrow() {
        return this;
    }
}
