import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.ArrayList;

class MapaPuntos extends Mapa {
    ArrayList<Venta> ventasPorPunto;

    public MapaPuntos(String ciudad) {
        super(ciudad);
        this.ventasPorPunto  = Venta.listaVentas(ventas);
        initComponents();
    }

    private void initComponents() {
        content = new Content(this.rectangulos, this.ventasPorPunto);
        content.setBackground(new java.awt.Color(255, 255, 255));
        content.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        this.setContentPane(content);
        this.setTitle("Mapa de " + ciudad);
        pack();
    }

    static class Content extends JPanel {
        ArrayList<Rectangulo> rectangulos;
        ArrayList<Venta> ventasPorPunto;

        Content(ArrayList<Rectangulo> rectangulos, ArrayList<Venta> ventas) {
            this.rectangulos = rectangulos;
            this.ventasPorPunto = ventas;
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

            for (Venta venta : this.ventasPorPunto) {
                g.drawString("$" + venta.v, venta.x - 5 + 50, venta.y - 3 + 50);
                g.fillOval(venta.x + 50, venta.y + 50, 7, 7);
            }
        }
    }
}
