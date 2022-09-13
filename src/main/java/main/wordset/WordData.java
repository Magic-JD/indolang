package main.wordset;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.Set;

@Getter
@AllArgsConstructor
public class WordData {
    private final String keyWord;
    private Set<String> translations;
    private ZonedDateTime date;
    private Integer sucessfulAnswers;

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public void setSucessfulAnswers(int sucessfulAnswers) {
        this.sucessfulAnswers = sucessfulAnswers;
    }

    public void addTranslations(Set<String> translations) {
        this.translations.addAll(translations);
    }

    @Override
    public String toString() {
        return this.getKeyWord() + "," + String.join(":", this.getTranslations()) + "," + this.getDate().toString() + "," + this.sucessfulAnswers;
    }
}
