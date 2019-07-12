package utils.functions;

@FunctionalInterface
public interface ThrowingRunnable extends Runnable {

    static Runnable unthrow(ThrowingRunnable runnable) {
        return runnable;
    }

    static ThrowingRunnable wrap(Runnable runnable) {
        return runnable::run;
    }

    /**
     * Use it when you don't want to cast lambda by yourself
     *
     * @param runnable runnable to map and return
     * @return the same runnable
     */
    static ThrowingRunnable map(ThrowingRunnable runnable) {
        return runnable;
    }


    void runThrowing() throws Exception;


    @Override
    default void run() {
        try {
            runThrowing();
        } catch (Exception e) {
            Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    default Runnable unthrow() {
        return this;
    }

}
