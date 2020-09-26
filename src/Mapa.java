import javax.swing.*;
import java.util.ArrayList;

public class Mapa extends JFrame {
    protected JPanel content;
    ArrayList<Rectangulo> rectangulos;
    ArrayList<Venta> ventas;
    String ciudad;

    public Mapa(String ciudad) {
        this.rectangulos = MapaTotal.getRectangulos(ciudad);
        this.ventas = MapaTotal.getVentas(ciudad);
        this.ciudad = ciudad;
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
}
