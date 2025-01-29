import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import java.util.*;

public class GraphTraversal {

    // обход в глубину dfs
    public static void dfs(Graph graph, String currentNode, Set<String> visited) {
        visited.add(currentNode); // Отметить текущий узел как посещённый
        System.out.println(currentNode); // Выводим текущий узел в консоль

        // Проходим по всем рёбрам исходящим от текущего узла
        graph.getNode(currentNode).edges().forEach(edge -> {
            String neighbor = edge.getOpposite(graph.getNode(currentNode)).getId(); // Получаем соседний узел
            if (!visited.contains(neighbor)) {
                dfs(graph, neighbor, visited);
            }
        });

    }

    // обход в ширину bfs
    public static void bfs(Graph graph, String startNode) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        visited.add(startNode);
        queue.add(startNode);

        while (!queue.isEmpty()) {
            String currentNode = queue.poll();
            System.out.println(currentNode);

            // проход по всем рёбрам исходящим от текущего узла
            graph.getNode(currentNode).edges().forEach(edge -> {
                String neighbor = edge.getOpposite(graph.getNode(currentNode)).getId(); // Получаем соседний узел
                if (!visited.contains(neighbor)) { // Проверяем, был ли узел посещён
                    visited.add(neighbor); // Отмечаем узел как посещённый
                    queue.add(neighbor); // Добавляем узел в очередь
                }
            });
        }
    }
}
