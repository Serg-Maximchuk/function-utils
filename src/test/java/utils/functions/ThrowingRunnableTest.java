package utils.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThrowingRunnableTest {

    private static final String STR_1 = "first";
    private static final String STR_2 = "second";
    private static final String EXPECTED = STR_1 + STR_2;

    @Test
    void throwingRunnable_When_DoesNotThrowException() {
        StringBuilder stringBuilder = new StringBuilder(STR_1);

        ThrowingRunnable doesNotThrowRunnable = () -> stringBuilder.append(STR_2);

        assertDoesNotThrow(doesNotThrowRunnable::run);
        assertEquals(EXPECTED, stringBuilder.toString());
    }

    @Test
    void throwingRunnable_When_ThrowingException() {
        ThrowingRunnable throwingRunnable = () -> {
            throw new TestCheckedException();
        };

        assertThrows(TestCheckedException.class, throwingRunnable::run);
        assertThrows(TestCheckedException.class, () -> throwingRunnable.unthrow().run());
    }

}
