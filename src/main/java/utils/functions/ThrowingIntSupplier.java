package utils.functions;

import java.util.Objects;
import java.util.function.IntSupplier;

/**
 * {@link IntSupplier} that may sneaky throw checked {@link Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #getAsIntThrows()}.
 *
 * @see IntSupplier
 * @see ThrowingSupplier
 */
@FunctionalInterface
public interface ThrowingIntSupplier extends IntSupplier {

    /**
     * Unwrap {@link ThrowingIntSupplier}.
     *
     * @param supplier the one to be unwrapped
     * @return unwrapped {@link IntSupplier}
     */
    static IntSupplier unthrow(ThrowingIntSupplier supplier) {
        return supplier;
    }

    /**
     * Wrap input {@code supplier} as a throwing one.
     *
     * @param supplier the one to be wrapped
     * @return wrapped {@link ThrowingIntSupplier}
     * @throws NullPointerException if {@code supplier} is null
     */
    static ThrowingIntSupplier wrap(IntSupplier supplier) {
        return Objects.requireNonNull(supplier)::getAsInt;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param supplier the one to map and return
     * @return the same supplier
     */
    static ThrowingIntSupplier map(ThrowingIntSupplier supplier) {
        return supplier;
    }


    /**
     * Gets a result, may throw checked {@link Exception}.
     *
     * @return a result
     */
    int getAsIntThrows() throws Exception;


    @Override
    default int getAsInt() {
        try {
            return getAsIntThrows();
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingIntSupplier}.
     *
     * @return this unwrapped {@link IntSupplier}
     */
    default IntSupplier unthrow() {
        return this;
    }
}
