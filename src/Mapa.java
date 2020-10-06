import javax.swing.*;
import javax.xml.crypto.Data;
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
        return Database.getLocales(ciudad);
    }

    static ArrayList<Venta> getVentas(String ciudad) {
        return Database.getVentas(ciudad);
    }
}
