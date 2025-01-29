import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.text.DecimalFormat;
import java.util.*;
import java.util.Map;


public class CourierDelivery {
    public static int completedOreder; // Количество выполненных заказов

    public static double calculateDeliveryCost(double longToOrder, double longToCustomer) {
        if ((longToOrder + longToCustomer) - 2.5 >= 1) {
            CourierCompany.minimumOrderDeliveryCost += 50;
        } else if ((longToOrder + longToCustomer) - 2.5 >= 2) {
            CourierCompany.minimumOrderDeliveryCost += 100;
        } else if ((longToOrder + longToCustomer) - 2.5 >= 3) {
            CourierCompany.minimumOrderDeliveryCost += 150;
        } else if ((longToOrder + longToCustomer) - 2.5 >= 4) {
            CourierCompany.minimumOrderDeliveryCost += 250;
        }
        return CourierCompany.minimumOrderDeliveryCost;
    }

    public static Double calculateAvarageCostOrders(HashMap<Integer, Double> costOfOrders) {
        double costOrders = 0.0;
        int quantityOrders = 0;
        double avarageCostOrders;

        for (Map.Entry<Integer, Double> entry : costOfOrders.entrySet()) {
            Double value = entry.getValue();
            costOrders += value;
            quantityOrders++;
        }

        DecimalFormat dF = new DecimalFormat("#.##");
        String avarage = dF.format(costOrders / quantityOrders);
        avarageCostOrders = Double.parseDouble(avarage);
        return avarageCostOrders;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Order> orders = new ArrayList<>();
        List<Сourier> couriers = new ArrayList<>(); // Добавим список курьеров
        Schedule schedule = new Schedule();
        HashMap<Integer, Double> costOfOrders = new HashMap<>();
        Graph graph = new SingleGraph("DeliveryGraph");


        Сourier theFirstCourier = new Сourier("Иван", "Иванов", "Доставка горячей пищи",
                53.2318, 50.1901, 10.00, true, "Самара, улица Советской Армии, 223");

        Сourier theSecondCourier = new Сourier("Александр", "Андрович", "Доставка холодной пищи",
                53.2167, 50.1927, 10.00, true, "Самара, проспект Карла Маркса, 198");

        couriers.add(theFirstCourier);
        couriers.add(theSecondCourier);

        // add peak
        graph.addNode("Courier").setAttribute("ui.label", "Courier");

        boolean process = true;
        while (process) {
            System.out.println("\nМеню:");
            System.out.println("1. Добавить заказ");
            System.out.println("2. Вывести список заказов");
            System.out.println("3. Выход");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Order order = new Order();
                    System.out.print("Введите координаты адреса начала:");
                    String start = scanner.nextLine();
                    order.setStartAddress(start);

                    System.out.print("Введите координаты адреса конца:");
                    String end = scanner.nextLine();
                    order.setEndAddress(end);

                    System.out.println("Введите вес заказа:");
                    order.setWeight(scanner.nextInt());
                    scanner.nextLine(); // Очистка буфера после nextInt()

                    System.out.println("Введите тип еды заказа:");
                    order.setTypeOfFood(scanner.nextLine());

                    if (order.getTypeOfFood() != null && !order.getTypeOfFood().isEmpty() &&
                            order.getTypeOfFood().equalsIgnoreCase(theFirstCourier.getCompetence())) {
                        schedule.addDelivery(theFirstCourier.getCourierName(), order);
                    } else if (order.getTypeOfFood() != null && !order.getTypeOfFood().isEmpty() &&
                            order.getTypeOfFood().equalsIgnoreCase(theSecondCourier.getCompetence())) {
                        schedule.addDelivery(theSecondCourier.getCourierName(), order);
                    } else {
                        System.out.println("К сожалению, в ближайшее время не сможем доставить данный заказ. Попробуйте оформить заказ позднее.");
                    }
                    completedOreder++;

                    // получение координат по введённым адресам пользователя
                    double[] startCoordinates = GeocodingService.getCoordinates(start);
                    double[] endCoordinates = GeocodingService.getCoordinates(end);

                    Location courierLocation = new Location(theFirstCourier.getX(), theFirstCourier.getY());
                    Location startLocation = new Location(startCoordinates[0], startCoordinates[1]);
                    Location endLocation = new Location(endCoordinates[0], endCoordinates[1]);

                    // получение расстояния между двумя местоположениями
                    double longToOrder = courierLocation.distanceTo(startLocation);
                    double longToСustomer = startLocation.distanceTo(endLocation);
                    double way = longToСustomer + longToOrder;

                    costOfOrders.put(completedOreder, calculateDeliveryCost(longToOrder, longToСustomer));

                    double timeToStore = courierLocation.TravelTime(longToOrder, theFirstCourier.getSpeed());
                    double timeToCustomer = courierLocation.TravelTime(longToСustomer, theFirstCourier.getSpeed());
                    double time = timeToCustomer + timeToStore;

                    // add peak
                    graph.addNode("Start" + (completedOreder + 1)).setAttribute("ui.label", start);
                    graph.addNode("End" + (completedOreder + 1)).setAttribute("ui.label", end);

                    graph.addEdge("CourierToStart" + (completedOreder + 1), "Courier", "Start" + (completedOreder + 1))
                            .setAttribute("weight", longToOrder);
                    graph.addEdge("StartToEnd" + (completedOreder + 1), "Start" + (completedOreder + 1), "End" + (completedOreder + 1))
                            .setAttribute("weight", longToСustomer);


                    DecimalFormat dF = new DecimalFormat("#.##");
                    System.out.println("Расстояние, которое пройдёт курьер:" + dF.format(way) + " км" + "\n" + "Потраченное время:" + time + " ч");

                    System.out.println("Стоимость доставки: " + calculateDeliveryCost(longToOrder, longToСustomer) + "рублей" + "\n");

                    orders.add(order);
                    System.out.println("Желаете вернуться к меню?");
                    String in = scanner.nextLine();
                    if (in.equalsIgnoreCase("да")) {
                        process = true;
                    } else {
                        process = false;
                    }
                    break;

                case 2:
                    for (Order o : orders) {
                        String string = Order.toString(o);
                        System.out.println(string);
                    }
                    process = false;
                    break;
                case 3:
                    System.out.println("Выход.");
                    return;
                default:
                    System.out.println("Неверный выбор.");
                    process = false;
            }
        }


        // Запуск обхода в глубину (DFS)
        System.out.println("Обход в глубину (DFS):");
        Set<String> visitedDFS = new HashSet<>();
        GraphTraversal.dfs(graph, "Courier", visitedDFS);

        // Разделитель
        System.out.println("\n-----------------------------\n");

        // Запуск обхода в ширину (BFS)
        System.out.println("Обход в ширину (BFS):");
        GraphTraversal.bfs(graph, "Courier");

        CourierCompany.quantityOrders = costOfOrders.size();

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
        System.out.println("Общая длина маршрута: " + new DecimalFormat("#.##").format(totalDistance));

        System.out.printf("Прибыль компании:%s%n",
                CourierCompany.calculateProfit(calculateAvarageCostOrders(costOfOrders), totalDistance));
    }
}
