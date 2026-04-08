package najah.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@TestMethodOrder(OrderAnnotation.class)
class TestCalculator {

    private Calculator calculator;

    @BeforeAll
    static void beforeAll() {
        System.out.println("calculator setup complete");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("calculator tests finished");
    }

    @BeforeEach
    void setUp() {
        System.out.println("starting calculator test");
        calculator = new Calculator();
    }

    @AfterEach
    void tearDown() {
        System.out.println("ending calculator test");
    }

    @Order(1)
    @ParameterizedTest
    @CsvSource({
            "1, 2, 3",
            "5, 5, 10",
            "-2, 2, 0"
    })
    @DisplayName("add should return the right sum")
    void testAdd(int a, int b, int expected) {
        int result = calculator.add(a, b);

        assertEquals(expected, result);
        assertEquals(6, calculator.add(1, 2, 3));
    }

    @Order(2)
    @Test
    @DisplayName("add with no numbers should return zero")
    void testAddEmpty() {
        assertEquals(0, calculator.add());
        assertEquals(0, calculator.add(new int[]{}));
    }

    @Order(3)
    @Test
    @DisplayName("divide should work for normal numbers")
    void testDivideValid() {
        assertEquals(5, calculator.divide(10, 2));
        assertEquals(3, calculator.divide(9, 3));
    }

    @Order(4)
    @Test
    @DisplayName("divide should throw when divisor is zero")
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> calculator.divide(5, 0));
        assertEquals(2, calculator.divide(8, 4));
    }

    @Order(5)
    @ParameterizedTest
    @CsvSource({
            "0, 1",
            "1, 1",
            "5, 120"
    })
    @DisplayName("factorial should return expected values")
    void testFactorialValid(int input, int expected) {
        assertEquals(expected, calculator.factorial(input));
        assertEquals(2, calculator.factorial(2));
    }

    @Order(6)
    @Test
    @DisplayName("factorial should reject negative numbers")
    void testFactorialInvalid() {
        assertThrows(IllegalArgumentException.class, () -> calculator.factorial(-1));
        assertEquals(24, calculator.factorial(4));
    }

    @Order(7)
    @Test
    @DisplayName("calculator methods should finish quickly")
    void testTimeout() {
        assertTimeout(Duration.ofMillis(100), () -> calculator.factorial(10));
        assertTimeout(Duration.ofMillis(100), () -> calculator.add(1, 2, 3, 4, 5));
    }
}
