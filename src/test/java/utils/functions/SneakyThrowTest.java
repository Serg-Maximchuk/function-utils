package utils.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class SneakyThrowTest {

    @Test
    void sneakyThrow() {
        assertThrows(
                TestCheckedException.class,
                () -> SneakyThrow.sneakyThrow(new TestCheckedException())
        );
    }
}
