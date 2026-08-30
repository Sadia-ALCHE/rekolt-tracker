package mu.rekolt.model;

// Base class for different types of produce.
public abstract class Produce {
    private final String code;
    private final String name;
    private final double basePricePerKg;

    public Produce(String code, String name, double basePricePerKg) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Produce code is required.");
        }
        if (basePricePerKg <= 0) {
            throw new IllegalArgumentException("Base price must be positive.");
        }
        this.code = code;
        this.name = name;
        this.basePricePerKg = basePricePerKg;
    }
    public abstract double getCategoryMultiplier();
    public abstract String getCategoryName();

    // Calculates the base value.
    public double baseValue(double mass) {
        return mass * basePricePerKg;
    }
    public String getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
    public double getBasePricePerKg() {
        return basePricePerKg;
    }

    @Override
    public String toString() {
        return code + " (" + name + ")";
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {return true;
        }
        if (!(other instanceof Produce)) {
            return false;
        }
        return code.equals(((Produce) other).code);
    }
    @Override
    public int hashCode() {
        return code.hashCode();
    }
}


