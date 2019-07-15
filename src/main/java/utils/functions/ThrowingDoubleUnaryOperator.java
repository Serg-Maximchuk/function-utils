package utils.functions;

import java.util.Objects;
import java.util.function.DoubleUnaryOperator;

/**
 * {@link DoubleUnaryOperator} that may sneaky throw checked exception.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #applyAsDoubleThrows(double)}.
 *
 * @see ThrowingUnaryOperator
 * @see DoubleUnaryOperator
 */
@FunctionalInterface
public interface ThrowingDoubleUnaryOperator extends DoubleUnaryOperator {

    /**
     * Unwrap {@code ThrowingDoubleUnaryOperator}.
     *
     * @param operator the one to be unwrapped
     * @return unwrapped {@code DoubleUnaryOperator}
     */
    static DoubleUnaryOperator unthrow(ThrowingDoubleUnaryOperator operator) {
        return operator;
    }

    /**
     * Wrap input {@code operator} as a throwing one.
     *
     * @param operator the one to be wrapped
     * @return wrapped {@code ThrowingDoubleUnaryOperator}
     * @throws NullPointerException if {@code operator} is null
     */
    static ThrowingDoubleUnaryOperator wrap(DoubleUnaryOperator operator) {
        return Objects.requireNonNull(operator)::applyAsDouble;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param operator the one to map and return
     * @return the same operator
     */
    static ThrowingDoubleUnaryOperator map(ThrowingDoubleUnaryOperator operator) {
        return operator;
    }

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @return a unary operator that always returns its input argument
     */
    static ThrowingDoubleUnaryOperator identity() {
        return t -> t;
    }


    /**
     * Applies this operator to the given operand.
     *
     * @param operand the operand
     * @return the operator result
     */
    double applyAsDoubleThrows(double operand) throws Exception;


    @Override
    default double applyAsDouble(double operand) {
        try {
            return applyAsDoubleThrows(operand);
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
     * @see #andThen(DoubleUnaryOperator)
     * @see #throwingAndThen(ThrowingDoubleUnaryOperator)
     * @see #throwingCompose(ThrowingDoubleUnaryOperator)
     */
    @Override
    default ThrowingDoubleUnaryOperator compose(DoubleUnaryOperator before) {
        Objects.requireNonNull(before);
        return (double v) -> applyAsDouble(before.applyAsDouble(v));
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
     * @see #compose(DoubleUnaryOperator)
     * @see #throwingAndThen(ThrowingDoubleUnaryOperator)
     * @see #throwingCompose(ThrowingDoubleUnaryOperator)
     */
    @Override
    default ThrowingDoubleUnaryOperator andThen(DoubleUnaryOperator after) {
        Objects.requireNonNull(after);
        return (double t) -> after.applyAsDouble(applyAsDouble(t));
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
     * @see #compose(DoubleUnaryOperator)
     * @see #andThen(DoubleUnaryOperator)
     * @see #throwingAndThen(ThrowingDoubleUnaryOperator)
     */
    default ThrowingDoubleUnaryOperator throwingCompose(ThrowingDoubleUnaryOperator before) {
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
     * @see #compose(DoubleUnaryOperator)
     * @see #andThen(DoubleUnaryOperator)
     * @see #throwingCompose(ThrowingDoubleUnaryOperator)
     */
    default ThrowingDoubleUnaryOperator throwingAndThen(ThrowingDoubleUnaryOperator after) {
        return andThen(after);
    }

    /**
     * Unwrap this {@code ThrowingDoubleUnaryOperator}.
     *
     * @return this unwrapped {@code DoubleUnaryOperator}
     */
    default DoubleUnaryOperator unthrow() {
        return this;
    }
}
