package utils.functions;

import java.util.function.Predicate;

@FunctionalInterface
public interface ThrowingPredicate<T> extends Predicate<T> {

    static <T1> Predicate<T1> unthrow(ThrowingPredicate<T1> predicate) {
        return predicate;
    }

    static <T1> ThrowingPredicate<T1> wrap(Predicate<T1> predicate) {
        return predicate::test;
    }

    /**
     * Use it when you don't want to cast lambda by yourself
     *
     * @param predicate the one to map and return
     * @return the same predicate
     */
    static <T1> ThrowingPredicate<T1> map(ThrowingPredicate<T1> predicate) {
        return predicate;
    }


    boolean testThrowing(T t) throws Exception;


    @Override
    default boolean test(T t) {
        try {
            return testThrowing(t);
        } catch (Exception e) {
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    default Predicate<T> unthrow() {
        return this;
    }

}
