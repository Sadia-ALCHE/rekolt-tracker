package mu.rekolt.model;

public enum Grade {
    A(1.15),
    B(1.00),
    C(0.85),
    REJECT(0.00);

    private final double multiplier;

    Grade(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getMultiplier() {
        return multiplier;
    }

    /* Grades a quality score using the boundaries we were given:
       A = 85-100, B = 70-84, C = 50-69, REJECT = below 50
     */
    public static Grade fromScore(int qualityScore) {
        if (qualityScore < 0 || qualityScore > 100) {
            throw new IllegalArgumentException("Quality score must be between 0 and 100.");
        }
        if (qualityScore >= 85) {
            return A;
        } else if (qualityScore >= 70) {
            return B;
        } else if (qualityScore >= 50) {
            return C;
        } else {
            return REJECT;
        }
    }
}
