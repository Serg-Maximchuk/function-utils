package utils.functions;

import java.util.Objects;
import java.util.function.ObjIntConsumer;

/**
 * {@link ObjIntConsumer} that may sneaky throw checked {@link Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #acceptThrows(Object, int)}.
 *
 * @param <T> the type of the object argument to the operation
 * @see ObjIntConsumer
 * @see java.util.function.BiConsumer
 * @see ThrowingBiConsumer
 */
@FunctionalInterface
public interface ThrowingObjIntConsumer<T> extends ObjIntConsumer<T> {

    /**
     * Unwrap {@link ThrowingObjDoubleConsumer}.
     *
     * @param consumer the one to be unwrapped
     * @return unwrapped {@link ObjIntConsumer}
     */
    @SuppressWarnings("unchecked")
    static <T1> ObjIntConsumer<T1> unthrow(ThrowingObjIntConsumer<? super T1> consumer) {
        return (ObjIntConsumer<T1>) consumer;
    }

    /**
     * Wrap input {@code consumer} as a throwing one.
     *
     * @param consumer the one to be wrapped
     * @return wrapped {@link ThrowingObjIntConsumer}
     * @throws NullPointerException if {@code consumer} is null
     */
    static <T1> ThrowingObjIntConsumer<T1> wrap(ObjIntConsumer<? super T1> consumer) {
        return Objects.requireNonNull(consumer)::accept;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param consumer the one to map and return
     * @return the same consumer
     */
    @SuppressWarnings("unchecked")
    static <T1> ThrowingObjIntConsumer<T1> map(ThrowingObjIntConsumer<? super T1> consumer) {
        return (ThrowingObjIntConsumer<T1>) consumer;
    }


    /**
     * Performs this operation on the given arguments,
     * may throw checked {@link Exception}.
     *
     * @param t     the first input argument
     * @param value the second input argument
     */
    void acceptThrows(T t, int value) throws Exception;


    @Override
    default void accept(T t, int value) {
        try {
            acceptThrows(t, value);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingObjIntConsumer}.
     *
     * @return this unwrapped {@link ObjIntConsumer}
     */
    default ObjIntConsumer<T> unthrow() {
        return this;
    }
}
