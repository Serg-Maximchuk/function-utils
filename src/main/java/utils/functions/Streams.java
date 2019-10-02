package utils.functions;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Streams {

    public static <T> Stream<T> toStream(Iterable<T> iterable) {
        return toStream(Objects.requireNonNull(iterable).spliterator());
    }

    public static <T> Stream<T> toStream(Spliterator<T> spliterator) {
        return StreamSupport.stream(Objects.requireNonNull(spliterator), false);
    }

    public static <T> Stream<T> toStream(Iterator<T> iterator) {
        return toStream(Spliterators.spliteratorUnknownSize(iterator, 0));
    }

}
