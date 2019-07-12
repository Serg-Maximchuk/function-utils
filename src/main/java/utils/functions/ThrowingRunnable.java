package utils.functions;

@FunctionalInterface
public interface ThrowingRunnable extends Runnable {

    void runThrowing() throws Exception;

    @Override
    default void run() {
        try {
            runThrowing();
        } catch (Exception e) {
            Functions.<RuntimeException>sneakyThrow(e);
        }
    }
}
