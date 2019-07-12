package utils.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TryCatchTest {

    private static final String STR_1 = "first";
    private static final String STR_2 = "second";
    private static final String EXPECTED = STR_1 + STR_2;


    @Test
    void rethrowOnException_When_SupplierThrows() {
        assertThrows(TestCheckedException.class, () -> TryCatch.rethrowOnException(() -> {
            throw new TestCheckedException();
        }));
    }

    @Test
    void rethrowOnException_When_RunnableThrows() {
        assertThrows(TestCheckedException.class, () -> TryCatch.rethrowOnException(ThrowingRunnable.map(() -> {
            throw new TestCheckedException();
        })));
    }

    @Test
    void rethrowOnException_When_SupplierDoesNotThrow() {
        assertDoesNotThrow(() -> {
            StringBuilder result = TryCatch.rethrowOnException(() -> new StringBuilder(STR_1).append(STR_2));
            assertEquals(EXPECTED, result.toString());
        });
    }

    @Test
    void rethrowOnException_When_RunnableDoesNotThrow() {
        StringBuilder stringBuilder = new StringBuilder(STR_1);

        assertDoesNotThrow(() -> TryCatch.rethrowOnException(ThrowingRunnable.map(() -> stringBuilder.append(STR_2))));

        assertEquals(EXPECTED, stringBuilder.toString());
    }
}
