package utils.functions;

import java.util.Objects;
import java.util.function.LongSupplier;

/**
 * {@link LongSupplier} that may sneaky throw checked {@link Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #getAsLongThrows()}.
 *
 * @see LongSupplier
 * @see ThrowingSupplier
 */
@FunctionalInterface
public interface ThrowingLongSupplier extends LongSupplier {

    /**
     * Unwrap {@link ThrowingLongSupplier}.
     *
     * @param supplier the one to be unwrapped
     * @return unwrapped {@link LongSupplier}
     */
    static LongSupplier unthrow(ThrowingLongSupplier supplier) {
        return supplier;
    }

    /**
     * Wrap input {@code supplier} as a throwing one.
     *
     * @param supplier the one to be wrapped
     * @return wrapped {@link ThrowingLongSupplier}
     * @throws NullPointerException if {@code supplier} is null
     */
    static ThrowingLongSupplier wrap(LongSupplier supplier) {
        return Objects.requireNonNull(supplier)::getAsLong;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param supplier the one to map and return
     * @return the same supplier
     */
    static ThrowingLongSupplier map(ThrowingLongSupplier supplier) {
        return supplier;
    }


    /**
     * Gets a result, may throw checked {@link Exception}.
     *
     * @return a result
     */
    long getAsLongThrows() throws Exception;


    @Override
    default long getAsLong() {
        try {
            return getAsLongThrows();
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingLongSupplier}.
     *
     * @return this unwrapped {@link LongSupplier}
     */
    default LongSupplier unthrow() {
        return this;
    }
}
