package de.cofinpro.examples;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Parses arithmetic expressions like "(1 + 2) * 3" and returns the result.
 * <p>
 * This supports basic arithmetic operators (+ - / *) as well as brackets.
 * </p>
 * <p>
 * We use the Dijkstra <a href="https://en.wikipedia.org/wiki/Shunting-yard_algorithm">shunting-yard algorithm</a>
 * to handle operator precedence.
 * </p>
 *
 * @author Gregor Tudan, Cofinpro AG
 */
public class Calculator {

    /**
     * stack to safe operators to that must be delayed due to operator precedence
     */
    private Stack<Operator> operatorStack = new Stack<>();

    /**
     * holds the parsed tokens in reverse Polish notation
     */
    private Queue<Object> outputQueue = new LinkedList<>();

    public Number calculate(String string) throws ParseException {
        if (string == null || string.trim().isEmpty()) {
            return 0d;
        }

        evaluate(string);
        return calculate();
    }

    private void evaluate(String expression) throws ParseException {
        StringTokenizer tokenizer = new StringTokenizer(expression, "+-*/()", true);
        while (tokenizer.hasMoreTokens()) {
            final String token = tokenizer.nextToken().trim();
            if (isNumber(token)) {
                outputQueue.offer(toNumber(token));
            } else if (ArithmeticOperator.isOperator(token)) {
                final ArithmeticOperator operator = ArithmeticOperator.fromToken(token);
                while (!operatorStack.empty()
                        && operatorStack.peek().getPrecedence() >= operator.getPrecedence()
                        && operatorStack.peek() != Bracket.LEFT) {
                    outputQueue.offer(operatorStack.pop());
                }
                operatorStack.push(operator);
            } else if (Bracket.isBracket(token) && Bracket.fromToken(token) == Bracket.LEFT) {
                operatorStack.push(Bracket.LEFT);
            } else if (Bracket.isBracket(token) && Bracket.fromToken(token) == Bracket.RIGHT) {
                while (operatorStack.peek() != Bracket.LEFT) {
                    outputQueue.offer(operatorStack.pop());
                }
                // pop the bracket from the stack
                operatorStack.pop();
            }
        }

        // Empty the stack into the output queue
        while (!operatorStack.isEmpty()) {
            outputQueue.offer(operatorStack.pop());
        }
    }

    /**
     * Calculates the result from the output queue and returns the result
     *
     * @return the calculation result
     */
    private Number calculate() {
        Stack<Double> stack = new Stack<>();

        // Empty the output queue
        for (Object token; (token = outputQueue.poll()) != null; ) {
            if (token instanceof Double) {
                stack.push((Double) token);
            } else {
                // remember that we use reverse polish notation - operands are inverted
                Double right = stack.pop();
                Double left = stack.pop();
                double result = ((ArithmeticOperator) token).apply(left, right);
                stack.push(result);
            }
        }
        return stack.pop();
    }

    private Number toNumber(String token) throws ParseException {
        return DecimalFormat.getInstance().parse(token).doubleValue();
    }

    private static boolean isNumber(String token) {
        return token.matches("\\d*,?\\d+");
    }


}
