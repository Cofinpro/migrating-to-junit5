package de.cofinpro.examples;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;

import static de.cofinpro.examples.ArithmeticOperator.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ArithmeticOperatorTest {

    @RunWith(Parameterized.class)
    public static class Parsing {

        @Parameter(0)
        public String string;

        @Parameter(1)
        public ArithmeticOperator operator;

        @Parameterized.Parameters(name = "{index}: parsing \"{0}\" returns {1}")
        public static Collection<Object[]> parameters() {
            return Arrays.asList(new Object[][]{
                    {"+", ADD},
                    {"  +", ADD},
                    {"+  ", ADD},
                    {"-", SUBTRACT},
                    {" - ", SUBTRACT},
                    {"*", MULTIPLY},
                    {"  * ", MULTIPLY},
                    {"/", DIVIDE},
            });
        }

        @Test
        public void testParsing() throws ParseException {
            assertThat(ArithmeticOperator.fromToken(string), is(operator));
        }
    }

    @Test
    public void testAdd() {
        assertThat(ADD.apply(1, 2), is(3d));
    }

    @Test
    public void testSubtract() {
        assertThat(SUBTRACT.apply(3, 1), is(2d));
    }

    @Test
    public void testMultiply() {
        assertThat(MULTIPLY.apply(3, 2), is(6d));
    }

    @Test
    public void testDivide() {
        assertThat(DIVIDE.apply(6, 2), is(3d));
    }
}