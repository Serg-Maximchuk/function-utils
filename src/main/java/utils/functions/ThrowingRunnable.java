package utils.functions;

import java.util.Objects;

/**
 * {@code Runnable} that may throw checked {@code Exception}.
 *
 * <p>This is a functional interface whose functional method
 * is {@link #runThrowing()}.
 *
 * @see Runnable
 */
@FunctionalInterface
public interface ThrowingRunnable extends Runnable {

    /**
     * Unwrap {@code ThrowingRunnable}.
     *
     * @param runnable the one to be unwrapped
     * @return unwrapped {@code Runnable}
     */
    static Runnable unthrow(ThrowingRunnable runnable) {
        return runnable;
    }

    /**
     * Wrap input {@code runnable} as a throwing one.
     *
     * @param runnable the one to be wrapped
     * @return wrapped {@code ThrowingRunnable}
     * @throws NullPointerException if {@code runnable} is null
     */
    static ThrowingRunnable wrap(Runnable runnable) {
        return Objects.requireNonNull(runnable)::run;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param runnable runnable to map and return
     * @return the same runnable
     */
    static ThrowingRunnable map(ThrowingRunnable runnable) {
        return runnable;
    }


    /**
     * The main action, may throw checked {@code Exception}
     */
    void runThrowing() throws Exception;


    @Override
    default void run() {
        try {
            runThrowing();
        } catch (Exception e) {
            Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@code ThrowingRunnable}.
     *
     * @return this unwrapped {@code Runnable}
     */
    default Runnable unthrow() {
        return this;
    }

}
