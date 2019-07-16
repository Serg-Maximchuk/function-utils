package utils.functions;

import java.util.Objects;
import java.util.function.IntPredicate;

/**
 * {@link IntPredicate} that may sneaky throw checked {@link Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #testThrows(int)}.
 *
 * @see IntPredicate
 * @see ThrowingPredicate
 */
@FunctionalInterface
public interface ThrowingIntPredicate extends IntPredicate {

    /**
     * Unwrap {@link ThrowingIntPredicate}.
     *
     * @param predicate the one to be unwrapped
     * @return unwrapped {@link IntPredicate}
     */
    static IntPredicate unthrow(ThrowingIntPredicate predicate) {
        return predicate;
    }

    /**
     * Wrap input {@code predicate} as a throwing one.
     *
     * @param predicate the one to be wrapped
     * @return wrapped {@link ThrowingIntPredicate}
     * @throws NullPointerException if {@code predicate} is null
     */
    static ThrowingIntPredicate wrap(IntPredicate predicate) {
        return Objects.requireNonNull(predicate)::test;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param predicate the one to map and return
     * @return the same predicate
     */
    static ThrowingIntPredicate map(ThrowingIntPredicate predicate) {
        return predicate;
    }


    /**
     * Evaluates this predicate on the given argument, may throw
     * checked {@link Exception}.
     *
     * @param value the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    boolean testThrows(int value) throws Exception;


    @Override
    default boolean test(int value) {
        try {
            return testThrows(value);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingIntPredicate}.
     *
     * @return this unwrapped {@link IntPredicate}
     */
    default IntPredicate unthrow() {
        return this;
    }
}
