import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
                    JOptionPane.showMessageDialog(frameLocales, "La información fue guardada correctamente");
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
                try {
                    ArrayList<Venta> listaVentas = Venta.listaVentas(ventas.getText());
                    for (Venta venta : listaVentas) {
                        System.out.println(venta.x + ", " + venta.y + ", " + venta.v);
                    }
                    System.out.println(Integer.parseInt(codigoVendedor.getText()));
                    System.out.println(ciudad.getSelectedItem());
                    codigoVendedor.setText("");
                    ventas.setText("");
                    ciudad.setSelectedIndex(0);
                    JOptionPane.showMessageDialog(frameLocales, "La información fue guardada correctamente");
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(frameLocales, "No se ha podido guardar, formato incorrecto");
                }
            }
        });

        frameVentas.add(ingresarVendedor);  //adding button in JFrame
        frameVentas.add(codigoVendedor);  //adding button in JFrame
        frameVentas.add(labelCodigoVendedor);  //adding button in JFrame
        frameVentas.add(labelVentas);  //adding button in JFrame
        frameVentas.add(ventas);  //adding button in JFrame
        frameVentas.add(labelCiudad);  //adding button in JFrame
        frameVentas.add(ciudad);  //adding button in JFrame

        JLabel labelCiudad2 = new JLabel("Seleccione la ciudad:");
        labelCiudad2.setBounds(370,445, 300,30);
        JComboBox ciudad2 = new JComboBox(new String[] {"Medellin", "Cali", "Bogota"}); // Mockeado
        ciudad2.setBounds(350,480, 300,40);

        JButton generar = new JButton("Generar");  //creating instance of JButton
        generar.setBounds(600, 800, 100, 50); //x axis, y axis, width, height
        generar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new MapaPuntos(ciudad2.getSelectedItem().toString()).setVisible(true);
            }
        });

        frameMapaPuntos.add(labelCiudad2);
        frameMapaPuntos.add(ciudad2);
        frameMapaPuntos.add(generar);

        JLabel labelCiudad3 = new JLabel("Seleccione la ciudad:");
        labelCiudad3.setBounds(370,445, 300,30);
        JComboBox ciudad3 = new JComboBox(new String[] {"Medellin", "Cali", "Bogota"}); // Mockeado
        ciudad3.setBounds(350,480, 300,40);

        JButton generar2 = new JButton("Generar");  //creating instance of JButton
        generar2.setBounds(600, 800, 100, 50); //x axis, y axis, width, height
        generar2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new MapaTotal(ciudad3.getSelectedItem().toString()).setVisible(true);
            }
        });

        frameMapaTotales.add(labelCiudad3);
        frameMapaTotales.add(ciudad3);
        frameMapaTotales.add(generar2);

        framePrincipal.setSize(1000, 1000);  //400 width and 500 height
        frameLocales.setSize(1000, 1000);  //400 width and 500 height
        frameVentas.setSize(1000, 1000);  //400 width and 500 height
        frameMapaPuntos.setSize(1000, 1000);  //400 width and 500 height
        frameMapaTotales.setSize(1000, 1000);  //400 width and 500 height
        framePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameLocales.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameVentas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMapaPuntos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMapaTotales.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framePrincipal.setLayout(null);  //using no layout managers
        frameLocales.setLayout(null);  //using no layout managers
        frameVentas.setLayout(null);  //using no layout managers
        frameMapaPuntos.setLayout(null);  //using no layout managers
        frameMapaTotales.setLayout(null);  //using no layout managers

        framePrincipal.setVisible(true);  //making the frame visible
    }
}
