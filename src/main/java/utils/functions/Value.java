package utils.functions;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public final class Value {

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
     * @throws NullPointerException if {@code consumer} is null
     * @see Value#withThrowing(java.lang.Object, utils.functions.ThrowingConsumer)
     */
    public static <T> T with(T value, Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer).accept(value);
        return value;
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
     * @throws NullPointerException if {@code consumer} is null
     * @see Value#with(java.lang.Object, java.util.function.Consumer)
     */
    public static <T> T withThrowing(T value, ThrowingConsumer<? super T> consumer) {
        return with(value, consumer);
    }

    public static <T> ThrowingUnaryOperator<T> apply(Consumer<? super T> action) {
        return x -> with(x, action);
    }

    public static <T> ThrowingUnaryOperator<T> applyThrowing(ThrowingConsumer<? super T> action) {
        return apply(action);
    }

    public static <T, R> R map(T value, Function<? super T, ? extends R> map) {
        return map.apply(value);
    }

    public static <T, R> R mapThrowing(T value, ThrowingFunction<? super T, ? extends R> map) {
        return map.apply(value);
    }

}
