import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.ArrayList;

class MapaTotal extends Mapa {

    public MapaTotal(String ciudad) {
        super(ciudad);
        initComponents();
    }

    private void initComponents() {
        content = new MapaTotal.Content(this.rectangulos, this.ventas);
        content.setBackground(new java.awt.Color(255, 255, 255));
        content.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        this.setContentPane(content);
        this.setTitle("Mapa de " + ciudad);
        pack();
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
            super.paintComponent(g);

            g.drawRect(50, 50, 500, 500);

            for (Rectangulo rectangulo : rectangulos) {
                int totalLocal = 0;
                g.drawRect(rectangulo.x + 50, rectangulo.y + 50, rectangulo.width, rectangulo.height); // tiene offset
                for (int i = 0; i < ventas.size(); i++) {
                    if (rectangulo.contieneVenta(ventas.get(i))) {
                        totalLocal += ventas.get(i).v;
                        ventas.remove(i);
                        i--;
                    }
                }
                g.drawString("$" + totalLocal, rectangulo.x + 50 + 5, rectangulo.y + 50 + (rectangulo.height / 2));
            }

            int totalFuera = 0;
            for (Venta venta : ventas) {
                totalFuera += venta.v;
            }
            g.drawString("Total de ventas por fuera de los locales: $" + totalFuera, 100, 30);
        }
    }
}
