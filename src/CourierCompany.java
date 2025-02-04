import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CourierCompany {
     // минимальная стоимость доставки
    private static double avarageOrderDeliveryCost;// средняя стоимость заказа
    private static double totalIncome; // выручка
    private static int quantityOrders;

    // итоговая прибыль
    public static double calculateProfit(double avarageOrderDeliveryCost, double wholeWay, double avarageWeight) {
        double totalExpenses;
        if (avarageWeight > ConfigLoader.getDoubleProperty("avarage_weight_orders")) {
            totalExpenses = wholeWay * ConfigLoader.getDoubleProperty("max_flow_coefficient");
        } else {
            totalExpenses = wholeWay * ConfigLoader.getDoubleProperty("min_flow_coefficient");
        }
        totalIncome = quantityOrders * avarageOrderDeliveryCost;
        double result = totalIncome - totalExpenses;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        return Double.parseDouble(new DecimalFormat("#.##", symbols).format(result));
    }

    public static int getQuantityOrders() {
        return quantityOrders;
    }

    public static void setQuantityOrders(int quantityOrders) {
        CourierCompany.quantityOrders = quantityOrders;
    }

    public static double getTotalIncome() {
        return totalIncome;
    }

    public static void setTotalIcome(double totalIcome) {
        CourierCompany.totalIncome = totalIcome;
    }

    public double getAvarageOrderDeliveryCost() {
        return avarageOrderDeliveryCost;
    }

    public void setAvarageOrderDeliveryCost(double avarageOrderDeliveryCost) {
        CourierCompany.avarageOrderDeliveryCost = avarageOrderDeliveryCost;
    }
}