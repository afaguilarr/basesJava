import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class SeCruzanException extends Exception {}

class Rectangulo {
    int[] supDer, infIzq;
    int x, y, height, width;

    Rectangulo (int x, int y, int width, int height) {
        this.supDer = new int[] {x + width, y};
        this.infIzq = new int[] {x, y + height};
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean seCruza (Rectangulo rectangulo) {
        if (this.supDer[1] >= rectangulo.infIzq[1] || this.infIzq[1] <= rectangulo.supDer[1]) {
            return false;
        }
        if (this.supDer[0] <= rectangulo.infIzq[0] || this.infIzq[0] >= rectangulo.supDer[0]) {
            return false;
        }
        return true;
    }

    public boolean seCruzaConAlguno(ArrayList<Rectangulo> rectangulos){
        for (Rectangulo rectangulo1:rectangulos){
            if (this.seCruza(rectangulo1)){
                return true;
            }
        }
        return false;
    }
}

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

            System.out.println(rectangulos.toString());

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

class Vista1 {
    public static void vista1 () {
        JFrame framePrincipal = new JFrame("Menu principal");  //creating instance of JFrame
        JFrame frameLocales = new JFrame("Ingreso de datos de los locales de una ciudad");  //creating instance of JFrame
        JFrame frameVentas = new JFrame("Ingreso de datos de las ventas de un vendedor en una ciudad");  //creating instance of JFrame
        JFrame frameMapaPuntos = new JFrame("Mapa de puntos de ventas");  //creating instance of JFrame
        JFrame frameMapaTotales = new JFrame("Mapa de total de ventas");  //creating instance of JFrame

        JButton volver = new JButton("< Volver");  //creating instance of JButton
        volver.setBounds(300, 800, 100, 50);  //x axis, y axis, width, height
        volver.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                frameLocales.setVisible(false);
                frameVentas.setVisible(false);
                frameMapaPuntos.setVisible(false);
                frameMapaTotales.setVisible(false);
                framePrincipal.setVisible(true);
            }
        });

        JButton datosLocales = new JButton("Ingreso de datos de los locales de una ciudad");  //creating instance of JButton
        datosLocales.setBounds(250, 200, 500, 50);  //x axis, y axis, width, height
        datosLocales.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                framePrincipal.setVisible(false);
                frameLocales.setVisible(true);
                frameLocales.add(volver);
            }
        });

        JButton datosVentas = new JButton("Ingreso de datos de las ventas de un vendedor en una ciudad");  //creating instance of JButton
        datosVentas.setBounds(250, 400, 500, 50);  //x axis, y axis, width, height
        datosVentas.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                framePrincipal.setVisible(false);
                frameVentas.setVisible(true);
                frameVentas.add(volver);
            }
        });

        JButton mapaPuntos = new JButton("Mapa de puntos de ventas");  //creating instance of JButton
        mapaPuntos.setBounds(250, 600, 500, 50);  //x axis, y axis, width, height
        mapaPuntos.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                framePrincipal.setVisible(false);
                frameMapaPuntos.setVisible(true);
                frameMapaPuntos.add(volver);
            }
        });

        JButton mapaTotal = new JButton("Mapa de total de ventas");  //creating instance of JButton
        mapaTotal.setBounds(250, 800, 500, 50);  //x axis, y axis, width, height
        mapaTotal.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                framePrincipal.setVisible(false);
                frameMapaTotales.setVisible(true);
                frameMapaTotales.add(volver);
            }
        });

        framePrincipal.add(datosLocales);  //adding button in JFrame
        framePrincipal.add(datosVentas);  //adding button in JFrame
        framePrincipal.add(mapaPuntos);  //adding button in JFrame
        framePrincipal.add(mapaTotal);  //adding button in JFrame

        JLabel labelLocales = new JLabel("Ingrese los locales de la ciudad:");
        labelLocales.setBounds(120,100, 300,30);
        JTextArea locales = new JTextArea();
        locales.setBounds(100,135, 400,600);

        JLabel labelNombreCiudad = new JLabel("Ingrese el nombre de la ciudad:");
        labelNombreCiudad.setBounds(620,445, 300,30);
        JTextField nombreCiudad = new JTextField();
        nombreCiudad.setBounds(600,480, 300,40);

        JButton ingresarLocal = new JButton("Ingresar");  //creating instance of JButton
        ingresarLocal.setBounds(600, 800, 100, 50);  //x axis, y axis, width, height  //x axis, y axis, width, height
        ingresarLocal.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    XMLParser.xmlParser(locales.getText());
                    System.out.println(nombreCiudad.getText());
                    nombreCiudad.setText("");
                    locales.setText("");
                    JOptionPane.showMessageDialog(frameLocales, "La información fue correctamente guardada");
                }   catch (SeCruzanException seCruzanException){
                    JOptionPane.showMessageDialog(frameLocales, "No se ha podido guardar, los locales se cruzan");
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(frameLocales, "No se ha podido guardar, formato incorrecto");
                }
            }
        });

        frameLocales.add(ingresarLocal);  //adding button in JFrame
        frameLocales.add(nombreCiudad);  //adding button in JFrame
        frameLocales.add(labelNombreCiudad);  //adding button in JFrame
        frameLocales.add(labelLocales);  //adding button in JFrame
        frameLocales.add(locales);  //adding button in JFrame

        JLabel labelVentas = new JLabel("Ingrese las ventas:");
        labelVentas.setBounds(120,100, 300,30);
        JTextArea ventas = new JTextArea();
        ventas.setBounds(100,135, 400,600);

        JLabel labelCodigoVendedor = new JLabel("Ingrese el código de vendedor:");
        labelCodigoVendedor.setBounds(620,245, 300,30);
        JTextField codigoVendedor = new JTextField();
        codigoVendedor.setBounds(600,280, 300,20);

        JLabel labelCiudad = new JLabel("Seleccione la ciudad:");
        labelCiudad.setBounds(620,445, 300,30);
        JComboBox ciudad = new JComboBox(new String[] {"Medellin", "Cali", "Bogota"}); // Mockeado
        ciudad.setBounds(600,480, 300,40);

        JButton ingresarVendedor = new JButton("Ingresar");  //creating instance of JButton
        ingresarVendedor.setBounds(600, 800, 100, 50);  //x axis, y axis, width, height  //x axis, y axis, width, height
        ingresarVendedor.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println(codigoVendedor.getText());
                System.out.println(ciudad.getSelectedItem());
                System.out.println(ventas.getText());
                codigoVendedor.setText("");
                ventas.setText("");
                ciudad.setSelectedIndex(0);
            }
        });

        frameVentas.add(ingresarVendedor);  //adding button in JFrame
        frameVentas.add(codigoVendedor);  //adding button in JFrame
        frameVentas.add(labelCodigoVendedor);  //adding button in JFrame
        frameVentas.add(labelVentas);  //adding button in JFrame
        frameVentas.add(ventas);  //adding button in JFrame
        frameVentas.add(labelCiudad);  //adding button in JFrame
        frameVentas.add(ciudad);  //adding button in JFrame

        framePrincipal.setSize(1000, 1000);  //400 width and 500 height
        frameLocales.setSize(1000, 1000);  //400 width and 500 height
        frameVentas.setSize(1000, 1000);  //400 width and 500 height
        frameMapaPuntos.setSize(1000, 1000);  //400 width and 500 height
        frameMapaTotales.setSize(1000, 1000);  //400 width and 500 height

        framePrincipal.setLayout(null);  //using no layout managers
        frameLocales.setLayout(null);  //using no layout managers
        frameVentas.setLayout(null);  //using no layout managers
        frameMapaPuntos.setLayout(null);  //using no layout managers
        frameMapaTotales.setLayout(null);  //using no layout managers

        framePrincipal.setVisible(true);  //making the frame visible
    }
}

public class Main {
    public static void main(String[] args) {
        Vista1.vista1();
    }
}