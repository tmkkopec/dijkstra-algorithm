package info.tkopec;

import java.util.LinkedList;

/**
 * Created by tkopec on 04/05/16.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("\nInvalid number of arguments!" +
                    "\nNeed:\n\tArgument1: name of resource file\n" +
                    "\tArgument2: source vertex\n\n");
            return;
        }
        ParserXml parser = new ParserXml();
        Dijkstra dijkstra = new Dijkstra();
        Results results;

        LinkedList<LinkedList<Vertex>> adjacencyList = parser.parse(args[0]);

        Long start = System.currentTimeMillis();
        results = dijkstra.compute(adjacencyList, args[1]);
        Long elapsedTime = System.currentTimeMillis() - start;

        results.saveToFile(elapsedTime);
    }
}
