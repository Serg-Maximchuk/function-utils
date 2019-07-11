package utils.functions;

public final class SneakyThrow {

    @SuppressWarnings("unchecked")
    public static <T extends Throwable> T sneakyThrow(Throwable t) throws T {
        throw (T) t;
    }

}
