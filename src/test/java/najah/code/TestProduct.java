package najah.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.time.Duration;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class TestProduct {

    @Test
    @DisplayName("check product constructor")
    void testConstructorValid() {
        Product product = new Product("Book", 50.0);

        assertEquals("Book", product.getName());
        assertEquals(50.0, product.getPrice());
    }

    @Test
    @DisplayName("constructor with bad price")
    void testConstructorInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new Product("Pen", -1.0));
        assertEquals(10.0, new Product("Pen", 10.0).getPrice());
    }

    @ParameterizedTest
    @CsvSource({
            "10, 90",
            "25, 75",
            "50, 50"
    })
    @DisplayName("apply discount normally")
    void testApplyDiscountValid(double discount, double finalPrice) {
        Product product = new Product("Bag", 100.0);
        product.applyDiscount(discount);

        assertEquals(discount, product.getDiscount());
        assertEquals(finalPrice, product.getFinalPrice());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, 60})
    @DisplayName("apply discount with wrong value")
    void testApplyDiscountInvalid(double discount) {
        Product product = new Product("Phone", 200.0);

        assertThrows(IllegalArgumentException.class, () -> product.applyDiscount(discount));
        assertEquals(200.0, product.getFinalPrice());
    }

    @Test
    @DisplayName("final price without discount")
    void testGetFinalPriceWithoutDiscount() {
        Product product = new Product("Mouse", 80.0);

        assertEquals(80.0, product.getFinalPrice());
        assertEquals(0.0, product.getDiscount());
    }

    @Test
    @DisplayName("timeout test for product")
    void testTimeout() {
        Product product = new Product("Keyboard", 120.0);

        assertTimeout(Duration.ofMillis(100), () -> product.applyDiscount(20));
        assertTimeout(Duration.ofMillis(100), product::getFinalPrice);
    }

    @Disabled("This one fails. To fix it, change expected value to 80.0.")
    @Test
    @DisplayName("disabled example of a failing product test")
    void testFailingExample() {
        Product product = new Product("Table", 100.0);
        product.applyDiscount(20);

        assertEquals(70.0, product.getFinalPrice());
    }
}
