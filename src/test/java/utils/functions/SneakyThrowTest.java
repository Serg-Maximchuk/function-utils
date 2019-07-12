package utils.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class SneakyThrowTest {

    @Test
    void sneakyThrow() {
        class CheckedTestException extends Exception {
        }

        assertThrows(
                CheckedTestException.class,
                () -> SneakyThrow.sneakyThrow(new CheckedTestException())
        );
    }
}
