package utils.functions;

import java.util.Objects;
import java.util.function.LongBinaryOperator;

/**
 * {@link LongBinaryOperator} that may sneaky throw checked {@link Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #applyAsLongThrows(long, long)}.
 *
 * @see LongBinaryOperator
 * @see utils.functions.ThrowingBinaryOperator
 * @see ThrowingLongUnaryOperator
 */
@FunctionalInterface
public interface ThrowingLongBinaryOperator extends LongBinaryOperator {

    /**
     * Unwrap {@link ThrowingLongBinaryOperator}.
     *
     * @param operator the one to be unwrapped
     * @return unwrapped {@link LongBinaryOperator}
     */
    static LongBinaryOperator unthrow(ThrowingLongBinaryOperator operator) {
        return operator;
    }

    /**
     * Wrap input {@code operator} as a throwing one.
     *
     * @param operator the one to be wrapped
     * @return wrapped {@link ThrowingLongBinaryOperator}
     * @throws NullPointerException if {@code operator} is null
     */
    static ThrowingLongBinaryOperator wrap(LongBinaryOperator operator) {
        return Objects.requireNonNull(operator)::applyAsLong;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param operator the one to map and return
     * @return the same operator
     */
    static ThrowingLongBinaryOperator map(ThrowingLongBinaryOperator operator) {
        return operator;
    }


    /**
     * Applies this operator to the given operands,
     * may throw checked {@link Exception}.
     *
     * @param left  the first operand
     * @param right the second operand
     * @return the operator result
     */
    long applyAsLongThrows(long left, long right) throws Exception;


    @Override
    default long applyAsLong(long left, long right) {
        try {
            return applyAsLongThrows(left, right);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingLongBinaryOperator}.
     *
     * @return this unwrapped {@link LongBinaryOperator}
     */
    default LongBinaryOperator unthrow() {
        return this;
    }
}
