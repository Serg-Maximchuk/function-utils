package utils.functions;

import java.util.Objects;
import java.util.function.DoublePredicate;

/**
 * {@link DoublePredicate} that may sneaky throw checked {@link Exception}.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #testThrows(double)}.
 *
 * @see DoublePredicate
 * @see ThrowingPredicate
 */
@FunctionalInterface
public interface ThrowingDoublePredicate extends DoublePredicate {

    /**
     * Unwrap {@link ThrowingDoublePredicate}.
     *
     * @param predicate the one to be unwrapped
     * @return unwrapped {@link DoublePredicate}
     */
    static DoublePredicate unthrow(ThrowingDoublePredicate predicate) {
        return predicate;
    }

    /**
     * Wrap input {@code predicate} as a throwing one.
     *
     * @param predicate the one to be wrapped
     * @return wrapped {@link ThrowingDoublePredicate}
     * @throws NullPointerException if {@code predicate} is null
     */
    static ThrowingDoublePredicate wrap(DoublePredicate predicate) {
        return Objects.requireNonNull(predicate)::test;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param predicate the one to map and return
     * @return the same predicate
     */
    static ThrowingDoublePredicate map(ThrowingDoublePredicate predicate) {
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
    boolean testThrows(double value) throws Exception;


    @Override
    default boolean test(double value) {
        try {
            return testThrows(value);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@link ThrowingDoublePredicate}.
     *
     * @return this unwrapped {@link DoublePredicate}
     */
    default DoublePredicate unthrow() {
        return this;
    }
}
