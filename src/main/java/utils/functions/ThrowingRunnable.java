package utils.functions;

@FunctionalInterface
public interface ThrowingRunnable extends Runnable {

    static Runnable unthrow(ThrowingRunnable runnable) {
        return runnable;
    }

    static ThrowingRunnable wrap(Runnable runnable) {
        return runnable::run;
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
