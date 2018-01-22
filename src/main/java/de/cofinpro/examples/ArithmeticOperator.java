package de.cofinpro.examples;

import java.text.ParseException;
import java.util.function.BiFunction;

enum ArithmeticOperator implements Operator {

    ADD(0, (l1, l2) -> l1 + l2),
    SUBTRACT(0, (l1, l2) -> l1 - l2),
    MULTIPLY(1, (l1, l2) -> l1 * l2),
    DIVIDE(1, (l1, l2) -> l1 / l2);

    final int precedence;
    final BiFunction<Double, Double, Double> function;

    ArithmeticOperator(int precedence, BiFunction<Double, Double, Double> operator) {
        this.precedence = precedence;
        this.function = operator;
    }

    static ArithmeticOperator fromToken(String character) throws ParseException {
        switch (character.trim()) {
            case "+":
                return ADD;
            case "-":
                return SUBTRACT;
            case "*":
                return MULTIPLY;
            case "/":
                return DIVIDE;
            default:
                throw new ParseException("Unknown operator:" + character, -1);
        }
    }

    static boolean isOperator(String string) {
        return string.matches("[+\\-*/^]");
    }

    @Override
    public int getPrecedence() {
        return precedence;
    }

    public double apply(double left, double right) {
        return function.apply(left, right);
    }
}
