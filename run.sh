#!/bin/bash

if [ $# -lt 4 ]; then
    echo "Usage: ./run.sh <csv|json> <parking_file> <properties_file> <population_file>"
    echo "Example: ./run.sh csv data/parking.csv data/properties.csv data/population.txt"
    exit 1
fi

java -cp ".:bin:lib/json-simple-1.1.1.jar" presentation.Main "$@"

