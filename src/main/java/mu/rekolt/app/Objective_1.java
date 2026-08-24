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
        double CategoryMultiplier = 1.00;
        double RateOfCommission = 0.05;
        int LevyRatePerKg = 2;
        double TransportLevyPerKg = 2.0;

        // This will help us to calculate the base value
        double BaseValue = mass * BasePriceForBeans;

        // Here's where we will be applying the grade multiplier
        String Grade;
        double GradeMultiplier;
        if (QualityScore >= 85 && QualityScore <= 100) {
            Grade = "A";
            GradeMultiplier = 1.15;
        } else if (QualityScore >= 70) {
            Grade = "B";
            GradeMultiplier = 1.00;
        } else if (QualityScore >= 50) {
            Grade = "C";
            GradeMultiplier = 0.85;
        } else {
            Grade = "REJECTED";
            GradeMultiplier = 0.00;
        }
        double GradeValue = BaseValue * GradeMultiplier;

        // Here's where we will be applying the category multiplier
        double CategoryValue = GradeValue * CategoryMultiplier;

        // This will help us to calculate our commission
        double Commission = CategoryValue * RateOfCommission;

        // This will help us to calculate the transport levy
        double TransportLevy = mass * (double) LevyRatePerKg;

        // This will help us calculate our net payable
        double NetPayable = CategoryValue - Commission - TransportLevy;

        // Printing Results
        System.out.println("REKOLT Planters’ Cooperative Worked Example");
        System.out.println("--------------------------------------------");

        System.out.println("Member M-0042, 236 kg of Pro, quality score 91");
        System.out.printf("Member: %s%n", MemberID);
        System.out.printf("Produce: %s%n", ProduceCode);
        System.out.printf("Mass: %.2f kg%n", mass);
        System.out.printf("Quality Score: %d%n", QualityScore);

        System.out.printf("%n1. Base value");
        System.out.printf("   236.00 kg x 90.00 = %.2f MUR", BaseValue);
        System.out.printf("2. Grade %s multiplier: ", Grade);
        System.out.printf("   %.2f x %.2f = %.2f MUR", BaseValue, GradeMultiplier, GradeValue);
        System.out.printf("3. Cereal category multiplier: %n");
        System.out.printf("   %.2f x %.2f = %.2f MUR", GradeValue, CategoryMultiplier, CategoryValue);
        System.out.printf("4. Commission: %.2f MUR", Commission);
        System.out.printf("5. Transport levy: %n");
        System.out.printf("   236.00 kg x 2.00 =  %.2f MUR", TransportLevy);

        System.out.printf("%nNET PAYABLE: %.2f MUR%n", NetPayable);
    }
}
