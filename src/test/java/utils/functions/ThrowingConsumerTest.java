package utils.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThrowingConsumerTest {

    private static final String STR_1 = "first";
    private static final String STR_2 = "second";
    private static final String EXPECTED = STR_1 + STR_2;

    @Test
    void throwingBiConsumer_When_DoesNotThrowException() {
        StringBuilder stringBuilder = new StringBuilder(STR_1);

        ThrowingConsumer<String> doesNotThrowConsumer = stringBuilder::append;

        assertDoesNotThrow(() -> doesNotThrowConsumer.accept(STR_2));
        assertEquals(EXPECTED, stringBuilder.toString());
    }

    @Test
    void throwingBiConsumer_When_ThrowingException() {
        ThrowingConsumer<String> throwingConsumer = (__) -> {
            throw new TestCheckedException();
        };

        assertThrows(TestCheckedException.class, () -> throwingConsumer.accept(STR_2));
        assertThrows(TestCheckedException.class, () -> throwingConsumer.unthrow().accept(STR_2));
    }

}
