package utils.functions;

import java.util.Objects;
import java.util.function.Consumer;


public final class BatchOperation {

    public static <T> void withEach(Iterable<? extends T> iterable, Consumer<? super T> action) {
        Objects.requireNonNull(iterable).forEach(action);
    }

    @SafeVarargs
    public static <T> void doForEach(Consumer<? super T> action, T... doWithUs) {
        Objects.requireNonNull(action);

        for (T element : doWithUs) {
            action.accept(element);
        }
    }

}
