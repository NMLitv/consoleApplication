import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schedule {

    private Map<String, List<Order>> schedule;

    public Schedule() {
        schedule = new HashMap<>();
    }

    public void addDelivery(String courierName, Order delivery) {
        if (!schedule.containsKey(courierName)) {
            schedule.put(courierName, new ArrayList<>());
        }
        schedule.get(courierName).add(delivery);
    }

    public List<Order> getCourierSchedule(String courierName) {
        return schedule.getOrDefault(courierName, new ArrayList<>());
    }

    public int getOrdersSize(String nameCourier){
        return schedule.get(nameCourier).size();
    }
}