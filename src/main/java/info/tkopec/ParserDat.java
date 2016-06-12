package info.tkopec;

import java.io.*;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by tkopec on 04/05/16.
 * Parser for files with format: vertex,neighbours=[{vertex, {attr1, attr2, attr3, attr4}}; ...]
 * For example: 1,[{2,{2,2,2,2}};{5,{5,5,5,5}}]
 *              1,[{4,{1,1,1,1}};{3,{1,1,1,1}] etc.
 */
class ParserDat {
    private static final Logger logger = LogManager.getLogger(ParserDat.class.getName());

    LinkedList<LinkedList<Vertex>> parse(String fileName) {
        BufferedReader br = null;
        String line;
        int lineCounter = 1;
        LinkedList<LinkedList<Vertex>> graph = new LinkedList<LinkedList<Vertex>>();

        try {
            br = new BufferedReader(new FileReader(fileName));

            while((line = br.readLine()) != null) {
                // skip first line
                if(lineCounter == 1) {
                    lineCounter++;
                    continue;
                }

                logger.info("Parsing line number " + lineCounter++);

                /* split line into mainVertex and its neighbours
                    for instance:
                        -line before: 1,[{2,{2,2,2,2}};{5,{5,5,5,5}}]
                        -line after: commaSplit[0] = 1
                            commaSplit[1] = {2,{2,2,2,2}};{5,{5,5,5,5}}
                 */
                String[] commaSplit = new String[2];
                commaSplit[0] = line.substring(0, line.indexOf(','));
                commaSplit[1] = line.substring(line.indexOf(',') + 2, line.length() - 1);

                Vertex mainVertex = new Vertex(commaSplit[0]);

                // list for mainVertex and its neighbours
                LinkedList<Vertex> vertexList = new LinkedList<Vertex>();
                logger.info("Adding main vertex to list. VERTEX_ID: " + commaSplit[0]);
                vertexList.add(mainVertex);

                /*  split neighbours
                    for instance:
                        -before: {2,{2,2,2,2}};{5,{5,5,5,5}}
                        -after: neighbours[0] = {2,{2,2,2,2}}
                            neighbours[1] = {5,{5,5,5,5}}
                 */
                String[] neighbours = commaSplit[1].split(";");

                // for each tuple in neighbours array
                for (String s: neighbours) {
                    /*  separate vertexID from attributes
                        for instance:
                            -before: {5,{5,5,5,5}}
                            -after: vertexData[0] = 5
                                vertexData[1] = 5,5,5,5
                     */
                    String[] vertexData = new String[2];
                    vertexData[0] = s.substring(1, s.indexOf(','));
                    vertexData[1] = s.substring(s.indexOf(',') + 2, s.length() - 2);

                    /*  finally last split - get attributes
                        for instance:
                            -before: 1,2,3,4
                            -after: attributes[0] = 1
                                attributes[1] = 2 etc.
                     */
                    String[] attributes = vertexData[1].split(",");

                    // construct vertex using previously obtained vertexID and attributes
                    Vertex subVertex = new Vertex(vertexData[0],
                            new Double(attributes[0]),
                            new Double(attributes[1]),
                            new Double(attributes[2]),
                            new Double(attributes[3]));

                    // append new vertex to list
                    logger.info("Appending neighbour " +
                            subVertex.toString() +
                            " to VERTEX_ID[" + commaSplit[0] +"]");
                    vertexList.add(subVertex);
                }

                // append to graph vertex and its neighbours
                graph.add(vertexList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return graph;
    }
}
