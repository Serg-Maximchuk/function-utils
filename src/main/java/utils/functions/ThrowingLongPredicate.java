package utils.functions;

import java.util.Objects;
import java.util.function.LongPredicate;

/**
 * {@link LongPredicate} that may sneaky throw checked {@link Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #testThrows(long)}.
 *
 * @see LongPredicate
 * @see ThrowingPredicate
 */
@FunctionalInterface
public interface ThrowingLongPredicate extends LongPredicate {

    /**
     * Unwrap {@link ThrowingLongPredicate}.
     *
     * @param predicate the one to be unwrapped
     * @return unwrapped {@link LongPredicate}
     */
    static LongPredicate unthrow(ThrowingLongPredicate predicate) {
        return predicate;
    }

    /**
     * Wrap input {@code predicate} as a throwing one.
     *
     * @param predicate the one to be wrapped
     * @return wrapped {@link ThrowingLongPredicate}
     * @throws NullPointerException if {@code predicate} is null
     */
    static ThrowingLongPredicate wrap(LongPredicate predicate) {
        return Objects.requireNonNull(predicate)::test;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param predicate the one to map and return
     * @return the same predicate
     */
    static ThrowingLongPredicate map(ThrowingLongPredicate predicate) {
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
    boolean testThrows(long value) throws Exception;


    @Override
    default boolean test(long value) {
        try {
            return testThrows(value);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingLongPredicate}.
     *
     * @return this unwrapped {@link LongPredicate}
     */
    default LongPredicate unthrow() {
        return this;
    }
}
