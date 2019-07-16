package utils.functions;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;

import static java.util.stream.Collectors.*;

public class CollectOperation {

    public static <T> Collector<T, ?, List<T>> toShuffledList() {
        return collectingAndThen(toList(), Functions.apply(Collections::shuffle));
    }

}
