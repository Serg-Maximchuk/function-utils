package utils.functions;

import java.util.Objects;
import java.util.function.IntUnaryOperator;

/**
 * {@link IntUnaryOperator} that may sneaky throw checked {@link Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #applyAsIntThrows(int)}.
 *
 * @see ThrowingUnaryOperator
 * @see IntUnaryOperator
 */
@FunctionalInterface
public interface ThrowingIntUnaryOperator extends IntUnaryOperator {

    /**
     * Unwrap {@link ThrowingIntUnaryOperator}.
     *
     * @param operator the one to be unwrapped
     * @return unwrapped {@link IntUnaryOperator}
     */
    static IntUnaryOperator unthrow(ThrowingIntUnaryOperator operator) {
        return operator;
    }

    /**
     * Wrap input {@code operator} as a throwing one.
     *
     * @param operator the one to be wrapped
     * @return wrapped {@link ThrowingIntUnaryOperator}
     * @throws NullPointerException if {@code operator} is null
     */
    static ThrowingIntUnaryOperator wrap(IntUnaryOperator operator) {
        return Objects.requireNonNull(operator)::applyAsInt;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param operator the one to map and return
     * @return the same operator
     */
    static ThrowingIntUnaryOperator map(ThrowingIntUnaryOperator operator) {
        return operator;
    }

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @return a unary operator that always returns its input argument
     */
    static ThrowingIntUnaryOperator identity() {
        return t -> t;
    }


    /**
     * Applies this operator to the given operand,
     * may throw checked {@link Exception}.
     *
     * @param operand the operand
     * @return the operator result
     */
    int applyAsIntThrows(int operand) throws Exception;


    @Override
    default int applyAsInt(int operand) {
        try {
            return applyAsIntThrows(operand);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Returns a composed operator that first applies the {@code before}
     * operator to its input, and then applies this operator to the result.
     * If evaluation of either operator throws an exception, it is relayed to
     * the caller of the composed operator.
     *
     * @param before the operator to apply before this operator is applied
     * @return a composed operator that first applies the {@code before}
     * operator and then applies this operator
     * @throws NullPointerException if before is null
     * @see #andThen(IntUnaryOperator)
     * @see #throwingAndThen(ThrowingIntUnaryOperator)
     * @see #throwingCompose(ThrowingIntUnaryOperator)
     */
    @Override
    default ThrowingIntUnaryOperator compose(IntUnaryOperator before) {
        Objects.requireNonNull(before);
        return (int v) -> applyAsInt(before.applyAsInt(v));
    }

    /**
     * Returns a composed operator that first applies this operator to
     * its input, and then applies the {@code after} operator to the result.
     * If evaluation of either operator throws an exception, it is relayed to
     * the caller of the composed operator.
     *
     * @param after the operator to apply after this operator is applied
     * @return a composed operator that first applies this operator and then
     * applies the {@code after} operator
     * @throws NullPointerException if after is null
     * @see #compose(IntUnaryOperator)
     * @see #throwingAndThen(ThrowingIntUnaryOperator)
     * @see #throwingCompose(ThrowingIntUnaryOperator)
     */
    @Override
    default ThrowingIntUnaryOperator andThen(IntUnaryOperator after) {
        Objects.requireNonNull(after);
        return (int t) -> after.applyAsInt(applyAsInt(t));
    }

    /**
     * Returns a composed operator that first applies the {@code before}
     * operator to its input, and then applies this operator to the result.
     * If evaluation of either operator throws an exception, it is relayed to
     * the caller of the composed operator.
     *
     * @param before the operator to apply before this operator is applied
     * @return a composed operator that first applies the {@code before}
     * operator and then applies this operator
     * @throws NullPointerException if before is null
     * @see #compose(IntUnaryOperator)
     * @see #andThen(IntUnaryOperator)
     * @see #throwingAndThen(ThrowingIntUnaryOperator)
     */
    default ThrowingIntUnaryOperator throwingCompose(ThrowingIntUnaryOperator before) {
        return compose(before);
    }

    /**
     * Returns a composed operator that first applies this operator to
     * its input, and then applies the {@code after} operator to the result.
     * If evaluation of either operator throws an exception, it is relayed to
     * the caller of the composed operator.
     *
     * @param after the operator to apply after this operator is applied
     * @return a composed operator that first applies this operator and then
     * applies the {@code after} operator
     * @throws NullPointerException if after is null
     * @see #compose(IntUnaryOperator)
     * @see #andThen(IntUnaryOperator)
     * @see #throwingCompose(ThrowingIntUnaryOperator)
     */
    default ThrowingIntUnaryOperator throwingAndThen(ThrowingIntUnaryOperator after) {
        return andThen(after);
    }

    /**
     * Unwrap this {@link ThrowingIntUnaryOperator}.
     *
     * @return this unwrapped {@link IntUnaryOperator}
     */
    default IntUnaryOperator unthrow() {
        return this;
    }
}
