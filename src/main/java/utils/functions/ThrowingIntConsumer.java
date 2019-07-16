package utils.functions;

import java.util.Objects;
import java.util.function.IntConsumer;

/**
 * {@link IntConsumer} that may sneaky throw checked {@link Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #acceptThrows(int)}.
 *
 * @see java.util.function.Consumer
 * @see ThrowingConsumer
 */
@FunctionalInterface
public interface ThrowingIntConsumer extends IntConsumer {

    /**
     * Unwrap {@link ThrowingIntConsumer}.
     *
     * @param consumer the one to be unwrapped
     * @return unwrapped {@link IntConsumer}
     */
    static IntConsumer unthrow(ThrowingIntConsumer consumer) {
        return consumer;
    }

    /**
     * Wrap input {@code consumer} as a throwing one.
     *
     * @param consumer the one to be wrapped
     * @return wrapped {@link ThrowingIntConsumer}
     * @throws NullPointerException if {@code consumer} is null
     */
    static ThrowingIntConsumer wrap(IntConsumer consumer) {
        return Objects.requireNonNull(consumer)::accept;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param consumer the one to map and return
     * @return the same consumer
     */
    static ThrowingIntConsumer map(ThrowingIntConsumer consumer) {
        return consumer;
    }


    /**
     * Performs this operation on the given argument,
     * may throw checked {@link Exception}.
     *
     * @param value the input argument
     */
    void acceptThrows(int value) throws Exception;


    @Override
    default void accept(int value) {
        try {
            acceptThrows(value);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Returns a composed {@link IntConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@link IntConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    @Override
    default ThrowingIntConsumer andThen(IntConsumer after) {
        Objects.requireNonNull(after);
        return (int t) -> {
            accept(t);
            after.accept(t);
        };
    }

    /**
     * Returns a composed {@link ThrowingIntConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@link ThrowingIntConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default ThrowingIntConsumer throwingAndThen(ThrowingIntConsumer after) {
        return andThen(after);
    }

    /**
     * Unwrap this {@link ThrowingIntConsumer}.
     *
     * @return this unwrapped {@link IntConsumer}
     */
    default IntConsumer unthrow() {
        return this;
    }
}
