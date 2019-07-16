package utils.functions;

import java.util.Objects;
import java.util.function.IntBinaryOperator;

/**
 * {@link IntBinaryOperator} that may sneaky throw checked {@link Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #applyAsIntThrows(int, int)}.
 *
 * @see IntBinaryOperator
 * @see ThrowingIntUnaryOperator
 */
@FunctionalInterface
public interface ThrowingIntBinaryOperator extends IntBinaryOperator {

    /**
     * Unwrap {@link ThrowingIntBinaryOperator}.
     *
     * @param operator the one to be unwrapped
     * @return unwrapped {@link IntBinaryOperator}
     */
    static IntBinaryOperator unthrow(ThrowingIntBinaryOperator operator) {
        return operator;
    }

    /**
     * Wrap input {@code operator} as a throwing one.
     *
     * @param operator the one to be wrapped
     * @return wrapped {@link ThrowingIntBinaryOperator}
     * @throws NullPointerException if {@code operator} is null
     */
    static ThrowingIntBinaryOperator wrap(IntBinaryOperator operator) {
        return Objects.requireNonNull(operator)::applyAsInt;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param operator the one to map and return
     * @return the same operator
     */
    static ThrowingIntBinaryOperator map(ThrowingIntBinaryOperator operator) {
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
    int applyAsIntThrows(int left, int right) throws Exception;


    @Override
    default int applyAsInt(int left, int right) {
        try {
            return applyAsIntThrows(left, right);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingIntBinaryOperator}.
     *
     * @return this unwrapped {@link IntBinaryOperator}
     */
    default IntBinaryOperator unthrow() {
        return this;
    }
}
