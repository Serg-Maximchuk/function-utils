package utils.functions;

import java.util.Objects;
import java.util.function.BiPredicate;

/**
 * {@link BiPredicate} that may sneaky throw checked exception.
 * <p>
 * This is a functional interface whose functional method
 * is {@link #testThrowing(Object, Object)}.
 *
 * @param <T> the type of the first argument to the predicate
 * @param <U> the type of the second argument the predicate
 * @see ThrowingPredicate
 * @see BiPredicate
 */
@FunctionalInterface
public interface ThrowingBiPredicate<T, U> extends BiPredicate<T, U> {

    /**
     * Unwrap {@code ThrowingBiPredicate}.
     *
     * @param predicate the one to be unwrapped
     * @return unwrapped {@code BiPredicate}
     */
    @SuppressWarnings("unchecked")
    static <T1, U1> BiPredicate<T1, U1> unthrow(ThrowingBiPredicate<? super T1, ? super U1> predicate) {
        return (BiPredicate<T1, U1>) predicate;
    }

    /**
     * Wrap input {@code predicate} as a throwing one.
     *
     * @param predicate the one to be wrapped
     * @return wrapped {@code ThrowingBiPredicate}
     * @throws NullPointerException if {@code predicate} is null
     */
    static <T1, U1> ThrowingBiPredicate<T1, U1> wrap(BiPredicate<? super T1, ? super U1> predicate) {
        return Objects.requireNonNull(predicate)::test;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param predicate the one to map and return
     * @return the same predicate
     */
    @SuppressWarnings("unchecked")
    static <T1, U1> ThrowingBiPredicate<T1, U1> map(ThrowingBiPredicate<? super T1, ? super U1> predicate) {
        return (ThrowingBiPredicate<T1, U1>) predicate;
    }


    /**
     * Evaluates this predicate on the given arguments.
     *
     * @param t the first input argument
     * @param u the second input argument
     * @return {@code true} if the input arguments match the predicate,
     * otherwise {@code false}
     */
    boolean testThrowing(T t, U u) throws Exception;


    @Override
    default boolean test(T t, U u) {
        try {
            return testThrowing(t, u);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * AND of this predicate and another.  When evaluating the composed
     * predicate, if this predicate is {@code false}, then the {@code other}
     * predicate is not evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed
     * to the caller; if evaluation of this predicate throws an exception, the
     * {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ANDed with this
     *              predicate
     * @return a composed predicate that represents the short-circuiting logical
     * AND of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    @Override
    default ThrowingBiPredicate<T, U> and(BiPredicate<? super T, ? super U> other) {
        Objects.requireNonNull(other);
        return (T t, U u) -> test(t, u) && other.test(t, u);
    }

    /**
     * Returns a predicate that represents the logical negation of this
     * predicate.
     *
     * @return a predicate that represents the logical negation of this
     * predicate
     */
    @Override
    default ThrowingBiPredicate<T, U> negate() {
        return (T t, U u) -> !test(t, u);
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * OR of this predicate and another.  When evaluating the composed
     * predicate, if this predicate is {@code true}, then the {@code other}
     * predicate is not evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed
     * to the caller; if evaluation of this predicate throws an exception, the
     * {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ORed with this
     *              predicate
     * @return a composed predicate that represents the short-circuiting logical
     * OR of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    @Override
    default ThrowingBiPredicate<T, U> or(BiPredicate<? super T, ? super U> other) {
        Objects.requireNonNull(other);
        return (T t, U u) -> test(t, u) || other.test(t, u);
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * AND of this predicate and another.  When evaluating the composed
     * predicate, if this predicate is {@code false}, then the {@code other}
     * predicate is not evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed
     * to the caller; if evaluation of this predicate throws an exception, the
     * {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ANDed with this
     *              predicate
     * @return a composed predicate that represents the short-circuiting logical
     * AND of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    default ThrowingBiPredicate<T, U> andThrowing(ThrowingBiPredicate<? super T, ? super U> other) {
        return and(other);
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * OR of this predicate and another.  When evaluating the composed
     * predicate, if this predicate is {@code true}, then the {@code other}
     * predicate is not evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed
     * to the caller; if evaluation of this predicate throws an exception, the
     * {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ORed with this
     *              predicate
     * @return a composed predicate that represents the short-circuiting logical
     * OR of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    default ThrowingBiPredicate<T, U> orThrowing(ThrowingBiPredicate<? super T, ? super U> other) {
        return or(other);
    }

    /**
     * Unwrap this {@code ThrowingBiPredicate}.
     *
     * @return this unwrapped {@code BiPredicate}
     */
    default BiPredicate<T, U> unthrow() {
        return this;
    }
}
