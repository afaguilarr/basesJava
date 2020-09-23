import java.util.ArrayList;

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

    public boolean contieneVenta(Venta venta) {
        return (venta.x >= this.x && venta.x <= this.x + this.width) &&
                (venta.y >= this.y && venta.y <= this.y + this.height);
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
