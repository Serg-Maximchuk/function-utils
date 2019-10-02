package utils.functions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;

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

    @Test
    void merge_When_ArrayWithTwoRunnable_Expect_CalledInCorrectOrder() {
        Boolean[] calls = new Boolean[]{
                null, null
        };

        List<Runnable> setTrueRunnable = List.of(
                () -> calls[0] = true,
                () -> calls[1] = calls[0]
        );
        List<ThrowingRunnable> setFalseThrowingRunnable = List.of(
                () -> calls[0] = false,
                () -> calls[1] = calls[0]
        );


        ThrowingRunnable.merge(setTrueRunnable.toArray(Runnable[]::new)).run();
        assertTrue(calls[0]);
        assertTrue(calls[1]);

        ThrowingRunnable.throwingMerge(setFalseThrowingRunnable.toArray(ThrowingRunnable[]::new)).run();
        assertFalse(calls[0]);
        assertFalse(calls[1]);


        ThrowingRunnable.merge(setTrueRunnable).run();
        assertTrue(calls[0]);
        assertTrue(calls[1]);

        ThrowingRunnable.throwingMerge(setFalseThrowingRunnable).run();
        assertFalse(calls[0]);
        assertFalse(calls[1]);


        ThrowingRunnable.merge(setTrueRunnable.iterator()).run();
        assertTrue(calls[0]);
        assertTrue(calls[1]);

        ThrowingRunnable.throwingMerge(setFalseThrowingRunnable.iterator()).run();
        assertFalse(calls[0]);
        assertFalse(calls[1]);


        ThrowingRunnable.merge(setTrueRunnable.spliterator()).run();
        assertTrue(calls[0]);
        assertTrue(calls[1]);

        ThrowingRunnable.throwingMerge(setFalseThrowingRunnable.spliterator()).run();
        assertFalse(calls[0]);
        assertFalse(calls[1]);
    }

    @Test
    void merge_When_EmptyArray_Expect_EmptyRunnableReturned() {
        assertDoesNotThrow(() -> ThrowingRunnable.merge().run());
    }

    @Test
    void merge_When_ArrayContainsNull_Expect_NPE() {
        assertThrows(NullPointerException.class, () -> ThrowingRunnable.merge(new Runnable[]{null}).run());
    }

    @Test
    void throwingCompose() {
        Boolean[] calls = new Boolean[]{
                null, null
        };

        assertDoesNotThrow(() -> ThrowingRunnable.wrap(() -> calls[1] = calls[0])
                .throwingCompose(() -> calls[0] = true)
                .run());

        assertTrue(calls[0]);
        assertTrue(calls[1]);
    }

    @Test
    void throwingAndThen() {
        Boolean[] calls = new Boolean[]{
                null, null
        };

        assertDoesNotThrow(() -> ThrowingRunnable.wrap(() -> calls[0] = true)
                .throwingAndThen(() -> calls[1] = calls[0])
                .run());

        assertTrue(calls[0]);
        assertTrue(calls[1]);
    }
}
