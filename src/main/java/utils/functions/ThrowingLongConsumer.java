package utils.functions;

import java.util.Objects;
import java.util.function.LongConsumer;

/**
 * {@link LongConsumer} that may sneaky throw checked {@link Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #acceptThrows(long)}.
 *
 * @see LongConsumer
 * @see ThrowingConsumer
 */
@FunctionalInterface
public interface ThrowingLongConsumer extends LongConsumer {

    /**
     * Unwrap {@link ThrowingLongConsumer}.
     *
     * @param consumer the one to be unwrapped
     * @return unwrapped {@link LongConsumer}
     */
    static LongConsumer unthrow(ThrowingLongConsumer consumer) {
        return consumer;
    }

    /**
     * Wrap input {@code consumer} as a throwing one.
     *
     * @param consumer the one to be wrapped
     * @return wrapped {@link ThrowingLongConsumer}
     * @throws NullPointerException if {@code consumer} is null
     */
    static ThrowingLongConsumer wrap(LongConsumer consumer) {
        return Objects.requireNonNull(consumer)::accept;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param consumer the one to map and return
     * @return the same consumer
     */
    static ThrowingLongConsumer map(ThrowingLongConsumer consumer) {
        return consumer;
    }


    /**
     * Performs this operation on the given argument,
     * may throw checked {@link Exception}.
     *
     * @param value the input argument
     */
    void acceptThrows(long value) throws Exception;


    @Override
    default void accept(long value) {
        try {
            acceptThrows(value);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Returns a composed {@link LongConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@link LongConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    @Override
    default ThrowingLongConsumer andThen(LongConsumer after) {
        Objects.requireNonNull(after);
        return (long t) -> {
            accept(t);
            after.accept(t);
        };
    }

    /**
     * Returns a composed {@link ThrowingLongConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@link ThrowingLongConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default ThrowingLongConsumer throwingAndThen(ThrowingLongConsumer after) {
        return andThen(after);
    }

    /**
     * Unwrap this {@link ThrowingLongConsumer}.
     *
     * @return this unwrapped {@link LongConsumer}
     */
    default LongConsumer unthrow() {
        return this;
    }
}
