package utils.functions;

import java.util.Objects;
import java.util.function.ObjDoubleConsumer;

/**
 * {@link ObjDoubleConsumer} that may sneaky throw checked {@link Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #acceptThrows(Object, double)}.
 *
 * @param <T> the type of the object argument to the operation
 * @see ObjDoubleConsumer
 * @see java.util.function.BiConsumer
 * @see ThrowingBiConsumer
 */
@FunctionalInterface
public interface ThrowingObjDoubleConsumer<T> extends ObjDoubleConsumer<T> {

    /**
     * Unwrap {@link ThrowingObjDoubleConsumer}.
     *
     * @param consumer the one to be unwrapped
     * @return unwrapped {@link ObjDoubleConsumer}
     */
    @SuppressWarnings("unchecked")
    static <T1> ObjDoubleConsumer<T1> unthrow(ThrowingObjDoubleConsumer<? super T1> consumer) {
        return (ObjDoubleConsumer<T1>) consumer;
    }

    /**
     * Wrap input {@code consumer} as a throwing one.
     *
     * @param consumer the one to be wrapped
     * @return wrapped {@link ThrowingObjDoubleConsumer}
     * @throws NullPointerException if {@code consumer} is null
     */
    static <T1> ThrowingObjDoubleConsumer<T1> wrap(ObjDoubleConsumer<? super T1> consumer) {
        return Objects.requireNonNull(consumer)::accept;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param consumer the one to map and return
     * @return the same consumer
     */
    @SuppressWarnings("unchecked")
    static <T1> ThrowingObjDoubleConsumer<T1> map(ThrowingObjDoubleConsumer<? super T1> consumer) {
        return (ThrowingObjDoubleConsumer<T1>) consumer;
    }


    /**
     * Performs this operation on the given arguments,
     * may throw checked {@link Exception}.
     *
     * @param t     the first input argument
     * @param value the second input argument
     */
    void acceptThrows(T t, double value) throws Exception;


    @Override
    default void accept(T t, double value) {
        try {
            acceptThrows(t, value);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingObjDoubleConsumer}.
     *
     * @return this unwrapped {@link ObjDoubleConsumer}
     */
    default ObjDoubleConsumer<T> unthrow() {
        return this;
    }
}
