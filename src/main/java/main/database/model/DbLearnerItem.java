package main.database.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;

@Document("learner")
@Data
public class DbLearnerItem {
    private ObjectId wordId;
    private ObjectId userId;
    private ZonedDateTime date;
    private Integer successfulAnswers;
}
