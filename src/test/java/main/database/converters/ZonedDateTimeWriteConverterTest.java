package main.database.converters;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static main.TestConstants.TIME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ZonedDateTimeWriteConverterTest {

    ZonedDateTimeWriteConverter SUT = new ZonedDateTimeWriteConverter();

    @Test
    void testConvertWillTakeADateAndReturnTheCorrectZonedDateTime() {
        Date converted = SUT.convert(TIME);
        assertNotNull(converted);
        assertEquals(TIME.toInstant(), converted.toInstant());
    }

}