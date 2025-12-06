package com.solvians.showcase;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AppTest {
    @Test
    public void expectTwoIntArgs() {
        NumberFormatException numbers = Assertions.assertThrows(NumberFormatException.class, () -> {
            App.main(new String[]{"xxx", "zzz"});
        });
        numbers = Assertions.assertThrows(NumberFormatException.class, () -> {
            App.main(new String[]{"10", "zzz"});
        });
        assertEquals("For input string: \"zzz\"", numbers.getMessage());
    }

    @Test
    public void expectTwoArgs() {
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            App.main(new String[]{"xxx"});
        });

        thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            App.main(new String[]{"10"});
        });
        assertEquals("Expected number of threads and number of quotes. But got: [10]", thrown.getMessage());
    }

    @Test
    public void expectPositiveInts() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            App.main(new String[]{"0", "10"});
        });
        thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            App.main(new String[]{"10", "0"});
        });
        thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            App.main(new String[]{"-1", "10"});
        });
        thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            App.main(new String[]{"10", "-1"});
        });
        assertEquals("Number of threads and quotes must be positive integers.", thrown.getMessage());
    }
}
