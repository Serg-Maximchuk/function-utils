package utils.functions;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * An aggregate class for MFU methods
 */
public final class Functions {

    /**
     * Instead of:
     * <pre>
     *   SomeType some = new SomeType();
     *   some.setSomeInt(2);
     *   some.setSomeString(&quot;string&quot;);
     *   some.setSomethingMore(new Something());
     *   return some;
     * </pre>
     * You can write it in more imperative way:
     * <pre>
     *   return Value.with(new SomeType(), some -&gt; {
     *     some.setSomeInt(2);
     *     some.setSomeString(&quot;string&quot;);
     *     some.setSomethingMore(new Something());
     *   });
     * </pre>
     * Or:
     * <pre>
     *   return with(new HashSet&lt;&gt;(), set -&gt; set.add(new Object()));
     * </pre>
     * Or:
     * <pre>
     *   return with(new HttpHeaders(), headers -&gt; headers.add(AUTHORIZATION, "Basic ..."));
     * </pre>
     * Or even better:
     * <pre>
     *   return with(new User(), users::add);
     * </pre>
     *
     * @param value    a value to be applied to {@code consumer}
     * @param consumer will accept a {@code value}
     * @return the {@code value}
     * @see Functions#withThrowing(java.lang.Object, utils.functions.ThrowingConsumer)
     */
    public static <T> T with(T value, Consumer<? super T> consumer) {
        return Value.with(value, consumer);
    }

    /**
     * Instead of:
     * <pre>
     *   SomeType some = new SomeType();
     *   some.setSomeInt(2);
     *   some.setSomeString(&quot;string&quot;);
     *   some.setSomethingMore(new Something());
     *   return some;
     * </pre>
     * You can write it in more imperative way:
     * <pre>
     *   return Value.with(new SomeType(), some -&gt; {
     *     some.setSomeInt(2);
     *     some.setSomeString(&quot;string&quot;);
     *     some.setSomethingMore(new Something());
     *   });
     * </pre>
     * Or:
     * <pre>
     *   return with(new HashSet&lt;&gt;(), set -&gt; set.add(new Object()));
     * </pre>
     * Or:
     * <pre>
     *   return with(new HttpHeaders(), headers -&gt; headers.add(AUTHORIZATION, "Basic ..."));
     * </pre>
     * Or even better:
     * <pre>
     *   return with(new User(), users::add);
     * </pre>
     * <p>
     * As a bonus, consumer is throwable.
     *
     * @param value    a value to be applied to {@code consumer}
     * @param consumer will accept a {@code value}
     * @return the {@code value}
     * @see Functions#with(java.lang.Object, java.util.function.Consumer)
     */
    public static <T> T withThrowing(T value, ThrowingConsumer<? super T> consumer) {
        return Value.withThrowing(value, consumer);
    }

    public static <T> ThrowingUnaryOperator<T> apply(Consumer<? super T> action) {
        return Value.apply(action);
    }

    public static <T> ThrowingUnaryOperator<T> applyThrowing(ThrowingConsumer<? super T> action) {
        return Value.applyThrowing(action);
    }

    public static <T, R> R map(T value, Function<? super T, ? extends R> function) {
        return Value.map(value, function);
    }

    public static <T, R> R mapThrowing(T value, ThrowingFunction<? super T, ? extends R> function) {
        return Value.mapThrowing(value, function);
    }

    public static <T extends Throwable> T sneakyThrow(Throwable throwable) throws T {
        return SneakyThrow.sneakyThrow(throwable);
    }

    public static <T> void withEach(List<? extends T> lst, Consumer<? super T> consumer) {
        BatchOperation.withEach(lst, consumer);
    }

    @SafeVarargs
    public static <T> void doForEach(Consumer<? super T> action, T... doWithUs) {
        BatchOperation.doForEach(action, doWithUs);
    }

    /**
     * Will rethrow any caught exception
     *
     * @return the supplier result
     * @see Functions#rethrowOnException(utils.functions.ThrowingRunnable)
     */
    public static <T> T rethrowOnException(ThrowingSupplier<? extends T> supplier) {
        return TryCatch.rethrowOnException(supplier);
    }

    /**
     * Will rethrow any caught exception
     *
     * @see Functions#rethrowOnException(utils.functions.ThrowingSupplier)
     */
    public static void rethrowOnException(ThrowingRunnable runnable) {
        TryCatch.rethrowOnException(runnable);
    }

    /**
     * Just to prevent casting lambda to Function, sometimes it is required.
     *
     * @return same function
     */
    public static <T, R> ThrowingFunction<T, R> mapFunction(Function<? super T, ? extends R> map) {
        return ThrowingFunction.wrap(map);
    }

    /**
     * Just to prevent casting lambda to Consumer, sometimes it is required.
     *
     * @return same consumer
     */
    public static <T> ThrowingConsumer<T> mapConsumer(Consumer<? super T> consumer) {
        return ThrowingConsumer.wrap(consumer);
    }

    public static <T> T doIfThrowing(
            ThrowingSupplier<? extends T> supplier,
            ThrowingPredicate<? super T> condition,
            ThrowingConsumer<? super T> job
    ) {
        return doIf(supplier, condition, job);
    }

    public static <T> T doIf(
            Supplier<? extends T> supplier,
            Predicate<? super T> condition,
            Consumer<? super T> job
    ) {
        T operand = supplier.get();

        if (condition.test(operand)) {
            job.accept(operand);
        }

        return operand;
    }

}
