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


    R applyThrowing(T t) throws Exception;


    @Override
    default R apply(T t) {
        try {
            return applyThrowing(t);
        } catch (Exception e) {
            throw Functions.sneakyThrow(e);
        }
    }

    default Function<T, R> unthrow() {
        return this;
    }
}
