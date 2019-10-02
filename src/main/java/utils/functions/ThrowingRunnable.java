package utils.functions;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.stream.Stream;

/**
 * {@link Runnable} that may throw checked {@link Exception}.
 *
 * <p>This is a functional interface whose functional method
 * is {@link #runThrowing()}.
 *
 * @see Runnable
 */
@FunctionalInterface
public interface ThrowingRunnable extends Runnable {

    /**
     * Unwrap {@link ThrowingRunnable}.
     *
     * @param runnable the one to be unwrapped
     * @return unwrapped {@link Runnable}
     * @see #unthrow()
     */
    static Runnable unthrow(ThrowingRunnable runnable) {
        return runnable;
    }

    /**
     * Use it when you don't want to cast lambda by yourself.
     *
     * @param runnable runnable to map and return
     * @return the same runnable
     * @see #wrap(Runnable)
     */
    static ThrowingRunnable map(ThrowingRunnable runnable) {
        return runnable;
    }

    /**
     * Returns merged {@link ThrowingRunnable} that is a
     * sequence of calls of each {@link ThrowingRunnable}
     * in {@code mergeUs}.
     *
     * @param mergeUs an array of tasks
     * @return merged {@link ThrowingRunnable} that is a
     * sequence of calls of each {@link ThrowingRunnable}
     * in {@code mergeUs}
     * @throws NullPointerException if {@code mergeUs} or any it's element is null
     * @see #merge(Runnable...)
     * @see #merge(Iterable)
     * @see #merge(Iterator)
     * @see #merge(Spliterator)
     * @see #merge(Stream)
     * @see #throwingMerge(Stream)
     * @see #throwingMerge(Iterator)
     * @see #throwingMerge(Iterable)
     * @see #throwingMerge(Spliterator)
     */
    static ThrowingRunnable throwingMerge(ThrowingRunnable... mergeUs) {
        return throwingMerge(Stream.of(requireNonNull(mergeUs, "Array should not be null!")));
    }

    /**
     * Returns merged {@link ThrowingRunnable} that is a
     * sequence of calls of each {@link ThrowingRunnable}
     * in {@code mergeUs}.
     *
     * @param mergeUs a stream of tasks
     * @return merged {@link ThrowingRunnable} that is a
     * sequence of calls of each {@link ThrowingRunnable}
     * in {@code mergeUs}
     * @throws NullPointerException if {@code mergeUs} or
     *                              any it's element is null
     * @see #merge(Runnable...)
     * @see #merge(Iterable)
     * @see #merge(Iterator)
     * @see #merge(Spliterator)
     * @see #merge(Stream)
     * @see #throwingMerge(ThrowingRunnable...)
     * @see #throwingMerge(Iterator)
     * @see #throwingMerge(Iterable)
     * @see #throwingMerge(Spliterator)
     */
    static ThrowingRunnable throwingMerge(Stream<ThrowingRunnable> mergeUs) {
        return requireNonNull(mergeUs, "Stream should not be null!")
                .map(Objects::requireNonNull)
                .reduce(empty(), ThrowingRunnable::andThen);
    }

    /**
     * Empty {@link ThrowingRunnable}, may be useful.
     *
     * @return empty {@link ThrowingRunnable}
     */
    static ThrowingRunnable empty() {
        return () -> {
        };
    }

    /**
     * Returns a composed {@link ThrowingRunnable} that first calls {@code this}
     * and then {@code after}.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param after the function to call after {@code this}
     * @return a composed function that first calls {@code this} and then {@code after}
     * @throws NullPointerException if {@code after} is null
     * @see #compose(Runnable)
     * @see #throwingCompose(ThrowingRunnable)
     * @see #throwingAndThen(ThrowingRunnable)
     */
    default ThrowingRunnable andThen(Runnable after) {
        requireNonNull(after);
        return () -> {
            this.run();
            after.run();
        };
    }

    @Override
    default void run() {
        try {
            runThrowing();
        } catch (Exception e) {//noinspection RedundantTypeArguments
            Functions.<RuntimeException>sneakyThrow(e);
        }
    }

    /**
     * Returns merged {@link ThrowingRunnable} that is a
     * sequence of calls of each {@link Runnable} in {@code mergeUs}.
     *
     * @param mergeUs an array of tasks
     * @return merged {@link ThrowingRunnable} that is a
     * sequence of calls of each {@link Runnable} in {@code mergeUs}
     * @throws NullPointerException if {@code mergeUs} or any of it's element is null
     * @see #merge(Iterable)
     * @see #merge(Iterator)
     * @see #merge(Spliterator)
     * @see #merge(Stream)
     * @see #throwingMerge(ThrowingRunnable...)
     * @see #throwingMerge(Stream)
     * @see #throwingMerge(Iterator)
     * @see #throwingMerge(Iterable)
     * @see #throwingMerge(Spliterator)
     */
    static ThrowingRunnable merge(Runnable... mergeUs) {
        return merge(Arrays.stream(requireNonNull(mergeUs, "Array should not be null!")));
    }

    /**
     * Returns merged {@link ThrowingRunnable} that is a
     * sequence of calls of each {@link Runnable} in {@code mergeUs}.
     *
     * @param mergeUs a stream of tasks
     * @return merged {@link ThrowingRunnable} that is a
     * sequence of calls of each {@link Runnable} in {@code mergeUs}
     * @throws NullPointerException if {@code mergeUs} or any it's element is null
     * @see #merge(Runnable...)
     * @see #merge(Iterable)
     * @see #merge(Iterator)
     * @see #merge(Spliterator)
     * @see #throwingMerge(ThrowingRunnable...)
     * @see #throwingMerge(Stream)
     * @see #throwingMerge(Iterator)
     * @see #throwingMerge(Iterable)
     * @see #throwingMerge(Spliterator)
     */
    static ThrowingRunnable merge(Stream<Runnable> mergeUs) {
        return throwingMerge(requireNonNull(mergeUs, "Stream should not be null!").map(ThrowingRunnable::wrap));
    }

    /**
     * Wrap input {@code runnable} as a throwing one.
     *
     * @param runnable the one to be wrapped
     * @return wrapped {@link ThrowingRunnable}
     * @throws NullPointerException if {@code runnable} is null
     * @see #map(ThrowingRunnable)
     */
    static ThrowingRunnable wrap(Runnable runnable) {
        return requireNonNull(runnable)::run;
    }

    /**
     * Returns merged {@link ThrowingRunnable} that is a
     * sequence of calls of each {@link Runnable} in {@code mergeUs}.
     *
     * @param mergeUs an iterable of tasks
     * @return merged {@link ThrowingRunnable} that is a
     * sequence of calls of each {@link Runnable} in {@code mergeUs}
     * @throws NullPointerException if {@code mergeUs} or any it's element is null
     * @see #merge(Runnable...)
     * @see #merge(Iterator)
     * @see #merge(Spliterator)
     * @see #merge(Stream)
     * @see #throwingMerge(ThrowingRunnable...)
     * @see #throwingMerge(Stream)
     * @see #throwingMerge(Iterator)
     * @see #throwingMerge(Iterable)
     * @see #throwingMerge(Spliterator)
     */
    static ThrowingRunnable merge(Iterable<Runnable> mergeUs) {
        return merge(Streams.toStream(mergeUs));
    }

    /**
     * Returns merged {@link ThrowingRunnable} that is a
     * sequence of calls of each {@link ThrowingRunnable}
     * in {@code mergeUs}.
     *
     * @param mergeUs an iterable of tasks
     * @return merged {@link ThrowingRunnable} that is a
     * sequence of calls of each {@link ThrowingRunnable}
     * in {@code mergeUs}
     * @throws NullPointerException if {@code mergeUs} or
     *                              any it's element is null
     * @see #merge(Runnable...)
     * @see #merge(Iterable)
     * @see #merge(Iterator)
     * @see #merge(Spliterator)
     * @see #merge(Stream)
     * @see #throwingMerge(ThrowingRunnable...)
     * @see #throwingMerge(Stream)
     * @see #throwingMerge(Iterator)
     * @see #throwingMerge(Spliterator)
     */
    static ThrowingRunnable throwingMerge(Iterable<ThrowingRunnable> mergeUs) {
        return throwingMerge(Streams.toStream(mergeUs));
    }

    /**
     * Returns merged {@link ThrowingRunnable} that is a
     * sequence of calls of each {@link Runnable} in {@code mergeUs}.
     *
     * @param mergeUs an iterator of tasks
     * @return merged {@link ThrowingRunnable} that is a
     * sequence of calls of each {@link Runnable} in {@code mergeUs}
     * @throws NullPointerException if {@code mergeUs} or any it's element is null
     * @see #merge(Runnable...)
     * @see #merge(Iterable)
     * @see #merge(Spliterator)
     * @see #merge(Stream)
     * @see #throwingMerge(ThrowingRunnable...)
     * @see #throwingMerge(Stream)
     * @see #throwingMerge(Iterator)
     * @see #throwingMerge(Iterable)
     * @see #throwingMerge(Spliterator)
     */
    static ThrowingRunnable merge(Iterator<Runnable> mergeUs) {
        return merge(Streams.toStream(mergeUs));
    }

    /**
     * Returns merged {@link ThrowingRunnable} that is a
     * sequence of calls of each {@link ThrowingRunnable}
     * in {@code mergeUs}.
     *
     * @param mergeUs an iterator of tasks
     * @return merged {@link ThrowingRunnable} that is a
     * sequence of calls of each {@link ThrowingRunnable}
     * in {@code mergeUs}
     * @throws NullPointerException if {@code mergeUs} or
     *                              any it's element is null
     * @see #merge(Runnable...)
     * @see #merge(Iterable)
     * @see #merge(Iterator)
     * @see #merge(Spliterator)
     * @see #merge(Stream)
     * @see #throwingMerge(ThrowingRunnable...)
     * @see #throwingMerge(Stream)
     * @see #throwingMerge(Iterable)
     * @see #throwingMerge(Spliterator)
     */
    static ThrowingRunnable throwingMerge(Iterator<ThrowingRunnable> mergeUs) {
        return throwingMerge(Streams.toStream(mergeUs));
    }

    /**
     * Returns merged {@link ThrowingRunnable} that is a
     * sequence of calls of each {@link Runnable} in {@code mergeUs}.
     *
     * @param mergeUs a spliterator of tasks
     * @return merged {@link ThrowingRunnable} that is a
     * sequence of calls of each {@link Runnable} in {@code mergeUs}
     * @throws NullPointerException if {@code mergeUs} or any it's element is null
     * @see #merge(Runnable...)
     * @see #merge(Iterable)
     * @see #merge(Iterator)
     * @see #merge(Stream)
     * @see #throwingMerge(ThrowingRunnable...)
     * @see #throwingMerge(Stream)
     * @see #throwingMerge(Iterator)
     * @see #throwingMerge(Iterable)
     * @see #throwingMerge(Spliterator)
     */
    static ThrowingRunnable merge(Spliterator<Runnable> mergeUs) {
        return merge(Streams.toStream(mergeUs));
    }

    /**
     * Returns merged {@link ThrowingRunnable} that is a
     * sequence of calls of each {@link ThrowingRunnable}
     * in {@code mergeUs}.
     *
     * @param mergeUs a spliterator of tasks
     * @return merged {@link ThrowingRunnable} that is a
     * sequence of calls of each {@link ThrowingRunnable}
     * in {@code mergeUs}
     * @throws NullPointerException if {@code mergeUs} or
     *                              any it's element is null
     * @see #merge(Runnable...)
     * @see #merge(Iterable)
     * @see #merge(Iterator)
     * @see #merge(Spliterator)
     * @see #merge(Stream)
     * @see #throwingMerge(ThrowingRunnable...)
     * @see #throwingMerge(Stream)
     * @see #throwingMerge(Iterator)
     * @see #throwingMerge(Iterable)
     */
    static ThrowingRunnable throwingMerge(Spliterator<ThrowingRunnable> mergeUs) {
        return throwingMerge(Streams.toStream(mergeUs));
    }

    /**
     * The main action, may throw checked {@link Exception}.
     */
    void runThrowing() throws Exception;

    /**
     * Unwrap this {@link ThrowingRunnable}.
     *
     * @return this unwrapped {@link Runnable}
     * @see #unthrow(ThrowingRunnable)
     */
    default Runnable unthrow() {
        return this;
    }

    /**
     * Returns a composed {@link ThrowingRunnable} that first calls {@code this}
     * and then {@code after}.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param after the function to call after {@code this}
     * @return a composed function that first calls {@code this} and then {@code after}
     * @throws NullPointerException if {@code after} is null
     * @see #compose(Runnable)
     * @see #throwingCompose(ThrowingRunnable)
     * @see #andThen(Runnable)
     */
    default ThrowingRunnable throwingAndThen(ThrowingRunnable after) {
        return andThen(after);
    }

    /**
     * Returns a composed {@link ThrowingRunnable} that first calls {@code before}
     * and then {@code this}.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param before the function to call before {@code this}
     * @return a composed function that first calls {@code before} and then {@code this}
     * @throws NullPointerException if {@code before} is null
     * @see #compose(Runnable)
     * @see #andThen(Runnable)
     * @see #throwingAndThen(ThrowingRunnable)
     */
    default ThrowingRunnable throwingCompose(ThrowingRunnable before) {
        return compose(before);
    }

    /**
     * Returns a composed {@link ThrowingRunnable} that first calls {@code before}
     * and then {@code this}.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param before the function to call before {@code this}
     * @return a composed function that first calls {@code before} and then {@code this}
     * @throws NullPointerException if {@code before} is null
     * @see #throwingCompose(ThrowingRunnable)
     * @see #andThen(Runnable)
     * @see #throwingAndThen(ThrowingRunnable)
     */
    default ThrowingRunnable compose(Runnable before) {
        requireNonNull(before);
        return () -> {
            before.run();
            this.run();
        };
    }
}
