package utils.functions;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;

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
                e -> assertEquals(testCheckedException, e)
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
    void tryCatch_When_SupplierThrows_Expect_FallbackFunctionValueReceived() {
        String result = TryCatch.tryCatchFallback(
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
        assertEquals(EXPECTED, TryCatch.tryCatchFallback(
                () -> EXPECTED,
                e -> {
                    throw new TestCheckedException();
                }
        ));
    }

    @Test
    void tryCatchFallback_When_SupplierThrows_Expect_FallbackFunctionValueReceived() {
        AtomicBoolean wasExecuted = new AtomicBoolean(false);
        String result = TryCatch.tryCatchFallback(
                () -> {
                    throw new TestCheckedException();
                },
                e -> wasExecuted.set(false),
                () -> EXPECTED
        );

        assertEquals(EXPECTED, result);
    }

    @Test
    void tryCatchFallback_When_SupplierDoesNotThrow_Expect_SupplierValueReturned_2() {
        assertEquals(EXPECTED, TryCatch.tryCatchFallback(
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
