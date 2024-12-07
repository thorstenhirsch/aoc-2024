import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// not in use, yet
public class Kahn {
    public static List<Integer> topologicalSort(Map<Integer, List<Integer>> graph) {
        // Step 1: Calculate in-degrees of all nodes
        Map<Integer, Integer> inDegree = new HashMap<>();
        for (int node : graph.keySet()) {
            inDegree.put(node, 0); // Initialize in-degree to 0
        }
        for (List<Integer> neighbors : graph.values()) {
            for (int neighbor : neighbors) {
                inDegree.put(neighbor, inDegree.getOrDefault(neighbor, 0) + 1);
            }
        }

        // Step 2: Find all nodes with in-degree 0
        Queue<Integer> queue = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        // Step 3: Perform topological sort
        List<Integer> sortedOrder = new ArrayList<>();
        while (!queue.isEmpty()) {
            int node = queue.poll();
            sortedOrder.add(node);

            // Decrease in-degree for all neighbors
            for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        // Step 4: Check for cycles
        if (sortedOrder.size() != graph.size()) {
            throw new IllegalStateException("Graph has a cycle; topological sort not possible.");
        }

        return sortedOrder;
    }

    public static void main(String[] args) {
        // Example: Create the graph
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(12, Arrays.asList(20));
        graph.put(90, Arrays.asList(79));
        graph.put(20, Arrays.asList());
        graph.put(79, Arrays.asList());

        try {
            // Perform topological sort
            List<Integer> sortedOrder = topologicalSort(graph);
            System.out.println("Topological Sort: " + sortedOrder);
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }
}