public class Order {

    private static String typeOfFood; // Убрали static
    private static int weight;
    private static String startAddress;
    private static String endAddress;
    public static double deliveryCost = 100;

    public Order(){}

    public Order(String typeOfFood, int weight, String startAddress, String endAddress, double deliveryCost) {
        this.typeOfFood = typeOfFood;
        this.weight = weight;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.deliveryCost = deliveryCost;
    }

    public String getTypeOfFood() {
        return typeOfFood;
    }

    public int getWeight() {
        return weight;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public double getDeliveryCost() {
        return deliveryCost;
    }

    public void setTypeOfFood(String typeOfFood) {
        this.typeOfFood = typeOfFood;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public void setDeliveryCost(double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public static String toString(Order o) {
        return "Order{" +
                "typeOfFood='" + typeOfFood + '\'' +
                ", weight=" + weight +
                ", startAddress=" + startAddress +
                ", endAddress=" + endAddress +
                ", deliveryCost=" + deliveryCost +
                '}';
    }
}
