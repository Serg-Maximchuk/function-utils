package utils.functions;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * {@code Predicate} that may throw checked {@code Exception}.
 *
 * <p>This is a functional interface whose functional method
 * is {@link #testThrowing(Object)}.
 *
 * @param <T> the type of the input to the predicate
 * @see Predicate
 */
@FunctionalInterface
public interface ThrowingPredicate<T> extends Predicate<T> {

    /**
     * Unwrap {@code ThrowingPredicate}.
     *
     * @param predicate the one to be unwrapped
     * @return unwrapped {@code Predicate}
     */
    @SuppressWarnings("unchecked")
    static <T1> Predicate<T1> unthrow(ThrowingPredicate<? super T1> predicate) {
        return (ThrowingPredicate<T1>) predicate;
    }

    /**
     * Wrap input {@code predicate} as a throwing one.
     *
     * @param predicate the one to be wrapped
     * @return wrapped {@code ThrowingPredicate}
     * @throws NullPointerException if {@code predicate} is null
     */
    static <T1> ThrowingPredicate<T1> wrap(Predicate<? super T1> predicate) {
        return Objects.requireNonNull(predicate)::test;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param predicate the one to map and return
     * @return the same predicate
     */
    @SuppressWarnings("unchecked")
    static <T1> ThrowingPredicate<T1> map(ThrowingPredicate<? super T1> predicate) {
        return (ThrowingPredicate<T1>) predicate;
    }

    /**
     * Returns a predicate that is the negation of the supplied predicate.
     * This is accomplished by returning result of the calling
     * {@code target.negate()}.
     *
     * @param <T>    the type of arguments to the specified predicate
     * @param target predicate to negate
     * @return a predicate that negates the results of the supplied
     * predicate
     * @throws NullPointerException if target is null
     */
    @SuppressWarnings("unchecked")
    static <T> ThrowingPredicate<T> not(Predicate<? super T> target) {
        return (ThrowingPredicate<T>) Objects.requireNonNull(target).negate();
    }

    /**
     * Returns a predicate that is the negation of the supplied predicate.
     * This is accomplished by returning result of the calling
     * {@code target.negate()}.
     *
     * @param <T>    the type of arguments to the specified predicate
     * @param target predicate to negate
     * @return a predicate that negates the results of the supplied
     * predicate
     * @throws NullPointerException if target is null
     */
    static <T> ThrowingPredicate<T> throwingNot(ThrowingPredicate<? super T> target) {
        return not(target);
    }


    /**
     * Evaluates this predicate on the given argument,
     * may throw checked {@code Exception}.
     *
     * @param t the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    boolean testThrowing(T t) throws Exception;


    @Override
    default boolean test(T t) {
        try {
            return testThrowing(t);
        } catch (Exception e) {//noinspection RedundantTypeArguments
            throw Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Unwrap this {@code ThrowingPredicate}.
     *
     * @return this unwrapped {@code Predicate}
     */
    default Predicate<T> unthrow() {
        return this;
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
    default ThrowingPredicate<T> and(Predicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t) -> test(t) && other.test(t);
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
    default ThrowingPredicate<T> throwingAnd(ThrowingPredicate<? super T> other) {
        return and(other);
    }

    /**
     * Returns a predicate that represents the logical negation of this
     * predicate.
     *
     * @return a predicate that represents the logical negation of this
     * predicate
     */
    @Override
    default ThrowingPredicate<T> negate() {
        return (t) -> !test(t);
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
    default ThrowingPredicate<T> or(Predicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t) -> test(t) || other.test(t);
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
    default ThrowingPredicate<T> throwingOr(ThrowingPredicate<? super T> other) {
        return or(other);
    }

}
