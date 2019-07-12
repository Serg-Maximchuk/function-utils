package utils.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThrowingBiConsumerTest {

    private static final String STR_1 = "first";
    private static final String STR_2 = "second";
    private static final String EXPECTED = STR_1 + STR_2;

    @Test
    void throwingBiConsumer_When_DoesNotThrowException() {
        StringBuilder stringBuilder = new StringBuilder();

        ThrowingBiConsumer<String, String> doesNotThrowBiConsumer = (str1, str2) -> stringBuilder.append(str1)
                .append(str2);

        assertDoesNotThrow(() -> doesNotThrowBiConsumer.accept(STR_1, STR_2));
        assertEquals(EXPECTED, stringBuilder.toString());
    }

    @Test
    void throwingBiConsumer_When_ThrowingException() {
        ThrowingBiConsumer<String, String> throwingBiConsumer = (__1, __2) -> {
            throw new TestCheckedException();
        };

        assertThrows(TestCheckedException.class, () -> throwingBiConsumer.accept(STR_1, STR_2));
        assertThrows(TestCheckedException.class, () -> throwingBiConsumer.unthrow().accept(STR_1, STR_2));
    }
}
