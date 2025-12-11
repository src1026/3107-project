package test.processor;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import processor.DataProcessor;
import common.ParkingViolation;
import common.Property;

public class ResetInstanceTest {

    @Test
    public void testResetInstanceWhenNull() {
        // resetInstance() should handle null instance gracefully
        DataProcessor.resetInstance(); // Ensure it's null
        DataProcessor.resetInstance(); // Call again when null
        assertNull(DataProcessor.getInstance());
    }

    @Test
    public void testResetInstanceWhenNotNull() {
        // resetInstance() should clear caches and set instance to null
        List<ParkingViolation> violations = new ArrayList<>();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("19103", 100000.0, 1000.0));
        Map<String, Integer> population = new HashMap<>();
        
        DataProcessor.resetInstance();
        DataProcessor processor = DataProcessor.getInstance(violations, properties, population);
        
        // Populate caches
        processor.getAverageMarketValue("19103");
        processor.getAverageTotalLivableArea("19103");
        
        // Reset instance
        DataProcessor.resetInstance();
        
        // Verify instance is null
        assertNull(DataProcessor.getInstance());
        
        // Verify new instance can be created (caches were cleared)
        DataProcessor newProcessor = DataProcessor.getInstance(violations, properties, population);
        assertNotNull(newProcessor);
        // Should recalculate, not use old cache
        assertEquals(100000, newProcessor.getAverageMarketValue("19103"));
    }
}

