package utils.functions;

import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

/**
 * {@link DoubleBinaryOperator} that may sneaky throw checked exception.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #applyAsDoubleThrows(double, double)}.
 *
 * @see DoubleBinaryOperator
 * @see ThrowingDoubleUnaryOperator
 */
@FunctionalInterface
public interface ThrowingDoubleBinaryOperator extends DoubleBinaryOperator {

    /**
     * Unwrap {@code ThrowingDoubleBinaryOperator}.
     *
     * @param operator the one to be unwrapped
     * @return unwrapped {@code DoubleBinaryOperator}
     */
    static DoubleBinaryOperator unthrow(ThrowingDoubleBinaryOperator operator) {
        return operator;
    }

    /**
     * Wrap input {@code operator} as a throwing one.
     *
     * @param operator the one to be wrapped
     * @return wrapped {@code ThrowingDoubleBinaryOperator}
     * @throws NullPointerException if {@code operator} is null
     */
    static ThrowingDoubleBinaryOperator wrap(DoubleBinaryOperator operator) {
        return Objects.requireNonNull(operator)::applyAsDouble;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param operator the one to map and return
     * @return the same operator
     */
    static ThrowingDoubleBinaryOperator map(ThrowingDoubleBinaryOperator operator) {
        return operator;
    }


    /**
     * Applies this operator to the given operands.
     *
     * @param left  the first operand
     * @param right the second operand
     * @return the operator result
     */
    double applyAsDoubleThrows(double left, double right) throws Exception;


    @Override
    default double applyAsDouble(double left, double right) {
        try {
            return applyAsDoubleThrows(left, right);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@code ThrowingDoubleBinaryOperator}.
     *
     * @return this unwrapped {@code DoubleBinaryOperator}
     */
    default DoubleBinaryOperator unthrow() {
        return this;
    }
}
