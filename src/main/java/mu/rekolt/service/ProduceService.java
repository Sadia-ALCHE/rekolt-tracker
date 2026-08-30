package mu.rekolt.service;

import mu.rekolt.model.CashCropProduce;
import mu.rekolt.model.CerealProduce;
import mu.rekolt.model.PerishableProduce;
import mu.rekolt.model.Produce;

// The fixed price used to build the correct Produce for a given code via a switch statement.
public final class ProduceService {
    private static final String[] CODES = {"MZE", "BNS", "POT", "TEA"};
    private static final double[] BASE_PRICES = {30.0, 90.0, 45.0, 25.0};

    private ProduceService() {
        // utility class which has not been instantiated
    }
    public static boolean isValidCode(String rawCode) {
        if (rawCode == null) {
            return false;
        }
        String code = rawCode.trim().toUpperCase();
        for (String known : CODES) {
            if (known.equals(code)) {
                return true;
            }
        }
        return false;
    }

    private static double lookupBasePrice(String code) {
        for (int i = 0; i < CODES.length; i++) {
            if (CODES[i].equals(code)) {
                return BASE_PRICES[i];
            }
        }
        throw new IllegalArgumentException("Unknown produce code: " + code);
    }

    // Builds a Produce for a given code using a switch
    public static Produce createProduce(String rawCode) {
        if (!isValidCode(rawCode)) {
            throw new IllegalArgumentException("Unknown produce code: " + rawCode);
        }
        String code = rawCode.trim().toUpperCase();
        double basePrice = lookupBasePrice(code);

        switch (code) {
            case "MZE":
                return new CerealProduce(code, "Maize", basePrice);
            case "BNS":
                return new CerealProduce(code, "Beans", basePrice);
            case "POT":
                return new PerishableProduce(code, "Potatoes", basePrice);
            case "TEA":
                return new CashCropProduce(code, "Green tea leaf", basePrice);
            default:
                // Invalid codes are already handled by isValidCode.
                throw new IllegalArgumentException("Unknown produce code: " + code);
        }
    }
}