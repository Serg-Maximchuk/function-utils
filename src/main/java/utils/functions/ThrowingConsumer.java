package utils.functions;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * {@code Consumer} that may sneaky throw checked {@code Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #acceptThrows(Object)}.
 *
 * @param <T> the type of the input to the operation
 * @see Consumer
 * @see java.util.function.BiConsumer
 * @see ThrowingBiConsumer
 */
@FunctionalInterface
public interface ThrowingConsumer<T> extends Consumer<T> {

    /**
     * Unwrap {@code ThrowingConsumer}.
     *
     * @param consumer the one to be unwrapped
     * @return unwrapped {@code Consumer}
     */
    @SuppressWarnings("unchecked")
    static <T1> Consumer<T1> unthrow(ThrowingConsumer<? super T1> consumer) {
        return (ThrowingConsumer<T1>) consumer;
    }

    /**
     * Wrap input {@code consumer} as a throwing one.
     *
     * @param consumer the one to be wrapped
     * @return wrapped {@code ThrowingConsumer}
     * @throws NullPointerException if {@code consumer} is null
     */
    static <T1> ThrowingConsumer<T1> wrap(Consumer<? super T1> consumer) {
        return Objects.requireNonNull(consumer)::accept;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param consumer the one to map and return
     * @return the same consumer
     */
    @SuppressWarnings("unchecked")
    static <T1> ThrowingConsumer<T1> map(ThrowingConsumer<? super T1> consumer) {
        return (ThrowingConsumer<T1>) consumer;
    }


    /**
     * Performs this operation on the given argument,
     * may throw checked {@code Exception}.
     *
     * @param t the input argument
     */
    void acceptThrows(T t) throws Exception;


    @Override
    default void accept(T t) {
        try {
            acceptThrows(t);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Returns a composed {@code ThrowingConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code ThrowingConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    @Override
    default ThrowingConsumer<T> andThen(Consumer<? super T> after) {
        Objects.requireNonNull(after);

        return (T t) -> {
            accept(t);
            after.accept(t);
        };
    }

    /**
     * Returns a composed {@code ThrowingConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code ThrowingConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default ThrowingConsumer<T> throwingAndThen(ThrowingConsumer<? super T> after) {
        return andThen(after);
    }

    /**
     * Returns a composed {@code ThrowingConsumer} that performs, in sequence, this
     * operation followed by the {@code before} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code before} operation will not be performed.
     *
     * @param before the operation to perform before this operation
     * @return a composed {@code ThrowingConsumer} that performs in sequence this
     * operation followed by the {@code before} operation
     * @throws NullPointerException if {@code before} is null
     */
    @SuppressWarnings("unchecked")
    default ThrowingConsumer<T> compose(Consumer<? super T> before) {
        return wrap((Consumer<T>) before).andThen(this);
    }

    /**
     * Returns a composed {@code ThrowingConsumer} that performs, in sequence, this
     * operation followed by the {@code before} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code before} operation will not be performed.
     *
     * @param before the operation to perform before this operation
     * @return a composed {@code ThrowingConsumer} that performs in sequence this
     * operation followed by the {@code before} operation
     * @throws NullPointerException if {@code before} is null
     */
    @SuppressWarnings("unchecked")
    default ThrowingConsumer<T> throwingCompose(ThrowingConsumer<? super T> before) {
        return Objects.requireNonNull((ThrowingConsumer<T>) before).andThen(this);
    }

    /**
     * Unwrap this {@code ThrowingConsumer}.
     *
     * @return this unwrapped {@code Consumer}
     */
    default Consumer<T> unthrow() {
        return this;
    }

}
