package utils.functions;

import java.util.Objects;
import java.util.function.ObjLongConsumer;

/**
 * {@link ObjLongConsumer} that may sneaky throw checked {@code Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #acceptThrows(Object, long)}.
 *
 * @param <T> the type of the object argument to the operation
 * @see ObjLongConsumer
 * @see java.util.function.BiConsumer
 * @see ThrowingBiConsumer
 */
@FunctionalInterface
public interface ThrowingObjLongConsumer<T> extends ObjLongConsumer<T> {

    /**
     * Unwrap {@link ThrowingObjLongConsumer}.
     *
     * @param consumer the one to be unwrapped
     * @return unwrapped {@link ObjLongConsumer}
     */
    @SuppressWarnings("unchecked")
    static <T1> ObjLongConsumer<T1> unthrow(ThrowingObjLongConsumer<? super T1> consumer) {
        return (ObjLongConsumer<T1>) consumer;
    }

    /**
     * Wrap input {@code consumer} as a throwing one.
     *
     * @param consumer the one to be wrapped
     * @return wrapped {@link ThrowingObjLongConsumer}
     * @throws NullPointerException if {@code consumer} is null
     */
    static <T1> ThrowingObjLongConsumer<T1> wrap(ObjLongConsumer<? super T1> consumer) {
        return Objects.requireNonNull(consumer)::accept;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param consumer the one to map and return
     * @return the same consumer
     */
    @SuppressWarnings("unchecked")
    static <T1> ThrowingObjLongConsumer<T1> map(ThrowingObjLongConsumer<? super T1> consumer) {
        return (ThrowingObjLongConsumer<T1>) consumer;
    }


    /**
     * Performs this operation on the given arguments,
     * may throw checked {@code Exception}.
     *
     * @param t     the first input argument
     * @param value the second input argument
     */
    void acceptThrows(T t, long value) throws Exception;


    @Override
    default void accept(T t, long value) {
        try {
            acceptThrows(t, value);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingObjLongConsumer}.
     *
     * @return this unwrapped {@link ObjLongConsumer}
     */
    default ObjLongConsumer<T> unthrow() {
        return this;
    }
}
