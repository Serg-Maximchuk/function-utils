package utils.functions;

import java.util.List;
import java.util.function.Consumer;


public final class BatchOperation {

    public static <T> void withEach(List<T> list, Consumer<T> c) {
        list.forEach(c);
    }

    @SafeVarargs
    public static <T> void doForEach(Consumer<T> action, T... doWithUs) {
        for (T element : doWithUs) {
            action.accept(element);
        }
    }

}
