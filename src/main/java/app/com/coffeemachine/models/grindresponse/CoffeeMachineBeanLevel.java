package app.com.coffeemachine.models.grindresponse;

public class CoffeeMachineBeanLevel {

    private double beanLevel;
    private String unit;

    public CoffeeMachineBeanLevel() {
        this.unit = "Grams";
    }

    public String getUnit() {
        return this.unit;
    }

    public void setBeanLevel(double b) {
        this.beanLevel = b;
    }
    public double getBeanLevel() {
        return this.beanLevel;
    }


}
