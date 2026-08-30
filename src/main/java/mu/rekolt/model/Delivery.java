package mu.rekolt.model;

import mu.rekolt.service.Payable;
import mu.rekolt.service.Reportable;

// One weighed load from one member. Runs the five payment steps.
public class Delivery implements Comparable<Delivery>, Payable, Reportable {

    private final String deliveryId;
    private final Member member;
    private final Produce produce;
    private final double mass;
    private final int qualityScore;
    private final int week;
    private final Grade grade;

    public Delivery(String deliveryId, Member member, Produce produce, double mass, int qualityScore, int week) {
        if (deliveryId == null || deliveryId.isBlank()) {
            throw new IllegalArgumentException("Delivery id is required.");
        }
        if (member == null) {
            throw new IllegalArgumentException("Member is required.");
        }
        if (produce == null) {
            throw new IllegalArgumentException("Produce is required.");
        }
        if (mass <= 0 || mass > 5000) {
            throw new IllegalArgumentException("Mass must be above 0 and not more than 5000 kg.");
        }
        if (week < 1 || week > 20) {
            throw new IllegalArgumentException("Week must be between 1 and 20.");
        }
        this.deliveryId = deliveryId;
        this.member = member;
        this.produce = produce;
        this.mass = mass;
        this.qualityScore = qualityScore;
        this.week = week;
        this.grade = Grade.fromScore(qualityScore); // also validates 0-100
    }

    // Step 1
    public double baseValue() {
        return produce.baseValue(mass);
    }
    // Step 2
    public double gradedValue() {
        return baseValue() * grade.getMultiplier();
    }
    // Step 3
    public double categorisedValue() {
        return gradedValue() * produce.getCategoryMultiplier();
    }
    // Step 4: REJECT pays no commission
    public double commission() {
        if (grade == Grade.REJECT) {
            return 0.0;
        }
        return categorisedValue() * 0.05;
    }
    // Step 5: REJECT pays no transport levy
    public double transportLevy() {
        if (grade == Grade.REJECT) {
            return 0.0;
        }
        return mass * 2.0;
    }

    @Override
    public double netPayable() {
        if (grade == Grade.REJECT) {
            return 0.0;
        }
        return categorisedValue() - commission() - transportLevy();
    }

    @Override
    public String summaryLine() {
        return String.format("%-7s %-7s %-4s %7.1f kg %-6s %,10.2f",
                deliveryId, member.getId(), produce.getCode(), mass, grade, netPayable());
    }

    // Lets deliveries be sorted automatically, highest net payable first.
    @Override
    public int compareTo(Delivery other) {
        return Double.compare(other.netPayable(), this.netPayable());
    }

    public String getDeliveryId() {
        return deliveryId;
    }
    public Member getMember() {
        return member;
    }
    public Produce getProduce() {
        return produce;
    }
    public double getMass() {
        return mass;
    }
    public int getQualityScore() {
        return qualityScore;
    }
    public int getWeek() {
        return week;
    }
    public Grade getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return String.format("%-7s %-7s %-4s %7.1f kg %-6s %,10.2f", deliveryId, member.getId(), produce.getCode(), mass, grade, netPayable());
    }
}