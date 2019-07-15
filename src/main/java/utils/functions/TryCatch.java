package utils.functions;

public final class TryCatch {

    /**
     * Will rethrow any caught exception
     *
     * @return the supplier result
     * @see TryCatch#rethrowOnException(utils.functions.ThrowingRunnable)
     */
    public static <T> T rethrowOnException(ThrowingSupplier<? extends T> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Will rethrow any caught exception
     *
     * @see TryCatch#rethrowOnException(utils.functions.ThrowingSupplier)
     */
    public static void rethrowOnException(ThrowingRunnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

}
