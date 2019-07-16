package utils.functions;

import java.util.Objects;
import java.util.function.LongUnaryOperator;

/**
 * {@link LongUnaryOperator} that may sneaky throw checked {@link Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #applyAsLongThrows(long)}.
 *
 * @see ThrowingUnaryOperator
 * @see LongUnaryOperator
 */
@FunctionalInterface
public interface ThrowingLongUnaryOperator extends LongUnaryOperator {

    /**
     * Unwrap {@link ThrowingLongUnaryOperator}.
     *
     * @param operator the one to be unwrapped
     * @return unwrapped {@link LongUnaryOperator}
     */
    static LongUnaryOperator unthrow(ThrowingLongUnaryOperator operator) {
        return operator;
    }

    /**
     * Wrap input {@code operator} as a throwing one.
     *
     * @param operator the one to be wrapped
     * @return wrapped {@link ThrowingLongUnaryOperator}
     * @throws NullPointerException if {@code operator} is null
     */
    static ThrowingLongUnaryOperator wrap(LongUnaryOperator operator) {
        return Objects.requireNonNull(operator)::applyAsLong;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param operator the one to map and return
     * @return the same operator
     */
    static ThrowingLongUnaryOperator map(ThrowingLongUnaryOperator operator) {
        return operator;
    }

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @return a unary operator that always returns its input argument
     */
    static ThrowingLongUnaryOperator identity() {
        return t -> t;
    }


    /**
     * Applies this operator to the given operand,
     * may throw checked {@link Exception}.
     *
     * @param operand the operand
     * @return the operator result
     */
    long applyAsLongThrows(long operand) throws Exception;


    @Override
    default long applyAsLong(long operand) {
        try {
            return applyAsLongThrows(operand);
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
     * @see #andThen(LongUnaryOperator)
     * @see #throwingAndThen(ThrowingLongUnaryOperator)
     * @see #throwingCompose(ThrowingLongUnaryOperator)
     */
    @Override
    default ThrowingLongUnaryOperator compose(LongUnaryOperator before) {
        Objects.requireNonNull(before);
        return (long v) -> applyAsLong(before.applyAsLong(v));
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
     * @see #compose(LongUnaryOperator)
     * @see #throwingAndThen(ThrowingLongUnaryOperator)
     * @see #throwingCompose(ThrowingLongUnaryOperator)
     */
    @Override
    default ThrowingLongUnaryOperator andThen(LongUnaryOperator after) {
        Objects.requireNonNull(after);
        return (long t) -> after.applyAsLong(applyAsLong(t));
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
     * @see #compose(LongUnaryOperator)
     * @see #andThen(LongUnaryOperator)
     * @see #throwingAndThen(ThrowingLongUnaryOperator)
     */
    default ThrowingLongUnaryOperator throwingCompose(ThrowingLongUnaryOperator before) {
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
     * @see #compose(LongUnaryOperator)
     * @see #andThen(LongUnaryOperator)
     * @see #throwingCompose(ThrowingLongUnaryOperator)
     */
    default ThrowingLongUnaryOperator throwingAndThen(ThrowingLongUnaryOperator after) {
        return andThen(after);
    }

    /**
     * Unwrap this {@link ThrowingLongUnaryOperator}.
     *
     * @return this unwrapped {@link LongUnaryOperator}
     */
    default LongUnaryOperator unthrow() {
        return this;
    }
}
