import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.ArrayList;

class MapaTotal extends JFrame {
    private JPanel content;
    ArrayList<Rectangulo> rectangulos;
    ArrayList<Venta> ventas;
    String ciudad;

    public MapaTotal(String ciudad) {
        this.rectangulos = MapaTotal.getRectangulos(ciudad);
        this.ventas = MapaTotal.getVentas(ciudad);
        this.ciudad = ciudad;
        initComponents();
    }

    private void initComponents() {
        content = new Content(this.rectangulos, this.ventas);
        content.setBackground(new java.awt.Color(255, 255, 255));
        content.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        this.setContentPane(content);
        this.setTitle("Mapa de " + ciudad);
        pack();
    }

    static ArrayList<Rectangulo> getRectangulos(String ciudad){
        // Aqui se van a traer los rectangulos de la ciudad, esto es un mock
        return new ArrayList<>() {{
            add(new Rectangulo(0, 0, 30, 30));
            add(new Rectangulo(50, 50, 200, 200));
            add(new Rectangulo(400, 400, 100, 100));
            add(new Rectangulo(120, 340, 50, 50));
            add(new Rectangulo(400, 100, 100, 30));
            add(new Rectangulo(40, 390, 40, 100));
        }};
    }

    static ArrayList<Venta> getVentas(String ciudad){
        // Aqui se van a traer los rectangulos de la ciudad, esto es un mock
        return new ArrayList<>() {{
            add(new Venta(10, 10, 5));
            add(new Venta(10, 10, 10));
            add(new Venta(50, 50, 100));
            add(new Venta(405, 405, 10));
            add(new Venta(499, 499, 12));
            add(new Venta(54, 400, 100));
            add(new Venta(499, 0, 52));
            add(new Venta(251, 251, 32));
        }};
    }

    static class Content extends JPanel {
        ArrayList<Rectangulo> rectangulos;
        ArrayList<Venta> ventas;

        Content(ArrayList<Rectangulo> rectangulos, ArrayList<Venta> ventas) {
            this.rectangulos = rectangulos;
            this.ventas = ventas;
            setPreferredSize(new Dimension(600,600));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawRect(50,50,500,500);

            for (Rectangulo rectangulo:rectangulos){
                int totalLocal = 0;
                g.drawRect(rectangulo.x + 50, rectangulo.y + 50, rectangulo.width, rectangulo.height); // tiene offset
                for (int i = 0; i < ventas.size(); i++){
                    if(rectangulo.contieneVenta(ventas.get(i))){
                        totalLocal += ventas.get(i).v;
                        ventas.remove(i);
                        i--;
                    }
                }
                g.drawString("$" + totalLocal, rectangulo.x + 50 + 5, rectangulo.y + 50 + (rectangulo.height/2));
            }

            int totalFuera = 0;
            for (Venta venta:ventas) {
                totalFuera += venta.v;
            }
            g.drawString("Total de ventas por fuera de los locales: $" + totalFuera, 100, 30);
        }
    }
}
