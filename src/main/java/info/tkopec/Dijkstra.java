package info.tkopec;

import java.util.*;

/**
 * Created by tkopec on 14/05/16.
 */
class Dijkstra {
    private Map<String, Double> distances = new HashMap<>();
    private Map<String, String> predecessors = new HashMap<>();
    private Queue<String> priorityQueue;

    Results compute(List<LinkedList<Vertex>> graph, String source) {
        String u;
        List<Vertex> current;
        double alternate;

        initializeSingleSource(graph, source);

        // main loop
        while (!priorityQueue.isEmpty()) {
            // remove and return best vertex
            u = priorityQueue.remove();

            // find vertex u in graph
            current = getNeighboursList(graph, u);

            // for each neighbour v of u
            for (Vertex v: current) {
                alternate = distances.get(u) + getWeight(v);

                if(alternate < distances.get(v.id)) {
                    distances.put(v.id, alternate);
                    predecessors.put(v.id, u);

                    // remove outdated element
                    removeFromQueue(priorityQueue, v.id);
                    // and insert updated node
                    priorityQueue.add(v.id);
                }
            }
        }

        return new Results(distances, predecessors, source);
    }

    private void initializeSingleSource(List<LinkedList<Vertex>> graph,
                                        String source) {
        // initialize ascending comparator of distances
        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String x, String y) {
                Double o1 = distances.get(x);
                Double o2 = distances.get(y);

                if (o1 == null || o2 == null)
                    return 1;

                if(o1 - o2 > 0)
                    return 1;

                if(o1 - o2 < 0)
                    return -1;

                return 0;
            }
        };

        /*
            initialize priority queue with size == number of vertices
            and order elements by distances from source vertex
        */
        priorityQueue = new PriorityQueue<>(graph.size(), comparator);

        String firstVertex;

        for (LinkedList<Vertex> vertices: graph) {
            // retrieve but DO NOT remove first element from list
            firstVertex = vertices.peekFirst().id;

            // initialize data structures with default values
            if (!firstVertex.equals(source)) {
                distances.put(firstVertex, Double.MAX_VALUE);
                predecessors.put(firstVertex, "UNDEFINED");
            }

            // add every vertex to priority queue
            priorityQueue.add(firstVertex);
        }

        // set distance from source as 0
        distances.put(source, 0d);
    }

    private List<Vertex> getNeighboursList(List<LinkedList<Vertex>> graph, String u) {
        Iterator<LinkedList<Vertex>> iterator = graph.iterator();
        LinkedList<Vertex> neighboursList;

        while (iterator.hasNext()) {
            neighboursList = iterator.next();
            if (neighboursList.peekFirst().id.equals(u))
                return neighboursList.subList(1, neighboursList.size());
        }

        return new LinkedList<>();
    }

    private void removeFromQueue(Queue<String> priorityQueue, String id) {
        Iterator<String> iterator = priorityQueue.iterator();

        while(iterator.hasNext()) {
            if (iterator.next().equals(id)) {
                iterator.remove();
                return;
            }
        }
    }

    private double getWeight(Vertex vertex) {
        return vertex.attr1 * vertex.attr2 * vertex.attr3 * vertex.attr4;
    }
}
