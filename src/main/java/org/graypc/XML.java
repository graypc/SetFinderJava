package org.graypc;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by graypc on 12/1/16.
 */
public class XML
{
    public XML()
    {}

    public void writeXML(List<List<Card>> cards)
    {
        try
        {
            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder =
                    factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // root element
            Element rootElement = doc.createElement("cards");
            doc.appendChild(rootElement);

           for (int rowIndex = 0; rowIndex < cards.size(); ++rowIndex)
           {
               List<Card> row = cards.get(rowIndex);
               Element rowElement = doc.createElement("row");
               rootElement.appendChild(rowElement);

               for (int cardIndex = 0; cardIndex < row.size(); ++cardIndex)
               {
                   Card card = row.get(cardIndex);
                   Element cardElement = doc.createElement("card");
                   cardElement.setAttribute("parameters", card.getParametersCode());
                   rowElement.appendChild(cardElement);
               }
           }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("Cards.xml"));
            transformer.transform(source, result);
        }
        catch (Exception ex)
        {}
    }

    public List<List<Card>> readXML()
    {
        List<List<Card>> cards = new ArrayList<List<Card>>();
        try
        {
            File cardsFile = new File("Cards.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(cardsFile);
            doc.getDocumentElement().normalize();
            NodeList rowList = doc.getElementsByTagName("row");

            for (int i = 0; i < rowList.getLength(); ++i)
            {
                Node rowNode = rowList.item(i);

                if (rowNode.getNodeType() != Node.ELEMENT_NODE)
                    continue;

                List<Card> row = new ArrayList<Card>();

                Element rowElement = (Element)rowNode;
                NodeList cardList = rowElement.getChildNodes();

                for (int j = 0; j < cardList.getLength(); ++j)
                {
                    Node cardNode = cardList.item(j);

                    if (cardNode.getNodeType() != Node.ELEMENT_NODE)
                        continue;

                    Element cardElement = (Element)cardNode;
                    String params = cardElement.getAttribute("parameters");

                    Card card = new Card(params);
                    row.add(card);
                    //System.out.format("%s\n", params);
                }
                cards.add(row);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
        return cards;
    }
}
