package main.database.converters;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static main.TestConstants.DATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ZonedDateTimeReadConverterTest {

    ZonedDateTimeReadConverter SUT = new ZonedDateTimeReadConverter();

    @Test
    void testConvertWillTakeADateAndReturnTheCorrectZonedDateTime() {
        ZonedDateTime converted = SUT.convert(DATE);
        assertNotNull(converted);
        assertEquals(DATE.toInstant(), converted.toInstant());
    }
}