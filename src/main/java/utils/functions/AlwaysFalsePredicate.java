package utils.functions;

import java.util.function.Predicate;


/**
 * Predicate that always returns {@code false}
 *
 * @param <T> ignored param
 */
public class AlwaysFalsePredicate<T> implements Predicate<T> {

    @Override
    public boolean test(T iAmNothing) {
        return false;
    }
}
