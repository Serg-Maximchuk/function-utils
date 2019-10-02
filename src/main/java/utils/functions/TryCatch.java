package utils.functions;

@SuppressWarnings("WeakerAccess")
public final class TryCatch {

    /**
     * Will rethrow any caught exception
     *
     * @return the supplier result
     * @see #rethrowOnException(ThrowingRunnable)
     * @see #rethrowOnException(ThrowingSupplier, ThrowingConsumer)
     */
    public static <T> T rethrowOnException(ThrowingSupplier<? extends T> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Will rethrow any caught exception with supplying it before rethrowing
     *
     * @return the supplier result
     * @see #rethrowOnException(ThrowingSupplier)
     */
    public static <T> T rethrowOnException(
            ThrowingSupplier<? extends T> supplier,
            ThrowingConsumer<Exception> onException
    ) {
        try {
            return supplier.get();
        } catch (Exception e) {
            onException.accept(e);
            //noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Will rethrow any caught exception
     *
     * @see #rethrowOnException(ThrowingSupplier)
     */
    public static void rethrowOnException(ThrowingRunnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Will try to return value from {@code supplier} with
     * {@code fallback} as a {@code exception -> value}
     * {@link ThrowingFunction}. It is relayed to caller to
     * deal with {@link Exception} in fallback
     * {@link ThrowingFunction}
     *
     * @return the {@code supplier} or {@code fallback} result
     * @see #tryCatch(ThrowingRunnable, ThrowingConsumer)
     */
    public static <T> T tryCatchFallback(
            ThrowingSupplier<? extends T> supplier,
            ThrowingFunction<Exception, ? extends T> fallback
    ) {
        try {
            return supplier.get();
        } catch (Exception e) {
            return fallback.apply(e);
        }
    }

    /**
     * Just functional replacement for common void try/catch block
     * @see #tryCatchFallback(ThrowingSupplier, ThrowingFunction)
     */
    public static void tryCatch(ThrowingRunnable runnable, ThrowingConsumer<Exception> onException) {
        try {
            runnable.run();
        } catch (Exception e) {
            onException.accept(e);
        }
    }

}
