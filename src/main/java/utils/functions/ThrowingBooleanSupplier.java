package utils.functions;

import java.util.Objects;
import java.util.function.BooleanSupplier;

/**
 * {@link BooleanSupplier} that may sneaky throw checked {@link Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #getAsBooleanThrowing()}.
 *
 * @see ThrowingSupplier
 * @see BooleanSupplier
 */
@FunctionalInterface
public interface ThrowingBooleanSupplier extends BooleanSupplier {

    /**
     * Unwrap {@link ThrowingBooleanSupplier}.
     *
     * @param supplier the one to be unwrapped
     * @return unwrapped {@link BooleanSupplier}
     */
    static BooleanSupplier unthrow(ThrowingBooleanSupplier supplier) {
        return supplier;
    }

    /**
     * Wrap input {@code operator} as a throwing one.
     *
     * @param operator the one to be wrapped
     * @return wrapped {@link ThrowingBooleanSupplier}
     * @throws NullPointerException if {@code operator} is null
     */
    static ThrowingBooleanSupplier wrap(BooleanSupplier operator) {
        return Objects.requireNonNull(operator)::getAsBoolean;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param operator the one to map and return
     * @return the same operator
     */
    static ThrowingBooleanSupplier map(ThrowingBooleanSupplier operator) {
        return operator;
    }


    /**
     * Gets a result, may throw checked {@link Exception}.
     *
     * @return a result
     */
    boolean getAsBooleanThrowing() throws Exception;


    @Override
    default boolean getAsBoolean() {
        try {
            return getAsBooleanThrowing();
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingBooleanSupplier}.
     *
     * @return this unwrapped {@link BooleanSupplier}
     */
    default BooleanSupplier unthrow() {
        return this;
    }
}
