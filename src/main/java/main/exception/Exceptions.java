package main.exception;

public class Exceptions {
    public static class LanguageNotSupportedException extends RuntimeException {
    }

    public static class LanguageNotProvidedException extends RuntimeException {
    }

    public static class WordNotFoundException extends RuntimeException {
    }

    public static class AllWordsLearnedException extends RuntimeException {
    }

    public static class TranslationsNotFoundException extends RuntimeException {
    }

    public static class UserHasNotLearnedWordException extends RuntimeException {
    }
}
