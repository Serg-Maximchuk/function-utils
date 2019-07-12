package utils.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThrowingFunctionTest {

    private static final String STR_1 = "first";
    private static final String STR_2 = "second";
    private static final String EXPECTED = STR_1 + STR_2;

    @Test
    void throwingFunction_When_DoesNotThrowException() {
        StringBuilder stringBuilder = new StringBuilder(STR_1);

        ThrowingFunction<String, StringBuilder> doesNotThrowingFunction = stringBuilder::append;

        assertDoesNotThrow(() -> doesNotThrowingFunction.apply(STR_2));
        assertEquals(EXPECTED, stringBuilder.toString());
    }

    @Test
    void throwingFunction_When_ThrowingException() {
        ThrowingFunction<String, String> throwingFunction = (__) -> {
            throw new TestCheckedException();
        };

        assertThrows(TestCheckedException.class, () -> throwingFunction.apply(STR_1));
        assertThrows(TestCheckedException.class, () -> throwingFunction.unthrow().apply(STR_2));
    }
}
