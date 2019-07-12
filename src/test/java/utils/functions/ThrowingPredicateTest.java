package utils.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThrowingPredicateTest {


    @Test
    void throwingPredicate_When_DoesNotThrowException() {
        ThrowingPredicate<Object> doesNotThrowingPredicate = __ -> true;
        assertDoesNotThrow(() -> assertTrue(doesNotThrowingPredicate.test(new Object())));
    }

    @Test
    void throwingPredicate_When_ThrowingException() {
        ThrowingPredicate<Object> throwingPredicate = (__) -> {
            throw new TestCheckedException();
        };

        assertThrows(TestCheckedException.class, () -> throwingPredicate.test(new Object()));
        assertThrows(TestCheckedException.class, () -> throwingPredicate.unthrow().test(new Object()));
    }
}
