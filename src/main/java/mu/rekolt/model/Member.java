package mu.rekolt.model;

import mu.rekolt.service.Payable;
import mu.rekolt.service.Reportable;

import java.util.ArrayList;
import java.util.List;

// This will be a planter registered with the REKOLT cooperative.
public class Member implements Payable, Reportable {
    private final String id;
    private final String name;
    private final List<Delivery> deliveries = new ArrayList<>();

    public Member(String id, String name) {
        // Id must look like M-0042: letter M, hyphen, 4 digits.
        if (id == null || !id.matches("M-\\d{4}")) {
            throw new IllegalArgumentException("Member id must match M-#### (e.g. M-0042).");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Member name is required.");
        }
        this.id = id;
        this.name = name;
    }

    //Called by SeasonService whenever this member takes a delivery
    public void addDelivery(Delivery delivery) {
        deliveries.add(delivery);
    }
    public List<Delivery> getDeliveries() {
        return deliveries;
    }
    @Override
    public double netPayable() {
        double total = 0.0;
        for (Delivery delivery : deliveries) {
            total += delivery.netPayable();
        }
        return total;
    }

    @Override
    public String summaryLine() {
        return String.format("%s %-20s %,10.2f MUR", id, name, netPayable());
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return id + " " + name;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Member)) {
            return false;
        }
        return id.equals(((Member) other).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}