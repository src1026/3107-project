# Testing Information

## Test Structure

JUnit tests have been created for the `processor` tier (`DataProcessor` class) following the requirements:

- **One test class per method** being tested
- **One test method per test case** within each test class
- Tests are designed to achieve **100% statement coverage**
- Tests use mock objects (test helpers) for data management tier classes

## Test Classes Created

1. **DataProcessorConstructorTest** - Tests the constructor with various null parameter scenarios
2. **GetTotalPopulationTest** - Tests `getTotalPopulation()` method
3. **GetFinesPerCapitaTest** - Tests `getFinesPerCapita()` method
4. **GetAverageMarketValueTest** - Tests `getAverageMarketValue()` method
5. **GetAverageTotalLivableAreaTest** - Tests `getAverageTotalLivableArea()` method
6. **GetMarketValuePerCapitaTest** - Tests `getMarketValuePerCapita()` method
7. **FormatFourDecimalsTest** - Tests `formatFourDecimals()` static method

## Important Notes

### ParkingViolation Implementation Required

The test class `GetFinesPerCapitaTest` uses a helper class `TestParkingViolation` that extends `ParkingViolation`. 

**Before running tests, you must implement the `ParkingViolation` class** in `src/common/ParkingViolation.java` with:
- Private fields for all data
- Constructor that takes all parameters
- Getter methods for all fields
- Implementation of `hasValidZipCode()` and `isFromPA()` methods

Once `ParkingViolation` is implemented, update `TestParkingViolation.java` to call the super constructor properly.

## Running Tests

To compile and run the tests, you'll need:
- JUnit 4 (or JUnit 5)
- The compiled classes from all tiers

Example compilation:
```bash
javac -cp ".:junit-4.13.2.jar:hamcrest-core-1.3.jar" -d bin src/common/*.java src/datamanagement/*.java src/processor/*.java src/test/processor/*.java
```

Example test execution:
```bash
java -cp ".:bin:junit-4.13.2.jar:hamcrest-core-1.3.jar" org.junit.runner.JUnitCore test.processor.DataProcessorConstructorTest
```

## Test Coverage

Each test class is designed to cover all code paths in the method being tested:

- **Normal cases**: Valid inputs producing expected outputs
- **Edge cases**: Empty collections, zero values, null values
- **Error cases**: Invalid inputs that should throw exceptions
- **Boundary cases**: Values at limits (rounding, etc.)

## Defensive Programming

All tests verify that defensive programming is working:
- Null parameter checks throw `IllegalArgumentException`
- Null values in collections are handled gracefully
- Invalid data is filtered out appropriately

