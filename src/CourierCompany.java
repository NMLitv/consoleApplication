import java.text.DecimalFormat;

public class CourierCompany {
    public static double minimumOrderDeliveryCost = 300; // минимальная стоимость доставки
    private static double avarageOrderDeliveryCost;// средняя стоимость заказа
    private static double totalIncome; // выручка
    public static int quantityOrders;

    // итоговая прибыль
    public static double calculateProfit(double avarageOrderDeliveryCost, double wholeWay) {
        double result = 0;
        double totalExpenses = wholeWay * 15;
        totalIncome = quantityOrders * avarageOrderDeliveryCost;
        result = totalIncome - totalExpenses;
        return Double.parseDouble(new DecimalFormat("#.##").format(result));
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

    public static double getAvarageOrderDeliveryCost() {
        return avarageOrderDeliveryCost;
    }

    public static void setAvarageOrderDeliveryCost(double avarageOrderDeliveryCost) {
        CourierCompany.avarageOrderDeliveryCost = avarageOrderDeliveryCost;
    }
}