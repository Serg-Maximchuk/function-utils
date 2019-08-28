package utils.functions;

public final class TryCatch {

    /**
     * Will rethrow any caught exception
     *
     * @return the supplier result
     * @see TryCatch#rethrowOnException(ThrowingRunnable)
     * @see TryCatch#rethrowOnException(ThrowingSupplier, ThrowingConsumer)
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
     * @see TryCatch#rethrowOnException(ThrowingSupplier)
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
     * @see TryCatch#rethrowOnException(ThrowingSupplier)
     */
    public static void rethrowOnException(ThrowingRunnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Just functional replacement for common void try/catch block
     */
    public static void tryCatch(ThrowingRunnable runnable, ThrowingConsumer<Exception> onException) {
        try {
            runnable.run();
        } catch (Exception e) {
            onException.accept(e);
        }
    }

}
