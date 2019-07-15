package utils.functions;

import java.util.Objects;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;

/**
 * {@link DoubleSupplier} that may sneaky throw checked {@code Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #getAsDoubleThrows()}.
 *
 * @see DoublePredicate
 * @see ThrowingSupplier
 */
@FunctionalInterface
public interface ThrowingDoubleSupplier extends DoubleSupplier {

    /**
     * Unwrap {@link ThrowingDoubleSupplier}.
     *
     * @param supplier the one to be unwrapped
     * @return unwrapped {@link DoubleSupplier}
     */
    static DoubleSupplier unthrow(ThrowingDoubleSupplier supplier) {
        return supplier;
    }

    /**
     * Wrap input {@code supplier} as a throwing one.
     *
     * @param supplier the one to be wrapped
     * @return wrapped {@link ThrowingDoubleSupplier}
     * @throws NullPointerException if {@code supplier} is null
     */
    static ThrowingDoubleSupplier wrap(DoubleSupplier supplier) {
        return Objects.requireNonNull(supplier)::getAsDouble;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param supplier the one to map and return
     * @return the same supplier
     */
    static ThrowingDoubleSupplier map(ThrowingDoubleSupplier supplier) {
        return supplier;
    }


    /**
     * Gets a result, may throw checked {@code Exception}.
     *
     * @return a result
     */
    double getAsDoubleThrows() throws Exception;


    @Override
    default double getAsDouble() {
        try {
            return getAsDoubleThrows();
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingDoubleSupplier}.
     *
     * @return this unwrapped {@link DoubleSupplier}
     */
    default DoubleSupplier unthrow() {
        return this;
    }
}
