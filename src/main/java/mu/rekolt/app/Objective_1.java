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
    }
}
