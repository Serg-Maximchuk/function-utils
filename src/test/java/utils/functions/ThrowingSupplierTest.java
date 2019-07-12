package utils.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThrowingSupplierTest {

    private static final String STR_1 = "first";
    private static final String STR_2 = "second";
    private static final String EXPECTED = STR_1 + STR_2;

    @Test
    void throwingSupplier_When_DoesNotThrowException() {
        StringBuilder stringBuilder = new StringBuilder(STR_1);

        ThrowingSupplier<StringBuilder> doesNotThrowConsumer = () -> stringBuilder.append(STR_2);

        assertDoesNotThrow(doesNotThrowConsumer::get);
        assertEquals(EXPECTED, stringBuilder.toString());
    }

    @Test
    void throwingSupplier_When_ThrowingException() {
        ThrowingSupplier<String> throwingConsumer = () -> {
            throw new TestCheckedException();
        };

        assertThrows(TestCheckedException.class, throwingConsumer::get);
        assertThrows(TestCheckedException.class, () -> throwingConsumer.unthrow().get());
    }

}
