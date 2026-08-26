package mu.rekolt.model;

// This will be a planter registered with the REKOLT cooperative.
public class Member {

    private final String id;
    private final String name;

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
}