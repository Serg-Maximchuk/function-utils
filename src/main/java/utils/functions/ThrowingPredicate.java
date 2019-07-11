package utils.functions;

import java.util.function.Predicate;

@FunctionalInterface
public interface ThrowingPredicate<T> extends Predicate<T> {

    @Override
    default boolean test(T t) {
        try {
            return testThrowing(t);
        } catch (Exception e) {
            throw Functions.sneakyThrow(e);
        }
    }

    boolean testThrowing(T t) throws Exception;
}
