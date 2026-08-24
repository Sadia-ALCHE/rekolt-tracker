package mu.rekolt.app;

public class Objective_1 {
    public static void main(String[] args) {

        //Stating the inputs/information needed for delivery
        String memberId = "M-0042"; //This is the member's identity number
        String produceCode = "BNS"; // This is the code assigned to our produce. Eg. 'BNS' is for Beans.
        double mass = 236.0;    // This is the kg weight for our produce
        int qualityScore = 91;   // Whole numbered quality score ranked from (0-100)

        // This part contains payment rules for beans
        double basePriceForBeans = 90.0;
        double categoryMultiplier = 1.00;
        double rateOfCommission = 0.05;
        int levyRatePerKg = 2;

        // This will help us to calculate the base value
        double baseValue = mass * basePriceForBeans;

        // Here's where we will be applying the grade multiplier
        String grade;
        double gradeMultiplier;
        if (qualityScore >= 85 && qualityScore <= 100) {
            grade = "A";
            gradeMultiplier = 1.15;
        } else if (qualityScore >= 70) {
            grade = "B";
            gradeMultiplier = 1.00;
        } else if (qualityScore >= 50) {
            grade = "C";
            gradeMultiplier = 0.85;
        } else {
            grade = "REJECT";
            gradeMultiplier = 0.00;
        }
        double gradeValue = baseValue * gradeMultiplier;

        // Here's where we will be applying the category multiplier
        double categoryValue = gradeValue * categoryMultiplier;

        // Calculating deductions and net payable
        double commission;
        double transportLevy;
        double netPayable;

        if (grade.equals("REJECT")) {
            // Rejected deliveries have zero value and no deductions.
            commission = 0.0;
            transportLevy = 0.0;
            netPayable = 0.0;
        } else {
            commission = categoryValue * rateOfCommission;
            transportLevy = mass * (double) levyRatePerKg;
            netPayable = categoryValue - commission - transportLevy;
        }

        // Printing Results
        System.out.println("REKOLT Planters’ Cooperative Worked Example");
        System.out.println("--------------------------------------------");

        System.out.println("Member M-0042, 236 kg of Beans(BNS), Quality Score 91");
        System.out.printf("Member: %s%n", memberId);
        System.out.printf("Produce: %s%n", produceCode);
        System.out.printf("Mass: %.2f kg%n", mass);
        System.out.printf("Quality Score: %d%n", qualityScore);

        System.out.printf("%n1. Base value:%n");
        System.out.printf("   236.00 kg x 90.00 = %.2f MUR%n", baseValue);
        System.out.printf("%n2. Grade %s multiplier: %n", grade);
        System.out.printf("   %.2f x %.2f = %.2f MUR%n", baseValue, gradeMultiplier, gradeValue);
        System.out.printf("%n3. Cereal category multiplier: %n");
        System.out.printf("   %.2f x %.2f = %.2f MUR%n", gradeValue, categoryMultiplier, categoryValue);
        System.out.printf("%n4. Commission (5%%): %.2f MUR%n", commission);
        System.out.printf("%n5. Transport levy: %n");
        System.out.printf("   236.00 kg x 2.00 =  %.2f MUR%n", transportLevy);

        System.out.printf("%nNET PAYABLE: %.2f MUR%n", netPayable);
    }
}
