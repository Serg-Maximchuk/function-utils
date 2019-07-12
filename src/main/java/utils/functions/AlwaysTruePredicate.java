package utils.functions;

import java.util.function.Predicate;

/**
 * Predicate that always returns {@code true}
 *
 * @param <T> ignored param
 */
public class AlwaysTruePredicate<T> implements Predicate<T> {

    @Override
    public boolean test(T iAmNothing) {
        return true;
    }
}
