package najah.code;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@Execution(ExecutionMode.CONCURRENT)
class TestUserService {

    private final UserService userService = new UserService();

    @ParameterizedTest
    @ValueSource(strings = {"student@test.com", "a@b.co", "admin@mail.org"})
    @DisplayName("valid emails")
    void testIsValidEmailValid(String email) {
        assertTrue(userService.isValidEmail(email));
        assertFalse(userService.isValidEmail("wrong"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"plainaddress", "student@", "student.com"})
    @DisplayName("invalid emails")
    void testIsValidEmailInvalid(String email) {
        assertFalse(userService.isValidEmail(email));
        assertTrue(userService.isValidEmail("admin@test.com"));
    }

    @Test
    @DisplayName("correct login")
    void testAuthenticateValid() {
        assertTrue(userService.authenticate("admin", "1234"));
        assertFalse(userService.authenticate("admin", "wrong"));
    }

    @ParameterizedTest
    @org.junit.jupiter.params.provider.CsvSource({
            "user, 1234",
            "admin, wrong",
            "user, wrong"
    })
    @DisplayName("wrong login")
    void testAuthenticateInvalid(String username, String password) {
        assertFalse(userService.authenticate(username, password));
        assertTrue(userService.authenticate("admin", "1234"));
    }

    @Test
    @DisplayName("timeout for user service")
    void testTimeout() {
        assertTimeout(Duration.ofMillis(100), () -> userService.isValidEmail("a@b.com"));
        assertTimeout(Duration.ofMillis(100), () -> userService.authenticate("admin", "1234"));
    }
}
