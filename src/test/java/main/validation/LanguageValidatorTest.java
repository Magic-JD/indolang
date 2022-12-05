package main.validation;

import main.exception.Exceptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = "supported_languages=aa,bb", classes = LanguageValidator.class)
class LanguageValidatorTest {

    @Autowired
    LanguageValidator languageValidator;

    @Test
    void testLanguageValidatorThrowsLanguageNotProvidedExceptionIfLanguageIsBlank() {
        assertThrows(Exceptions.LanguageNotProvidedException.class, () -> languageValidator.validateLanguage(""));
    }

    @Test
    void testLanguageValidatorThrowsLanguageNotSupportedExceptionIfLanguageIsNotOnTheSupportedList() {
        assertThrows(Exceptions.LanguageNotSupportedException.class, () -> languageValidator.validateLanguage("cc"));
    }

    @Test
    void testLanguageValidatorDoesNotThrowIfTheLanguageIsSupported() {
        assertDoesNotThrow(() -> languageValidator.validateLanguage("aa"));
        assertDoesNotThrow(() -> languageValidator.validateLanguage("bb"));
    }

}