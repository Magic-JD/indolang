package main;

import main.database.model.DbLearnerItem;
import main.database.model.DbUserItem;
import main.database.model.DbWordTranslationsItem;
import main.rest.model.Answer;
import main.rest.model.Definition;
import main.rest.model.UserCredentialsDto;
import main.rest.model.Word;
import org.bson.types.ObjectId;
import org.springframework.http.HttpHeaders;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;

public class TestConstants {

    public static Instant NOW = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    public static ZonedDateTime TIME = ZonedDateTime.ofInstant(NOW, ZoneId.of("UTC"));
    public static Date DATE = Date.from(NOW);
    public static ZonedDateTime JUST_BEFORE_TIME = TIME.minusMinutes(3);
    public static ZonedDateTime BEFORE_TIME = TIME.minusMinutes(5);
    public static ZonedDateTime AFTER_TIME = TIME.plusMinutes(5);
    public static final String ACCEPTED_LANGUAGE_1 = "aa";
    public static final String ACCEPTED_LANGUAGE_2 = "bb";
    public static final String NON_ACCEPTED_LANGUAGE = "cc";
    public static final String EMPTY_STRING = "";
    public static final String WORD_1 = "WORD_1";
    public static final String WORD_2 = "WORD_2";
    public static final String WORD_3 = "WORD_3";
    public static final Word WORD_OBJECT_1 = new Word(WORD_1);
    public static final String TRANSLATION_1 = "TRANSLATION_1";
    public static final String TRANSLATION_2 = "TRANSLATION_2";
    public static final String TRANSLATION_3 = "TRANSLATION_3";
    public static final String TRANSLATION_4 = "TRANSLATION_4";
    public static final String TRANSLATION_5 = "TRANSLATION_5";
    public static final String TRANSLATION_6 = "TRANSLATION_6";
    public static final Set<String> EMPTY_SET = Set.of();
    public static final Set<String> TRANSLATION_SET_1 = Set.of(TRANSLATION_1, TRANSLATION_2);
    public static final Set<String> TRANSLATION_SET_1_SMALL = Set.of(TRANSLATION_1);
    public static final Set<String> TRANSLATION_SET_2 = Set.of(TRANSLATION_3, TRANSLATION_4);
    public static final Set<String> TRANSLATION_SET_2_SMALL = Set.of(TRANSLATION_3);
    public static final Set<String> TRANSLATION_SET_3 = Set.of(TRANSLATION_5, TRANSLATION_6);
    public static final String ROLE_1 = "USER";
    public static final String ROLE_2 = "ADMIN";
    public static final Set<String> ROLES_SET_1 = Set.of(ROLE_1);
    public static final Set<String> ROLES_SET_2 = Set.of(ROLE_1, ROLE_2);
    public static DbWordTranslationsItem DB_WORD_TRANSLATION_ITEM_1 = new DbWordTranslationsItem(ACCEPTED_LANGUAGE_1, WORD_1);
    public static DbWordTranslationsItem DB_WORD_TRANSLATION_ITEM_1_SMALL = new DbWordTranslationsItem(ACCEPTED_LANGUAGE_1, WORD_1);
    public static DbWordTranslationsItem DB_WORD_TRANSLATION_ITEM_2 = new DbWordTranslationsItem(ACCEPTED_LANGUAGE_1, WORD_2);
    public static DbWordTranslationsItem DB_WORD_TRANSLATION_ITEM_3 = new DbWordTranslationsItem(ACCEPTED_LANGUAGE_1, WORD_3);
    public static final ObjectId WT_ID_1 = new ObjectId();
    public static final ObjectId WT_ID_2 = new ObjectId();
    public static final ObjectId WT_ID_3 = new ObjectId();

    static {
        DB_WORD_TRANSLATION_ITEM_1.setTranslations(TRANSLATION_SET_1);
        DB_WORD_TRANSLATION_ITEM_1_SMALL.setTranslations(TRANSLATION_SET_1_SMALL);
        DB_WORD_TRANSLATION_ITEM_2.setTranslations(TRANSLATION_SET_2);
        DB_WORD_TRANSLATION_ITEM_3.setTranslations(TRANSLATION_SET_3);
        DB_WORD_TRANSLATION_ITEM_1.set_id(WT_ID_1);
        DB_WORD_TRANSLATION_ITEM_1_SMALL.set_id(WT_ID_1);
        DB_WORD_TRANSLATION_ITEM_2.set_id(WT_ID_2);
        DB_WORD_TRANSLATION_ITEM_3.set_id(WT_ID_3);
    }

    public static final String USERNAME_1 = "USERNAME_1";
    public static final String USERNAME_2 = "USERNAME_2";
    public static final int NUMBER = 3;
    public static final int ZERO = 0;
    public static DbLearnerItem DB_LEARNER_ITEM_AFTER = new DbLearnerItem(DB_WORD_TRANSLATION_ITEM_1, USERNAME_1, AFTER_TIME, NUMBER);
    public static DbLearnerItem DB_LEARNER_ITEM_BEFORE = new DbLearnerItem(DB_WORD_TRANSLATION_ITEM_1, USERNAME_1, BEFORE_TIME, NUMBER);
    public static DbLearnerItem DB_LEARNER_ITEM_SMALL = new DbLearnerItem(DB_WORD_TRANSLATION_ITEM_1_SMALL, USERNAME_1, BEFORE_TIME, NUMBER);
    public static DbLearnerItem DB_LEARNER_ITEM_JUST_BEFORE = new DbLearnerItem(DB_WORD_TRANSLATION_ITEM_2, USERNAME_1, JUST_BEFORE_TIME, NUMBER);
    public static Answer CORRECT_ANSWER = new Answer(WORD_1, TRANSLATION_1);
    public static Answer FALSE_ANSWER = new Answer(WORD_1, TRANSLATION_3);
    public static final String PASSWORD = "PASSWORD";
    public static final String INCORRECT_PASSWORD = "INCORRECT_PASSWORD";
    public static DbUserItem DB_USER_ITEM = new DbUserItem(USERNAME_1, PASSWORD, true, ROLES_SET_1);
    public static final Definition DEFINITION_1 = new Definition(WORD_1, TRANSLATION_1);
    public static final Definition DEFINITION_2 = new Definition(WORD_1, TRANSLATION_2);
    public static final Definition DEFINITION_3 = new Definition(WORD_2, TRANSLATION_3);
    public static final Definition DEFINITION_4 = new Definition(WORD_1, TRANSLATION_3);
    public static final HttpHeaders HEADERS_WO_LANG = new HttpHeaders();
    public static final HttpHeaders HEADERS_W_EMPTY_LANG = new HttpHeaders();
    public static final HttpHeaders HEADERS_W_WRONG_LANG = new HttpHeaders();
    public static final HttpHeaders CORRECT_HEADERS = new HttpHeaders();
    public static final UserCredentialsDto USER_CREDENTIALS_DTO = new UserCredentialsDto(USERNAME_1, PASSWORD);

    static {
        CORRECT_HEADERS.add(HttpHeaders.ACCEPT_LANGUAGE, "aa");
        HEADERS_W_EMPTY_LANG.add(HttpHeaders.ACCEPT_LANGUAGE, "");
        HEADERS_W_WRONG_LANG.add(HttpHeaders.ACCEPT_LANGUAGE, "cc");
    }
}
