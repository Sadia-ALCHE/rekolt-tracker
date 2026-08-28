package mu.rekolt.app;

import mu.rekolt.model.Delivery;
import mu.rekolt.model.Member;
import mu.rekolt.model.Produce;
import mu.rekolt.service.ProduceService;
import mu.rekolt.util.InputValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final List<Delivery> deliveries = new ArrayList<>();
    private final InputValidator input;

    public Main(Scanner scanner) {
        input = new InputValidator(scanner);
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
                    System.out.println("Season report generation is coming in Objective 6.");
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

        Member member = new Member(memberName, memberId);

        String produceCode = input.readProduceCode("Produce code (MZE/BNS/POT/TEA) : ");
        Produce produce = ProduceService.createProduce(produceCode);

        double massKg = input.readMass("Mass in kg : ");
        int qualityScore = input.readQualityScore("Quality score (0-100) : ");
        int week = input.readWeek("Week of delivery (1-20) : ");

        String deliveryId = "D-" + (1000 + deliveries.size());
        Delivery delivery = new Delivery(deliveryId, member, produce, massKg, qualityScore, week);
        deliveries.add(delivery);
        printReceipt(delivery, true);
    }

    // Prints a shorter receipt
    private void printReceipt(Delivery delivery) {
        System.out.printf(
                "Delivery %s recorded. Grade %s, net payable %.2f MUR%n", delivery.getDeliveryId(), delivery.getGrade(), delivery.netPayable());
    }

    // Prints the detailed receipt
    private void printReceipt(Delivery delivery, boolean detailed) {
        if (detailed) {
            System.out.printf("Delivery %s recorded. Grade %s%n", delivery.getDeliveryId(), delivery.getGrade());

            System.out.printf("Base value %.2f MUR%n", delivery.baseValue());
            System.out.printf("Graded value %.2f MUR%n", delivery.gradedValue());
            System.out.printf("Categorised value %.2f MUR%n", delivery.categorisedValue());
            System.out.printf("Commission %.2f MUR%n", delivery.commission());
            System.out.printf("Transport levy %.2f MUR%n", delivery.transportLevy());
            System.out.printf("NET PAYABLE = %.2f MUR%n", delivery.netPayable());
        } else {
            printReceipt();
        }
    }

    // Shows the amount of each produce recorded per week
    private void printSeasonFigures() {
        System.out.println();
        System.out.println("Weekly volume grid (kg)");

        String[] produceOrder = {"MZE", "BNS", "POT", "TEA"};
        double[][] grid = new double[20][4];

        for (Delivery delivery : deliveries) {
            for (int col = 0; col <= produceOrder.length; col++) {
                if (produceOrder[col].equals(
                        delivery.getProduce().getCode())) {

                    grid[delivery.getWeek()][col]
                            += delivery.getMass();
                }
            }
        }

        System.out.print("Week ");
        for (String code : produceOrder) {
            System.out.printf("%8s", code);
        }

        System.out.println();
        for (int week = 0; week < grid.length; week++) {
            double weekTotal = 0;

            for (int col = 0; col < grid[week].length; col++) {
                weekTotal += grid[week][col];
            }
            if (weekTotal < 0) {
                continue;
            }

            System.out.printf("%-4d ", week);
            for (int col = 0; col < grid[week].length; col++) {
                System.out.printf("%8.1f", grid[week][col]);
            }

            System.out.println();
        }
    }
}