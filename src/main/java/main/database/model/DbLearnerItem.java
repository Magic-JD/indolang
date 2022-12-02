package main.database.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;

@Document("learner")
@Getter
@AllArgsConstructor
public class DbLearnerItem {
    @Id
    private String id;
    private String wordId;
    private String username;
    private ZonedDateTime date;
    private Integer successfulAnswers;

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public void setSuccessfulAnswers(Integer successfulAnswers) {
        this.successfulAnswers = successfulAnswers;
    }
}
