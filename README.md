# Philadelphia Parking Violations and Property Data Analysis

This Java application processes and analyzes data from OpenDataPhilly regarding parking violations and property values in Philadelphia.

**Note: This is skeleton code with TODO comments. You need to implement all the methods marked with TODO.**

## Project Structure

```
src/
├── Main.java                    # Main entry point with menu system
├── ParkingViolation.java        # Data model for parking violations
├── Property.java                # Data model for properties
├── ParkingViolationReader.java  # Reads parking violations from CSV/JSON
├── PropertyReader.java          # Reads property data from CSV
├── PopulationReader.java        # Reads population data
└── DataProcessor.java           # Performs calculations and data processing
```

## Requirements

- Java 8 or higher
- JSON.simple library (json-simple-1.1.1.jar)

## Setup

1. Download the JSON.simple library (json-simple-1.1.1.jar) and add it to your project's classpath.

2. Compile the Java files:
   ```bash
   javac -cp ".:json-simple-1.1.1.jar" src/*.java
   ```

3. Run the program:
   ```bash
   java -cp ".:json-simple-1.1.1.jar:src" Main <format> <parking_file> <properties_file> <population_file>
   ```

   Example:
   ```bash
   java -cp ".:json-simple-1.1.1.jar:src" Main csv parking.csv properties.csv population.txt
   ```

## Command-Line Arguments

The program requires exactly 4 arguments (in order):
1. **Format**: Either `csv` or `json` (case-sensitive) - specifies the format of the parking violations file
2. **Parking file**: Path to the parking violations file (CSV or JSON)
3. **Properties file**: Path to the properties CSV file
4. **Population file**: Path to the population text file

## Features

The application provides a menu-driven interface with the following options:

1. **Total population for all ZIP codes** - Displays the sum of all populations
2. **Fines per capita for each ZIP code** - Shows parking fines per person for each ZIP code (only PA violations)
3. **Average residential market value** - Calculates average property value for a specified ZIP code
4. **Average residential total livable area** - Calculates average square footage for a specified ZIP code
5. **Residential market value per capita** - Calculates total property value divided by population for a ZIP code
0. **Exit** - Terminates the program

## Data Validation

The program handles invalid data gracefully:
- Missing, non-numeric, negative, or zero values for market value and total livable area are ignored
- Parking violations with missing ZIP codes or non-PA license plates are excluded from fines per capita calculations
- ZIP codes are normalized to their first 5 digits

## Notes

- For groups of 3, you'll need to implement menu options 6 and 7 (to be approved by your Project Manager)
- The program expects well-formed input files (assuming they exist and can be opened)
- ZIP codes may be missing for some entries, which the program handles appropriately

