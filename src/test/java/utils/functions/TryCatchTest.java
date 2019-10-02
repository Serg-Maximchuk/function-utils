package utils.functions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.time.Instant;

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

    @Test
    void tryCatch_When_RunnableThrows() {
        TestCheckedException testCheckedException = new TestCheckedException();

        TryCatch.tryCatch(
                () -> {
                    throw testCheckedException;
                },
                e -> {
                    assertEquals(testCheckedException, e);
                }
        );
    }

    @Test
    void tryCatch_When_RunnableDoesNotThrow() {
        assertDoesNotThrow(() -> TryCatch.tryCatch(
                () -> {
                },
                e -> {
                    throw new RuntimeException(e);
                }
        ));
    }

    @Test
    void tryCatch_When_SupplierThrows_Expect_FallbackSupplierValueReceived() {
        String result = TryCatch.tryCatch(
                () -> {
                    if (Instant.now().toEpochMilli() > 0) {
                        throw new TestCheckedException();
                    }
                    return null;
                },
                () -> EXPECTED
        );

        assertEquals(EXPECTED, result);
    }

    @Test
    void tryCatch_When_SupplierDoesNotThrow_Expect_SupplierValueReturned() {
        assertEquals(EXPECTED, TryCatch.tryCatch(
                () -> EXPECTED,
                () -> {
                    if (Instant.now().toEpochMilli() > 0) {
                        throw new TestCheckedException();
                    }
                    return null;
                }
        ));
    }

    @Test
    void tryCatch_When_SupplierThrows_Expect_FallbackFunctionValueReceived() {
        String result = TryCatch.tryCatch(
                () -> {
                    if (Instant.now().toEpochMilli() > 0) {
                        throw new TestCheckedException();
                    }
                    return null;
                },
                e -> EXPECTED
        );

        assertEquals(EXPECTED, result);
    }

    @Test
    void tryCatch_When_SupplierDoesNotThrow_Expect_SupplierValueReturned_2() {
        assertEquals(EXPECTED, TryCatch.tryCatch(
                () -> EXPECTED,
                e -> {
                    throw new TestCheckedException();
                }
        ));
    }

    @Test
    void rethrowOnException_With_Consumer_When_SupplierThrows() {
        TestCheckedException testCheckedException = new TestCheckedException();

        assertThrows(TestCheckedException.class, () -> TryCatch.rethrowOnException(
                () -> {
                    throw testCheckedException;
                },
                e -> assertEquals(testCheckedException, e)
        ));
    }

    @Test
    void rethrowOnException_With_Consumer_When_SupplierDoesNotThrow() {
        assertDoesNotThrow(() -> TryCatch.rethrowOnException(
                () -> null,
                e -> {
                    throw new RuntimeException(e);
                }
        ));
    }
}
