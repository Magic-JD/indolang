package main.exception.handler;

import main.exception.Exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(Exceptions.LanguageNotSupportedException.class)
    public ResponseEntity<Object> exception(Exceptions.LanguageNotSupportedException exception) {
        return new ResponseEntity<>("This language is currently not supported", HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(Exceptions.LanguageNotProvidedException.class)
    public ResponseEntity<Object> exception(Exceptions.LanguageNotProvidedException exception) {
        return new ResponseEntity<>("The language is not provided", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exceptions.WordNotFoundException.class)
    public ResponseEntity<Object> exception(Exceptions.WordNotFoundException exception) {
        return new ResponseEntity<>("The provided word could not be found in the dictionary.", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exceptions.AllWordsLearnedException.class)
    public ResponseEntity<Object> exception(Exceptions.AllWordsLearnedException exception) {
        return new ResponseEntity<>("User has learned all the possible words.", HttpStatus.PARTIAL_CONTENT);
    }

    @ExceptionHandler(Exceptions.TranslationsNotFoundException.class)
    public ResponseEntity<Object> exception(Exceptions.TranslationsNotFoundException exception) {
        return new ResponseEntity<>("Translations for the given question word could not be found.", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exceptions.UserHasNotLearnedWordException.class)
    public ResponseEntity<Object> exception(Exceptions.UserHasNotLearnedWordException exception) {
        return new ResponseEntity<>("The user has not yet learned this word, or it has been removed from the database.", HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
