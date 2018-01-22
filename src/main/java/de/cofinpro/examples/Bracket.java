package de.cofinpro.examples;

import java.text.ParseException;

enum Bracket implements Operator {
    LEFT,
    RIGHT;

    static boolean isBracket(String string) {
        return string != null && string.trim().matches("[()]");
    }

    public static Bracket fromToken(String token) throws ParseException {
        switch (token.trim()) {
            case "(":
                return LEFT;
            case ")":
                return RIGHT;
            default:
                throw new ParseException("Not a bracket: " + token, 0);
        }
    }

    @Override
    public int getPrecedence() {
        return 2;
    }
}
