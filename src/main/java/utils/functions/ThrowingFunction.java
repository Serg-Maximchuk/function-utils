package utils.functions;

import java.util.function.Function;

@FunctionalInterface
public interface ThrowingFunction<T, R> extends Function<T, R> {

    static <T1, R1> Function<T1, R1> unthrow(ThrowingFunction<T1, R1> function) {
        return function;
    }

    static <T1, R1> ThrowingFunction<T1, R1> wrap(Function<T1, R1> wrapMe) {
        return wrapMe::apply;
    }

    /**
     * Use it when you don't want to cast lambda by yourself
     *
     * @param function the one to map and return
     * @return the same function
     */
    static <T1, U1> ThrowingFunction<T1, U1> map(ThrowingFunction<T1, U1> function) {
        return function;
    }


    R applyThrowing(T t) throws Exception;


    @Override
    default R apply(T t) {
        try {
            return applyThrowing(t);
        } catch (Exception e) {
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    default Function<T, R> unthrow() {
        return this;
    }

}
