package mu.rekolt;

public class Main {
    public static void main(String[] args) {

        // The information for the delivery
        String MemberID = "M-OO42";
        String ProduceCode = "BNS";
        double mass = 236.0;
        int QualityScore = 91;

        // This part contains information about the payment
        double BasePrice = 90.0;
        double GradeMultiplier = 1.15;
        double CategoryMultiplier = 1.00;
        double RateOfCommission = 0.05;
        double TransportLevyPerKg = 2.0;

        // This will help us to calculate the base value
        double BaseValue = mass * BasePrice;

        // Here's where we will be applying the grade multiplier
        double GradeValue = BaseValue * GradeMultiplier;

        // Here's where we will be applying the category multiplier
        double CategoryValue = GradeValue * CategoryMultiplier;

        // This will help us to calculate our commission
        double Commission = CategoryValue * RateOfCommission;

        // This will help us to calculate the transport levy
        double TransportLevy = mass * TransportLevyPerKg;

        // This will help us calculate our net payable
        double NetPayable = CategoryValue - Commission - TransportLevy;

    }
}
