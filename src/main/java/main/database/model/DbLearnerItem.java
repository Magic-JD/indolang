package main.database.model;

import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.ZonedDateTime;

@Document("learner")
@Getter
public class DbLearnerItem {

    @Id
    private ObjectId _id;
    @DocumentReference
    private final DbWordTranslationsItem wordTranslation;
    private final String username;
    private ZonedDateTime date;
    private Integer successfulAnswers;

    public DbLearnerItem(DbWordTranslationsItem wordTranslation, String username, ZonedDateTime date, Integer successfulAnswers) {
        this.wordTranslation = wordTranslation;
        this.username = username;
        this.date = date;
        this.successfulAnswers = successfulAnswers;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public void setSuccessfulAnswers(Integer successfulAnswers) {
        this.successfulAnswers = successfulAnswers;
    }
}
