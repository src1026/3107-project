package test.processor;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import processor.DataProcessor;

public class FormatFourDecimalsTest {

    @Test
    public void testFormatFourDecimalsWithWholeNumber() {
        //   Whole number should show trailing zeros
        String result = DataProcessor.formatFourDecimals(5.0);
        assertEquals("5.0000", result);
    }

    @Test
    public void testFormatFourDecimalsWithOneDecimal() {
        //   One decimal place should show trailing zeros
        String result = DataProcessor.formatFourDecimals(5.1);
        assertEquals("5.1000", result);
    }

    @Test
    public void testFormatFourDecimalsWithTwoDecimals() {
        //   Two decimal places should show trailing zeros
        String result = DataProcessor.formatFourDecimals(5.12);
        assertEquals("5.1200", result);
    }

    @Test
    public void testFormatFourDecimalsWithThreeDecimals() {
        //   Three decimal places should show trailing zero
        String result = DataProcessor.formatFourDecimals(5.123);
        assertEquals("5.1230", result);
    }

    @Test
    public void testFormatFourDecimalsWithFourDecimals() {
        //   Four decimal places
        String result = DataProcessor.formatFourDecimals(5.1234);
        assertEquals("5.1234", result);
    }

    @Test
    public void testFormatFourDecimalsWithMoreThanFourDecimals() {
        //   More than four decimal places should round
        String result = DataProcessor.formatFourDecimals(5.12345);
        assertEquals("5.1235", result);
    }

    @Test
    public void testFormatFourDecimalsWithRoundingUp() {
        //   Rounding up
        String result = DataProcessor.formatFourDecimals(5.12349);
        assertEquals("5.1235", result);
    }

    @Test
    public void testFormatFourDecimalsWithZero() {
        //   Zero
        String result = DataProcessor.formatFourDecimals(0.0);
        assertEquals("0.0000", result);
    }

    @Test
    public void testFormatFourDecimalsWithNegativeNumber() {
        //   Negative number
        String result = DataProcessor.formatFourDecimals(-5.1234);
        assertEquals("-5.1234", result);
    }

    @Test
    public void testFormatFourDecimalsWithSmallNumber() {
        //   Small number less than 1
        String result = DataProcessor.formatFourDecimals(0.1234);
        assertEquals("0.1234", result);
    }

    @Test
    public void testFormatFourDecimalsWithLargeNumber() {
        //   Large number
        String result = DataProcessor.formatFourDecimals(12345.6789);
        assertEquals("12345.6789", result);
    }
}

