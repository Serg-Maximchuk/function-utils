package utils.functions;

import java.util.Objects;
import java.util.function.DoubleConsumer;

/**
 * {@code DoubleConsumer} that may sneaky throw checked {@code Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #acceptThrows(double)}.
 *
 * @see java.util.function.Consumer
 * @see ThrowingConsumer
 */
@FunctionalInterface
public interface ThrowingDoubleConsumer extends DoubleConsumer {

    /**
     * Unwrap {@code ThrowingDoubleConsumer}.
     *
     * @param consumer the one to be unwrapped
     * @return unwrapped {@code DoubleConsumer}
     */
    static DoubleConsumer unthrow(ThrowingDoubleConsumer consumer) {
        return consumer;
    }

    /**
     * Wrap input {@code consumer} as a throwing one.
     *
     * @param consumer the one to be wrapped
     * @return wrapped {@code ThrowingDoubleConsumer}
     * @throws NullPointerException if {@code consumer} is null
     */
    static ThrowingDoubleConsumer wrap(DoubleConsumer consumer) {
        return Objects.requireNonNull(consumer)::accept;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param consumer the one to map and return
     * @return the same consumer
     */
    static ThrowingDoubleConsumer map(ThrowingDoubleConsumer consumer) {
        return consumer;
    }


    /**
     * Performs this operation on the given argument.
     *
     * @param value the input argument
     */
    void acceptThrows(double value) throws Exception;


    @Override
    default void accept(double value) {
        try {
            acceptThrows(value);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Returns a composed {@code DoubleConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code DoubleConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    @Override
    default ThrowingDoubleConsumer andThen(DoubleConsumer after) {
        Objects.requireNonNull(after);
        return (double t) -> {
            accept(t);
            after.accept(t);
        };
    }

    /**
     * Returns a composed {@code ThrowingDoubleConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code ThrowingDoubleConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default ThrowingDoubleConsumer throwingAndThen(ThrowingDoubleConsumer after) {
        return andThen(after);
    }

    /**
     * Unwrap this {@code ThrowingDoubleConsumer}.
     *
     * @return this unwrapped {@code DoubleConsumer}
     */
    default DoubleConsumer unthrow() {
        return this;
    }
}
