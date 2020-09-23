import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.ArrayList;

class XMLParser {

    public static DOMSource xmlParser(String locales) throws SeCruzanException, TransformerException, ParserConfigurationException {
        String[] lineas = locales.split("\n");

        ArrayList<Rectangulo> rectangulos = new ArrayList<>();

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element root = document.createElement("locales");
        document.appendChild(root);

        for (String linea: lineas){
            String[] valores = linea.split(", ");

            String x = valores[0];
            String y = valores[1];
            String width = valores[2];
            String height = valores[3];

            Rectangulo rectangulo = new Rectangulo(Integer.parseInt(x), Integer.parseInt(y),
                    Integer.parseInt(width), Integer.parseInt(height));

            if (rectangulo.seCruzaConAlguno(rectangulos)){
                throw new SeCruzanException();
            }

            rectangulos.add(rectangulo);

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
        DOMSource domSource = new DOMSource(document);
        // StreamResult streamResult = new StreamResult(new File(xmlFilePath));

        // If you use
        StreamResult result = new StreamResult(System.out);
        // the output will be pushed to the standard output ...
        // You can use that for debugging

        transformer.transform(domSource, result);

        System.out.println("Done creating XML File");
        return domSource;
    }
}
