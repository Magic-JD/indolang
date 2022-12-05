package main.exception.handler;

import main.exception.Exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static main.exception.constants.ExceptionConstants.*;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(Exceptions.LanguageNotSupportedException.class)
    public ResponseEntity<Object> exception(Exceptions.LanguageNotSupportedException exception) {
        return new ResponseEntity<>(LANGUAGE_NOT_SUPPORTED, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(Exceptions.LanguageNotProvidedException.class)
    public ResponseEntity<Object> exception(Exceptions.LanguageNotProvidedException exception) {
        return new ResponseEntity<>(LANGUAGE_NOT_PROVIDED, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exceptions.WordNotFoundException.class)
    public ResponseEntity<Object> exception(Exceptions.WordNotFoundException exception) {
        return new ResponseEntity<>(WORD_COULD_NOT_BE_FOUND, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exceptions.AllWordsLearnedException.class)
    public ResponseEntity<Object> exception(Exceptions.AllWordsLearnedException exception) {
        return new ResponseEntity<>(USER_LEARNED_ALL_WORDS, HttpStatus.PARTIAL_CONTENT);
    }

    @ExceptionHandler(Exceptions.TranslationsNotFoundException.class)
    public ResponseEntity<Object> exception(Exceptions.TranslationsNotFoundException exception) {
        return new ResponseEntity<>(NO_TRANSLATIONS_FOUND, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exceptions.WordNotLearnedException.class)
    public ResponseEntity<Object> exception(Exceptions.WordNotLearnedException exception) {
        return new ResponseEntity<>(USER_HAS_NOT_LEARNED_WORD, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exceptions.WordDoesNotExistToBeRemoved.class)
    public ResponseEntity<Object> exception(Exceptions.WordDoesNotExistToBeRemoved exception) {
        return new ResponseEntity<>(WORD_CANNOT_BE_REMOVED, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
