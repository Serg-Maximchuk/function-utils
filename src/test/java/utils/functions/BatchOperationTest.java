package utils.functions;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BatchOperationTest {

    private static final String TEST_WORD = "testWord";
    private static final char[] testArray = TEST_WORD.toCharArray();
    private static final List<Character> characters = IntStream.range(0, testArray.length)
            .mapToObj(i -> testArray[i])
            .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));

    @Test
    void withEach() {
        StringBuilder stringBuilder = new StringBuilder();

        BatchOperation.withEach(characters, stringBuilder::append);

        assertEquals(TEST_WORD, stringBuilder.toString());
    }

    @Test
    void doForEach() {
        StringBuilder stringBuilder = new StringBuilder();

        BatchOperation.doForEach(stringBuilder::append, characters.toArray(new Character[0]));

        assertEquals(TEST_WORD, stringBuilder.toString());
    }
}
