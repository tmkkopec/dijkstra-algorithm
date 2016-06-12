package info.tkopec;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Created by tkopec on 14/05/16.
 */
class Results {
    private Map<String, Double> distances;
    private Map<String, String> predecessors;
    private String source;

    Results(Map<String, Double> distances, Map<String, String> predecessors, String source) {
        this.distances = distances;
        this.predecessors = predecessors;
        this.source = source;
    }

    void saveToFile(Long elapsedTime) {
        FileWriter fw = null;

        try {
            fw = new FileWriter("results.dat");

            fw.write("Dijkstra's algorithm\nShortest paths from source vertex "
                    + source + " to destination vertex:\n");

            for (Map.Entry<String, Double> entry: distances.entrySet()) {
                if (!entry.getKey().equals(source)) {
                    fw.write(entry.getKey() + ":\n");

                    String current = entry.getKey();
                    while (current != null && !"UNDEFINED".equals(current) && !current.equals(source)) {
                        fw.write(current + "->");

                        current = predecessors.get(current);
                    }

                    if ("UNDEFINED".equals(current))
                        fw.write("NODE IS DISCONNECTED FROM SOURCE - PATH DOES NOT EXIST\n");
                    else
                        fw.write(source + "\nTOTAL LENGTH: " +
                            distances.get(entry.getKey()) + "\n");
                }
            }

            fw.write("TOTAL TIME (in milliseconds): " + elapsedTime);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
