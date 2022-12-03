package main.validation;

import main.exception.Exceptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class LanguageValidator {

    @Value("${supported_languages}")
    private Set<String> supportedLanguages;

    public void validateLanguage(String language) {
        if ("".equals(language)) {
            throw new Exceptions.LanguageNotProvidedException();
        }
        if (!supportedLanguages.contains(language)) {
            throw new Exceptions.LanguageNotSupportedException();
        }
    }

}
