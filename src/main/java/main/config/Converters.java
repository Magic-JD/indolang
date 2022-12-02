package main.config;

import main.database.converters.ZonedDateTimeReadConverter;
import main.database.converters.ZonedDateTimeWriteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;

@Configuration
public class Converters {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(
                Arrays.asList(
                        new ZonedDateTimeReadConverter(),
                        new ZonedDateTimeWriteConverter()));
    }

}
