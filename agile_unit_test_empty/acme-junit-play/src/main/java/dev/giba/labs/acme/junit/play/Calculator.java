package dev.giba.labs.acme.junit.play;

public class Calculator {
    public double add(double x, double y) {
        return x + y;
    }

    public double sub(double x, double y) {
        return x - y;
    }

    public double mul(double x, double y) {
        return x * y;
    }

    public double div(double x, double y) {
        if (y <= 0) {
            throw new IllegalArgumentException("Divide by 0 is not allowed");
        }
        return x / y;
    }
}
