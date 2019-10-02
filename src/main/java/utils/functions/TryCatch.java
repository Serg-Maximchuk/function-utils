package utils.functions;

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
     * {@code fallbackSupplier} as a fallback
     * {@link ThrowingSupplier}. It is relayed to caller to
     * deal with {@link Exception} in fallback
     * {@link ThrowingSupplier}
     *
     * @return the {@code supplier} or {@code fallbackSupplier} result
     * @see #tryCatch(ThrowingRunnable, ThrowingConsumer)
     * @see #tryCatch(ThrowingSupplier, ThrowingFunction)
     */
    public static <T> T tryCatch(
            ThrowingSupplier<? extends T> supplier,
            ThrowingSupplier<? extends T> fallbackSupplier
    ) {
        try {
            return supplier.get();
        } catch (Exception e) {
            return fallbackSupplier.get();
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
     * @see #tryCatch(ThrowingSupplier, ThrowingSupplier)
     */
    public static <T> T tryCatch(
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
     * @see #tryCatch(ThrowingSupplier, ThrowingFunction)
     * @see #tryCatch(ThrowingSupplier, ThrowingSupplier)
     */
    public static void tryCatch(ThrowingRunnable runnable, ThrowingConsumer<Exception> onException) {
        try {
            runnable.run();
        } catch (Exception e) {
            onException.accept(e);
        }
    }

}
