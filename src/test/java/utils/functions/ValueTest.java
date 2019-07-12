package utils.functions;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValueTest {

    private static final String TEST_STR = "test";

    @Test
    void with() {
        List<String> strings = new ArrayList<>();

        Value.with(TEST_STR, strings::add);

        assertTrue(strings.contains(TEST_STR));
    }

    @Test
    void withThrowing_When_NotThrowing() {
        List<String> strings = new ArrayList<>();

        Value.withThrowing(TEST_STR, strings::add);

        assertTrue(strings.contains(TEST_STR));
    }

    @Test
    void withThrowing_When_Throwing() {
        assertThrows(TestCheckedException.class, () -> Value.withThrowing(TEST_STR, s -> {
            throw new TestCheckedException();
        }));
    }

    @Test
    void map() {
        List<String> strings = new ArrayList<>();
        assertTrue(() -> Value.map(TEST_STR, strings::add));
    }

    @Test
    void mapThrowing_When_NotThrowing() {
        List<String> strings = new ArrayList<>();
        assertTrue(() -> Value.mapThrowing(TEST_STR, strings::add));
    }

    @Test
    void mapThrowing_When_Throwing() {
        assertThrows(TestCheckedException.class, () -> Value.mapThrowing(TEST_STR, s -> {
            throw new TestCheckedException();
        }));
    }
}
