package mu.rekolt.model;

public class Produce {
    private final String code;
    private final String name;
    private final double basePricePerKg;
    private final double categoryMultiplier;
    private final String categoryName;

    public Produce(String code, String name, double basePricePerKg,
                   double categoryMultiplier, String categoryName) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Produce code is required.");
        }
        if (basePricePerKg <= 0) {
            throw new IllegalArgumentException("Base price must be positive.");
        }
        this.code = code;
        this.name = name;
        this.basePricePerKg = basePricePerKg;
        this.categoryMultiplier = categoryMultiplier;
        this.categoryName = categoryName;
    }

    //1 of the payment rules: mass x base price
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

    public double getCategoryMultiplier() {
        return categoryMultiplier;
    }

    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public String toString() {
        return code + " (" + name + ")";
    }
}
