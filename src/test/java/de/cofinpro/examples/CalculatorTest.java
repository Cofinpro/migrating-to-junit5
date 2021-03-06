package de.cofinpro.examples;

import java.text.ParseException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        this.calculator = new Calculator();
    }

    @Test
    void testSimpleAddition() throws ParseException {
        assertEquals(6.0, calculator.calculate("2 + 4"));
    }

    @Test
    @DisplayName("throws a parse exception on 💩 input")
    void testParseException() {
        ParseException exception = assertThrows(ParseException.class,
                () -> calculator.calculate("foo!"));
        assertTrue(exception.getMessage().contains("foo!"));
    }
}
