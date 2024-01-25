package dev.giba.labs.acme.junit.play;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalculatorTest {
    private Calculator calculator;

    @Test
    void shouldAddCorrectly() {
        var localCalculator = new Calculator();
        var x = 1;
        var y = 2;

        Assertions.assertEquals(3, localCalculator.add(x, y));
        //assertTrue
        //assertFalse
        //assertNull
        //assertNotNull

    }

    @Test
    @DisplayName("Deve subtrair corretamente")
    void shouldSubtractCorrectly() {
        var localCalculator = new Calculator();
        var x = 3;
        var y = 2;

        Assertions.assertEquals(1, localCalculator.sub(x, y));
    }

    @Test
    @DisplayName("Deve multiplicar corretamente")
    void shouldMultiplyCorrectly() {
        //Given
        var localCalculator = new Calculator();
        var x = 4;
        var y = 4;
        var expectedResult = 16;

        //When
        var result = localCalculator.mul(x, y);

        //Then
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Deve dividir corretamente")
    void shouldDivideCorrectly() {
        //Given
        var x = 32;
        var y = 8;
        var expectedResult = 4;

        //When
        var result = calculator.div(x, y);

        //Then
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Deve levantar uma exceção ao tentar dividir por zero")
    void shouldRaiseExceptionWhenTryToDividePerZero() {
        //Given
        var x = 64;
        var y = 0;
        var expectedError = "Divide by 0 is not allowed";

        //When
        var exception = Assertions.assertThrows(
                IllegalArgumentException.class, () -> calculator.div(x, y));

        //Then
        Assertions.assertEquals(expectedError, exception.getMessage());

    }

    @BeforeEach
    void beforeEachTest() {
        this.calculator = new Calculator();
    }

}
