package test.processor;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import processor.DataProcessor;

/**
 * Test class for DataProcessor.formatFourDecimals() method.
 * One test method per test case to achieve 100% statement coverage.
 */
public class FormatFourDecimalsTest {

    @Test
    public void testFormatFourDecimalsWithWholeNumber() {
        // Test case: Whole number should show trailing zeros
        String result = DataProcessor.formatFourDecimals(5.0);
        assertEquals("5.0000", result);
    }

    @Test
    public void testFormatFourDecimalsWithOneDecimal() {
        // Test case: One decimal place should show trailing zeros
        String result = DataProcessor.formatFourDecimals(5.1);
        assertEquals("5.1000", result);
    }

    @Test
    public void testFormatFourDecimalsWithTwoDecimals() {
        // Test case: Two decimal places should show trailing zeros
        String result = DataProcessor.formatFourDecimals(5.12);
        assertEquals("5.1200", result);
    }

    @Test
    public void testFormatFourDecimalsWithThreeDecimals() {
        // Test case: Three decimal places should show trailing zero
        String result = DataProcessor.formatFourDecimals(5.123);
        assertEquals("5.1230", result);
    }

    @Test
    public void testFormatFourDecimalsWithFourDecimals() {
        // Test case: Four decimal places
        String result = DataProcessor.formatFourDecimals(5.1234);
        assertEquals("5.1234", result);
    }

    @Test
    public void testFormatFourDecimalsWithMoreThanFourDecimals() {
        // Test case: More than four decimal places should round
        String result = DataProcessor.formatFourDecimals(5.12345);
        assertEquals("5.1235", result);
    }

    @Test
    public void testFormatFourDecimalsWithRoundingUp() {
        // Test case: Rounding up
        String result = DataProcessor.formatFourDecimals(5.12349);
        assertEquals("5.1235", result);
    }

    @Test
    public void testFormatFourDecimalsWithZero() {
        // Test case: Zero
        String result = DataProcessor.formatFourDecimals(0.0);
        assertEquals("0.0000", result);
    }

    @Test
    public void testFormatFourDecimalsWithNegativeNumber() {
        // Test case: Negative number
        String result = DataProcessor.formatFourDecimals(-5.1234);
        assertEquals("-5.1234", result);
    }

    @Test
    public void testFormatFourDecimalsWithSmallNumber() {
        // Test case: Small number less than 1
        String result = DataProcessor.formatFourDecimals(0.1234);
        assertEquals("0.1234", result);
    }

    @Test
    public void testFormatFourDecimalsWithLargeNumber() {
        // Test case: Large number
        String result = DataProcessor.formatFourDecimals(12345.6789);
        assertEquals("12345.6789", result);
    }
}

