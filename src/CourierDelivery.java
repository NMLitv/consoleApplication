import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class CourierDelivery {
    public static int completedOrder;
    public static boolean process = true;
    public static double longToOrder;
    public static double longToCustomer;

    public static void main(String[] arg){
        Scanner scanner = new Scanner(System.in);
        List<Order> orders = new ArrayList<>();
        List<Courier> couriers = initializeCouriers();
        Schedule schedule = new Schedule();
        HashMap<Integer, Double> costOfOrders = new HashMap<>();
        Graph graph = initializeGraph();

        showMenu(scanner, orders, couriers, graph, schedule, costOfOrders);

        performDFS(graph);
        System.out.println("\n-----------------------------\n");
        performBFS(graph);

        double totalDistance = calculateTotalDistance(graph);
        System.out.printf("Общая длина маршрута: %.2f%n", totalDistance);
        // spend time in way
        System.out.println("Время в пути: " + Location.TravelTime(totalDistance, couriers.getFirst().getSpeed()) + "ч");
        System.out.printf("Прибыль компании: %s%n",
                CourierCompany.calculateProfit(DeliveryCalculator.calculateAverageCostOrders(costOfOrders), totalDistance, calculateAvarageWeight(orders)));
    }

    private static List<Courier> initializeCouriers(){
        List<Courier> couriers = new ArrayList<>();
        Courier theFirstCourier = new Courier("Иван", "Иванов", "Доставка горячей пищи", 53.2318, 50.1901, 10.00, true, "Самара, улица Советской Армии, 223");
        couriers.add(theFirstCourier);
        Courier theSecondCourier = new Courier("Александр", "Андрович", "Доставка холодной пищи", 53.2167, 50.1927, 10.00, true, "Самара, проспект Карла Маркса, 198");
        couriers.add(theSecondCourier);
        return couriers;
    }

    private static Graph initializeGraph(){
        Graph graph = new SingleGraph("DeliveryGraph");
        graph.addNode("Courier").setAttribute("ui.label;", "Courier");
        return graph;
    }

    private static void showMenu(Scanner scanner, List<Order> orders, List<Courier> couriers, Graph graph, Schedule schedule, HashMap<Integer, Double> costOfOrders) {
        while (process) {
            System.out.println("\nМеню:\n1. Добавить заказ\n2. Вывести список заказов\n3. Выход");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> processOrder(scanner, orders, couriers, graph, schedule, costOfOrders);
                case 2 -> displayOrders(orders);
                case 3 -> {
                    System.out.println("Выход.");
                    return;
                }
                default -> System.out.println("Неверный выбор.");
            }
        }
    }

    private static void processOrder(Scanner scanner, List<Order> orders, List<Courier> couriers, Graph graph, Schedule schedule, HashMap<Integer, Double> costOfOrders) {
        // добавить обработку ввода
        Order order = new Order();
        System.out.println("Введите адрес начала:");
        String startAddress;
        while (true) {
            startAddress = scanner.nextLine().trim();
            if (startAddress.matches(".*[a-zA-Zа-яА-Я]+.*")) { // Проверяем, есть ли буквы
                order.setStartAddress(startAddress);
                break;
            }
            System.out.println("Ошибка! Введите корректный адрес (не число):");
        }

        System.out.println("Введите адрес конца:");
        String endAddress;
        while (true) {
            endAddress = scanner.nextLine().trim();
            if (endAddress.matches(".*[a-zA-Zа-яА-Я]+.*")) { // Проверяем, есть ли буквы
                order.setEndAddress(endAddress);
                break;
            }
            System.out.println("Ошибка! Введите корректный адрес (не число):");
        }

        System.out.println("Введите вес заказа (кг):");
        int weight;
        while (true) {
            if (scanner.hasNextDouble()) { // Проверяем, что введено число
                weight = scanner.nextInt();
                if (weight > 0 && weight <= ConfigLoader.getDoubleProperty("max_weight_order")) {
                    order.setWeight(weight);
                    scanner.nextLine(); // Очистка буфера после nextDouble()
                    break;
                }
            } else {
                scanner.next(); // Пропускаем некорректный ввод
            }
            System.out.println("Ошибка! Введите корректный вес (от 1 до 10 кг):");
        }

        System.out.println("Введите тип еды заказа (Доставка горячей пищи / Доставка холодной пищи):");
        String typeOfFood;
        while (true) {
            typeOfFood = scanner.nextLine().trim();
            if (typeOfFood.equalsIgnoreCase("Доставка горячей пищи") || typeOfFood.equalsIgnoreCase("Доставка холодной пищи")) {
                order.setTypeOfFood(typeOfFood);
                break;
            }
            System.out.println("Ошибка! Введите корректный тип еды (Доставка горячей пищи / Доставка холодной пищи):");
        }


        Courier assignedCourier = (Courier) couriers.stream()
                .filter(c -> c.getCompetence().equalsIgnoreCase(order.getTypeOfFood()))
                .findFirst()
                .orElse(null);

        if (assignedCourier != null){
            schedule.addDelivery(assignedCourier.getCourierName(), order);
        } else {
            System.out.println("К сожалению, заказ не найден.");
            return;
        }

        completedOrder++;
        double cost = DeliveryCalculator.calculateDeliveryCost(order, assignedCourier);
        costOfOrders.put(completedOrder, cost);
        orders.add(order);
        System.out.println("Стоимость доставки: " + cost + " рублей\n");

        // Добавление вершин (узлов)
        graph.addNode("Start" + completedOrder).setAttribute("ui.label", order.getStartAddress());
        graph.addNode("End" + completedOrder).setAttribute("ui.label", order.getEndAddress());

        // Добавление рёбер
        graph.addEdge("CourierToStart" + completedOrder, "Courier", "Start" + completedOrder)
                .setAttribute("weight", longToOrder);
        graph.addEdge("StartToEnd" + completedOrder, "Start" + completedOrder, "End" + completedOrder) // 🔥 исправлено
                .setAttribute("weight", longToCustomer);



        System.out.println("Желаете вернуться к меню?");
        String in = scanner.nextLine();
        process = in.equalsIgnoreCase("да");
    }

    private static void displayOrders(List<Order> orders) {
        orders.forEach(order -> System.out.println(Order.toString(order)));
    }

    private static void performDFS(Graph graph) {
        System.out.println("Обход в глубину (DFS):");
        GraphTraversal.dfs(graph, "Courier", new HashSet<>());
        // out peak
    }

    private static void performBFS(Graph graph) {
        System.out.println("Обход в ширину (BFS):");
        GraphTraversal.bfs(graph, "Courier");
    }

    private static double calculateTotalDistance(Graph graph) {
        Iterator<Edge> edgeIterator = graph.edges().iterator();
        double totalDistance = 0.0;

        while (edgeIterator.hasNext()) {
            Edge edge = edgeIterator.next();
            Object weightAttr = edge.getAttribute("weight");

            if (weightAttr instanceof Double) {
                totalDistance += (Double) weightAttr;
            } else {
                System.out.println("Ошибка: неверный тип или отсутствие атрибута weight у ребра " + edge.getId());
            }
        }
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        return Double.parseDouble(new DecimalFormat("#.##", symbols).format(totalDistance));
    }

    private static double calculateAvarageWeight(List<Order> orders){
        int quantityOrders = 0;
        int summWeight = 0;

        for (Order order: orders){
            summWeight += order.getWeight();
            quantityOrders++;
        }

        double result = (double) summWeight / quantityOrders;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat dF = new DecimalFormat("#.##", symbols);
        return Double.parseDouble(dF.format(result));
    }
}


class DeliveryCalculator {
    public static double calculateDeliveryCost(Order order, Courier courier) {
        double[] startCoordinates = GeocodingService.getCoordinates(order.getStartAddress());
        double[] endCoordinates = GeocodingService.getCoordinates(order.getEndAddress());

        Location courierLocation = new Location(courier.getX(), courier.getY());
        assert startCoordinates != null;
        Location startLocation = new Location(startCoordinates[0], startCoordinates[1]);
        assert endCoordinates != null;
        Location endLocation = new Location(endCoordinates[0], endCoordinates[1]);

        CourierDelivery.longToOrder = courierLocation.distanceTo(startLocation);
        CourierDelivery.longToCustomer = startLocation.distanceTo(endLocation);

        return calculateDeliveryCost(CourierDelivery.longToOrder, CourierDelivery.longToCustomer);
    }

    public static double calculateDeliveryCost(double longToOrder, double longToCustomer) {
        double distance = longToOrder + longToCustomer;
        if (distance >= 3.5) return 450;
        if (distance >= 2.5) return 300;
        if (distance >= 1.5) return 150;
        return 50;
    }

    public static double calculateAverageCostOrders(HashMap<Integer, Double> costOfOrders) {
        double costOrders = 0.0;
        int quantityOrders = 0;
        double avarageCostOrders;

        for (Map.Entry<Integer, Double> entry : costOfOrders.entrySet()) {
            Double value = entry.getValue();
            costOrders += value;
            quantityOrders++;
        }

        CourierCompany.setQuantityOrders(quantityOrders);
        DecimalFormat dF = new DecimalFormat("#.##");
        String avarage = dF.format(costOrders / quantityOrders);
        avarageCostOrders = Double.parseDouble(avarage);
        return avarageCostOrders;
    }
}
