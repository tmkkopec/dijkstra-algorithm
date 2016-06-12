package info.tkopec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;


/**
 * Created by tkopec on 25/05/16.
 */
public class ParserXml {
    private static final Logger logger = LogManager.getLogger(ParserDat.class.getName());

    LinkedList<LinkedList<Vertex>> parse(String fileName) {
        LinkedList<LinkedList<Vertex>> graph = new LinkedList<>();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(fileName));
            document.getDocumentElement().normalize();

            NodeList vertices = document.getElementsByTagName("vertex");
            for (int i = 0; i < vertices.getLength(); i++) {
                LinkedList<Vertex> vList = new LinkedList<>();

                // get main vertex and add it to empty list
                Element eNode = (Element) vertices.item(i);
                logger.info("Adding main vertex " + eNode.getAttribute("name"));
                vList.add(new Vertex((eNode.getAttribute("name"))));

                // get all neighbours and append it to main vertex
                NodeList neighbours = eNode.getElementsByTagName("neighbour");
                for (int j = 0; j < neighbours.getLength(); j++) {
                    Element neighbour = (Element) neighbours.item(j);

                    // get all weights
                    NodeList weights = neighbour.getElementsByTagName("weight");
                    LinkedList<Double> wList = new LinkedList<>();
                    for (int k = 0; k < weights.getLength(); k++)
                        wList.add(Double.parseDouble(weights.item(k).getTextContent()));

                    vList.add(new Vertex(neighbour.getAttribute("name"),
                            wList.get(0),
                            wList.get(1),
                            wList.get(2),
                            wList.get(3)));
                    logger.info("Appending neighbour vertex " + vList.peekLast());
                }

                graph.add(vList);
            }

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return graph;
    }

}
