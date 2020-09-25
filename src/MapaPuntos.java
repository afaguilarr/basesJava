import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.ArrayList;

class MapaPuntos extends JFrame {
    private JPanel content;
    ArrayList<Rectangulo> rectangulos;
    ArrayList<Venta> ventas;
    String ciudad;

    public MapaPuntos(String ciudad) {
        this.rectangulos = MapaPuntos.getRectangulos(ciudad);
        this.ventas = MapaPuntos.getVentas(ciudad);
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

    static ArrayList<Rectangulo> getRectangulos(String ciudad) {
        // Aqui se van a traer los rectangulos de la ciudad, esto es un mock
        return new ArrayList<Rectangulo>() {{
            add(new Rectangulo(0, 0, 30, 30));
            add(new Rectangulo(50, 50, 200, 200));
            add(new Rectangulo(400, 400, 100, 100));
        }};
    }

    static ArrayList<Venta> getVentas(String ciudad) {
        return Database.getVentas(ciudad);
    }

    static class Content extends JPanel {
        ArrayList<Rectangulo> rectangulos;
        ArrayList<Venta> ventas;

        Content(ArrayList<Rectangulo> rectangulos, ArrayList<Venta> ventas) {
            this.rectangulos = rectangulos;
            this.ventas = ventas;
            setPreferredSize(new Dimension(600, 600));
        }

        @Override
        public void paintComponent(Graphics g) {
            // calculos afuera y pintadas adentro
            super.paintComponent(g);

            g.drawRect(50, 50, 500, 500);

            for (Rectangulo rectangulo : rectangulos) {
                g.drawRect(rectangulo.x + 50, rectangulo.y + 50, rectangulo.width, rectangulo.height); // tiene offset
            }
            ArrayList<Venta> ventasPorPunto = Venta.listaVentas(ventas);
            for (Venta venta : ventasPorPunto) {
                g.drawString("$" + venta.v, venta.x - 5 + 50, venta.y - 3 + 50);
                g.fillOval(venta.x + 50, venta.y + 50, 7, 7);
            }
        }
    }
}
