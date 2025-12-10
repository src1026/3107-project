package processor;

import java.util.Iterator;
import java.util.List;
import common.ParkingViolation;

public class ViolationList implements Iterator<ParkingViolation> {
    private final List<ParkingViolation> violations;
    private int currentIndex;

    public ViolationList(List<ParkingViolation> violations) {
        if (violations == null) {
            throw new IllegalArgumentException("Violations list cannot be null");
        }
        this.violations = violations;
        this.currentIndex = 0;
    }

    public void reset() {
        this.currentIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < violations.size();
    }

    @Override
    public ParkingViolation next() {
        if (!hasNext()) {
            throw new java.util.NoSuchElementException("No more violations to iterate");
        }
        return violations.get(currentIndex++);
    }

    public int size() {
        return violations.size();
    }
}

