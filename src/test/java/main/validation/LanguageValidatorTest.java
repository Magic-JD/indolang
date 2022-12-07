package main.validation;

import main.exception.Exceptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static main.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = LanguageValidator.class)
class LanguageValidatorTest {

    @Autowired
    LanguageValidator languageValidator;

    @Test
    void testLanguageValidatorThrowsLanguageNotProvidedExceptionIfLanguageIsBlank() {
        assertThrows(Exceptions.LanguageNotProvidedException.class, () -> languageValidator.validateLanguage(EMPTY_STRING));
    }

    @Test
    void testLanguageValidatorThrowsLanguageNotSupportedExceptionIfLanguageIsNotOnTheSupportedList() {
        assertThrows(Exceptions.LanguageNotSupportedException.class, () -> languageValidator.validateLanguage(NON_ACCEPTED_LANGUAGE));
    }

    @Test
    void testLanguageValidatorDoesNotThrowIfTheLanguageIsSupported() {
        assertDoesNotThrow(() -> languageValidator.validateLanguage(ACCEPTED_LANGUAGE_1));
        assertDoesNotThrow(() -> languageValidator.validateLanguage(ACCEPTED_LANGUAGE_2));
    }

}