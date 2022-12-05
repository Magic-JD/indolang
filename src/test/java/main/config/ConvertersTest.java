package main.config;


import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ConvertersTest {

    Converters SUT = new Converters();

    @Test
    void testMongoCustomConversionsHasCustomWriteTargetForZonedDateTime() {
        assertTrue(SUT.mongoCustomConversions().hasCustomWriteTarget(ZonedDateTime.class, Date.class));
    }

    @Test
    void testMongoCustomConversionsHasCustomReadTargetForZonedDateTime() {
        assertTrue(SUT.mongoCustomConversions().hasCustomReadTarget(Date.class, ZonedDateTime.class));
    }
}