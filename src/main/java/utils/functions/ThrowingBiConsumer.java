package utils.functions;

import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * {@code BiConsumer} that may sneaky throw checked {@code Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #acceptThrows(Object, Object)}.
 *
 * @param <T> the type of the first argument to the operation
 * @param <U> the type of the second argument to the operation
 * @see BiConsumer
 * @see ThrowingConsumer
 * @see java.util.function.Consumer
 */
@FunctionalInterface
public interface ThrowingBiConsumer<T, U> extends BiConsumer<T, U> {

    /**
     * Unwrap {@code ThrowingBiConsumer}.
     *
     * @param consumer the one to be unwrapped
     * @return unwrapped {@code BiConsumer}
     */
    @SuppressWarnings("unchecked")
    static <T1, U1> BiConsumer<T1, U1> unthrow(ThrowingBiConsumer<? super T1, ? super U1> consumer) {
        return (BiConsumer<T1, U1>) consumer;
    }

    /**
     * Wrap input {@code consumer} as a throwing one.
     *
     * @param consumer the one to be wrapped
     * @return wrapped {@code ThrowingBiConsumer}
     * @throws NullPointerException if {@code consumer} is null
     */
    static <T1, U1> ThrowingBiConsumer<T1, U1> wrap(BiConsumer<? super T1, ? super U1> consumer) {
        return Objects.requireNonNull(consumer)::accept;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param consumer the one to map and return
     * @return the same consumer
     */
    @SuppressWarnings("unchecked")
    static <T1, U1> ThrowingBiConsumer<T1, U1> map(ThrowingBiConsumer<? super T1, ? super U1> consumer) {
        return (ThrowingBiConsumer<T1, U1>) consumer;
    }


    /**
     * Performs this operation on the given arguments,
     * may throw checked {@code Exception}.
     *
     * @param t the first input argument
     * @param u the second input argument
     */
    void acceptThrows(T t, U u) throws Exception;


    @Override
    default void accept(T t, U u) {
        try {
            acceptThrows(t, u);
        } catch (Exception e) {
            Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Returns a composed {@code ThrowingBiConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code ThrowingBiConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default ThrowingBiConsumer<T, U> andThenThrowing(ThrowingBiConsumer<? super T, ? super U> after) {
        return andThen(after);
    }

    /**
     * Returns a composed {@code ThrowingBiConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code ThrowingBiConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    @Override
    default ThrowingBiConsumer<T, U> andThen(BiConsumer<? super T, ? super U> after) {
        Objects.requireNonNull(after);

        return (t, u) -> {
            this.accept(t, u);
            after.accept(t, u);
        };
    }

    /**
     * Returns a composed {@code ThrowingBiConsumer} that performs, in sequence,
     * the {@code after} operation followed by this operation. If performing
     * either operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing the {@code after} operation throws
     * an exception, this operation will not be performed.
     *
     * @param before the operation to perform before this operation
     * @return a composed {@code ThrowingBiConsumer} that performs in sequence the
     * {@code before} operation followed by this operation
     * @throws NullPointerException if {@code before} is null
     */
    @SuppressWarnings("unchecked")
    default ThrowingBiConsumer<T, U> composeThrowing(ThrowingBiConsumer<? super T, ? super U> before) {
        return Objects.requireNonNull((ThrowingBiConsumer<T, U>) before).andThen(this);
    }

    /**
     * Returns a composed {@code ThrowingBiConsumer} that performs, in sequence,
     * the {@code after} operation followed by this operation. If performing
     * either operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing the {@code after} operation throws
     * an exception, this operation will not be performed.
     *
     * @param before the operation to perform before this operation
     * @return a composed {@code ThrowingBiConsumer} that performs in sequence the
     * {@code before} operation followed by this operation
     * @throws NullPointerException if {@code before} is null
     */
    @SuppressWarnings("unchecked")
    default ThrowingBiConsumer<T, U> compose(BiConsumer<? super T, ? super U> before) {
        return wrap((ThrowingBiConsumer<T, U>) before).andThen(this);
    }

    /**
     * Unwrap this {@code ThrowingBiConsumer}.
     *
     * @return this unwrapped {@code BiConsumer}
     */
    default BiConsumer<T, U> unthrow() {
        return this;
    }

}
