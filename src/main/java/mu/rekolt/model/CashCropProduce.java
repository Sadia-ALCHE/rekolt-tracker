package mu.rekolt.model;

public class CashCropProduce extends Produce{
    public cashCropProduce(String code, String name, double basePricePerKg) {
        super(code, name, basePricePerKg);
    }
    @Override
    public double getCategoryMultiplier() {
        return 1.10;
    }
    @Override
    public String getCategoryName() {
        return "Cash crop";
    }
}
