package mu.rekolt.service;

import mu.rekolt.model.Delivery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Stores and manages the deliveries recorded during the season.
public class SeasonService {
    private final List<Delivery> deliveries = new ArrayList<>();

    // Stores kg delivered for each produce type every week
    private final double[][] weeklyGrid = new double[20][4];
    private final String[] produceOrder = {"MZE", "BNS", "POT", "TEA"};

    // Stores the total payment for each member
    private final Map<String, Double> paymentPerMember = new HashMap<>();

    // Groups deliveries by member
    private final Map<String, List<Delivery>> deliveriesPerMember = new HashMap<>();

    // Stores unique member IDs
    private final Set<String> memberIds = new HashSet<>();

    public void recordDelivery(Delivery delivery) {
        deliveries.add(delivery);

        String memberId = delivery.getMember().getId();
        // Add member ID to the set
        memberIds.add(memberId);
        // Update the member's total payment
        double runningTotal = paymentPerMember.getOrDefault(memberId, 0.0);
        paymentPerMember.put(memberId, runningTotal + delivery.netPayable());
        // Create a list if this member has not made a delivery yet
        if (!deliveriesPerMember.containsKey(memberId)) {
            deliveriesPerMember.put(memberId, new ArrayList<>());
        }
        deliveriesPerMember.get(memberId).add(delivery);
        addToWeeklyGrid(delivery);
    }

    private void addToWeeklyGrid(Delivery delivery) {
        for (int col = 0; col < produceOrder.length; col++) {
            if (produceOrder[col].equals(delivery.getProduce().getCode())) {
                weeklyGrid[delivery.getWeek() - 1][col] += delivery.getMass();
            }
        }
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    // Returns the top N deliveries by value, using Delivery's own ordering.
    public List<Delivery> topDeliveriesByValue(int limit) {
        List<Delivery> sorted = new ArrayList<>(deliveries);
        Collections.sort(sorted);
        return sorted.subList(0, Math.min(limit, sorted.size()));
    }

    public double[][] getWeeklyGrid() {
        return weeklyGrid;
    }
    public String[] getProduceOrder() {
        return produceOrder;
    }
    public Map<String, Double> getPaymentPerMember() {
        return paymentPerMember;
    }
    public Map<String, List<Delivery>> getDeliveriesPerMember() {
        return deliveriesPerMember;
    }
    public Set<String> getMemberIds() {
        return memberIds;
    }
}
