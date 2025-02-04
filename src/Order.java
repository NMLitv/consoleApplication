public class Order {
    // решить со static
    private static String typeOfFood; // Убрали static
    private static int weight;
    private static String startAddress;
    private static String endAddress;


    public Order(){}

    public Order(String typeOfFood, int weight, String startAddress, String endAddress) {
        this.typeOfFood = typeOfFood;
        this.weight = weight;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
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

    public static String toString(Order order) {
        return "Order{" +
                "typeOfFood='" + typeOfFood + '\'' +
                ", weight=" + weight +
                ", startAddress='" + startAddress + '\'' +
                ", endAddress='" + endAddress + '\'' +
                '}';
    }
}
