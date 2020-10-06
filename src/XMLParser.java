import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.ArrayList;

class XMLParser {

    public static String xmlParser(String localesIngresados, ArrayList<Rectangulo> localesExistentes) throws SeCruzanException, TransformerException, ParserConfigurationException {
        String[] lineas = localesIngresados.split("\n");

        ArrayList<Rectangulo> locales = new ArrayList<>(localesExistentes);

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        StringWriter sw = new StringWriter();

        Element root = document.createElement("locales");
        document.appendChild(root);

        for (Rectangulo local: locales){
            Element tagRectangulo = document.createElement("rectangulo");
            root.appendChild(tagRectangulo);

            Element a = document.createElement("a");
            a.appendChild(document.createTextNode(Integer.toString(local.x)));
            tagRectangulo.appendChild(a);

            Element b = document.createElement("b");
            b.appendChild(document.createTextNode(Integer.toString(local.y)));
            tagRectangulo.appendChild(b);

            Element c = document.createElement("c");
            c.appendChild(document.createTextNode(Integer.toString(local.width)));
            tagRectangulo.appendChild(c);

            Element d = document.createElement("d");
            d.appendChild(document.createTextNode(Integer.toString(local.height)));
            tagRectangulo.appendChild(d);
        }

        for (String linea: lineas){
            String[] valores = linea.split(", ");

            String x = valores[0];
            String y = valores[1];
            String width = valores[2];
            String height = valores[3];

            Rectangulo rectangulo = new Rectangulo(Integer.parseInt(x), Integer.parseInt(y),
                    Integer.parseInt(width), Integer.parseInt(height));

            if (rectangulo.seCruzaConAlguno(locales)){
                throw new SeCruzanException();
            }

            locales.add(rectangulo);

            Element tagRectangulo = document.createElement("rectangulo");
            root.appendChild(tagRectangulo);

            Element a = document.createElement("a");
            a.appendChild(document.createTextNode(x));
            tagRectangulo.appendChild(a);

            Element b = document.createElement("b");
            b.appendChild(document.createTextNode(y));
            tagRectangulo.appendChild(b);

            Element c = document.createElement("c");
            c.appendChild(document.createTextNode(width));
            tagRectangulo.appendChild(c);

            Element d = document.createElement("d");
            d.appendChild(document.createTextNode(height));
            tagRectangulo.appendChild(d);
        }

        //transform the DOM Object to an XML File
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        DOMSource domSource = new DOMSource(document);
        // StreamResult streamResult = new StreamResult(new File(xmlFilePath));

        // If you use
        StreamResult result = new StreamResult(System.out);
        // the output will be pushed to the standard output ...
        // You can use that for debugging

        transformer.transform(domSource, result);

        System.out.println("Done creating XML File");

        transformer.transform(domSource, new StreamResult(sw));
        return sw.toString();
    }
}
