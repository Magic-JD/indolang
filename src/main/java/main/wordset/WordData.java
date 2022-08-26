package main.wordset;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.Set;

@Getter
@AllArgsConstructor
public class WordData {
    private final String keyWord;
    private final Set<String> translations;
    private ZonedDateTime date;
    private Integer sucessfulAnswers;

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public void setSucessfulAnswers(int sucessfulAnswers) {
        this.sucessfulAnswers = sucessfulAnswers;
    }
}
