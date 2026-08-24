package mu.rekolt.app;

public class Objective_1 {
    public static void main(String[] args) {

        //Stating the inputs/information needed for delivery
        String MemberID = "M-OO42"; //This is the member's identity number
        String ProduceCode = "BNS"; // This is the code assigned to our produce. Eg. 'BNS' is for Beans.
        double mass = 236.0;    // This is the kg weight for our produce
        int QualityScore = 91;   // Whole numbered quality score ranked from (0-100)

        // This part contains information about the payment
        double BasePriceForBeans = 90.0;
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

        // Printing Results
        System.out.println("REKOLT Planters’ Cooperative Produce Tracker");
        System.out.println("--------------------------------------------");

        System.out.printf("Member: %s%n", MemberID);
        System.out.printf("Produce: %s%n", ProduceCode);
        System.out.printf("Mass: %.2f kg%n", mass);
        System.out.printf("Quality Score: %d%n", QualityScore);

        System.out.printf("%nBase Value: %.2f MUR%n", BaseValue);
        System.out.printf("Grade Value: %.2f MUR%n", GradeValue);
        System.out.printf("Category Value: %.2f MUR%n", CategoryValue);
        System.out.printf("Commission: %.2f MUR%n", Commission);
        System.out.printf("Transport Levy: .%.2f MUR%n", TransportLevy);

        System.out.printf("%nNet Payable: %.2f MUR%n", NetPayable);
    }
}
