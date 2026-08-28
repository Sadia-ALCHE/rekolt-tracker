package mu.rekolt.app;

import mu.rekolt.model.Delivery;
import mu.rekolt.model.Member;
import mu.rekolt.model.Produce;
import mu.rekolt.service.ProduceService;
import mu.rekolt.service.SeasonService;
import mu.rekolt.util.InputValidator;

import java.util.Scanner;

public class Main {
    private final SeasonService season = new SeasonService();
    private final InputValidator input;

    public Main(Scanner scanner) {
        input = new InputValidator(scanner);

        // A dozen sample deliveries.
        Member pamela = new Member("M-0042", "Pamela Lion");
        Member kimu = new Member("M-0117", "Babatunde Kimunila");
        Member aidas = new Member("M-0088", "Aidas Utamilah");
        Member siya = new Member("M-0021", "Siya Hammed");

        season.recordDelivery(new Delivery("D-1001", pamela, ProduceService.createProduce("BNS"), 236.0, 91, 3));
        season.recordDelivery(new Delivery("D-1002", kimu, ProduceService.createProduce("MZE"), 412.5, 78, 1));
        season.recordDelivery(new Delivery("D-1003", aidas, ProduceService.createProduce("POT"), 150.0, 55, 2));
        season.recordDelivery(new Delivery("D-1004", siya, ProduceService.createProduce("MZE"), 300.0, 60, 5));
        season.recordDelivery(new Delivery("D-1005", pamela, ProduceService.createProduce("TEA"), 88.3, 96, 1));
        season.recordDelivery(new Delivery("D-1006", kimu, ProduceService.createProduce("BNS"), 390.5, 82, 2));
        season.recordDelivery(new Delivery("D-1007", aidas, ProduceService.createProduce("MZE"), 180.0, 40, 1)); // REJECT
        season.recordDelivery(new Delivery("D-1008", siya, ProduceService.createProduce("POT"), 120.5, 91, 5));
        season.recordDelivery(new Delivery("D-1009", pamela, ProduceService.createProduce("POT"), 95.0, 68, 4));
        season.recordDelivery(new Delivery("D-1010", kimu, ProduceService.createProduce("TEA"), 60.0, 88, 3));
        season.recordDelivery(new Delivery("D-1011", aidas, ProduceService.createProduce("BNS"), 210.0, 73, 4));
        season.recordDelivery(new Delivery("D-1012", siya, ProduceService.createProduce("TEA"), 45.0, 84, 2));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Main app = new Main(scanner);
        app.run();
        scanner.close();
    }

    public void run() {
        System.out.println("REKOLT Planters' Cooperative Produce Tracker - Season 2026");

        boolean running = true;
        while (running) {
            printMenu();
            int option = input.readMenuOption("Choose an option: ", 1, 4);
            switch (option) {
                case 1:
                    recordDelivery();
                    break;
                case 2:
                    printSeasonFigures();
                    break;
                case 3:
                    System.out.println("Season report generation loading");
                    break;
                case 4:
                    System.out.println("Goodbye.");
                    running = false;
                    break;
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("1. Record a delivery");
        System.out.println("2. Season figures on screen");
        System.out.println("3. Generate the season report");
        System.out.println("4. Exit");
    }

    // Records the information entered for one delivery
    private void recordDelivery() {
        String memberId = input.readMemberId("Member identifier : ");
        String memberName = input.readNonBlankText("Member name : ", "Member name");

        Member member = new Member(memberId, memberName);

        String produceCode = input.readProduceCode("Produce code (MZE/BNS/POT/TEA) : ");
        Produce produce = ProduceService.createProduce(produceCode);

        double mass = input.readMass("Mass in kg : ");
        int qualityScore = input.readQualityScore("Quality score (0-100) : ");
        int week = input.readWeek("Week of delivery (1-20) : ");
        String deliveryId = "D-" + (1000 + season.getDeliveries().size() + 1);

        Delivery delivery = new Delivery(deliveryId, member, produce, mass, qualityScore, week);
        season.recordDelivery(delivery);
        printReceipt(delivery, true);
    }

    // Prints a shorter receipt
    private void printReceipt(Delivery delivery) {
        System.out.printf(
                "Delivery %s recorded. Grade %s, net payable %.2f MUR%n", delivery.getDeliveryId(), delivery.getGrade(), delivery.netPayable());
    }

    // Prints the detailed receipt
    private void printReceipt(Delivery delivery, boolean detailed) {
        if (!detailed) {
            printReceipt(delivery);
            return;
        }
            System.out.printf("Delivery %s recorded. Grade %s%n", delivery.getDeliveryId(), delivery.getGrade());
            System.out.printf("Base value %.2f MUR%n", delivery.baseValue());
            System.out.printf("Graded value %.2f MUR%n", delivery.gradedValue());
            System.out.printf("Categorised value %.2f MUR%n", delivery.categorisedValue());
            System.out.printf("Commission %.2f MUR%n", delivery.commission());
            System.out.printf("Transport levy %.2f MUR%n", delivery.transportLevy());
            System.out.printf("NET PAYABLE = %.2f MUR%n", delivery.netPayable());
    }

    // Shows the amount of each produce recorded per week
    private void printSeasonFigures() {
        System.out.println();
        System.out.println("Weekly volume grid (kg)");

        String[] produceOrder = season.getProduceOrder();
        double[][] grid = season.getWeeklyGrid();

        System.out.print("Week ");
        for (String code : produceOrder) {
            System.out.printf("%8s", code);
        }
        System.out.println();

        for (int week = 0; week < grid.length; week++) {
            double weekTotal = 0;
            for (int col = 0; col < grid[week].length; col++) { weekTotal += grid[week][col];}
            if (weekTotal == 0) {
                continue;
            }

            System.out.printf("%-4d ", week + 1);
            for (int col = 0; col < grid[week].length; col++) {
                System.out.printf("%8.1f", grid[week][col]);
            }

            System.out.println();
        }
    }
}