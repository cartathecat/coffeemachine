package app.com.coffeemachine.models.grindresponse;

public class CoffeeMachineWaterLevel {

    private int waterLevel;
    private String unit;

    public CoffeeMachineWaterLevel() {
        this.unit = "Litres";
    }

    public String getUnit() {
        return this.unit;
    }

    public void setWaterLevel(int w) {
        this.waterLevel = w;
    }
    public int getWaterLevel() {
        return this.waterLevel;
    }
}
