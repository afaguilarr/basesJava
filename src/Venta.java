import java.util.ArrayList;

class Venta {
    int x, y, v;

    Venta(int x, int y, int v) {
        this.x = x;
        this.y = y;
        this.v = v;
    }

    public boolean seCruza(Venta venta) {
        return this.x == venta.x && this.y == venta.y;
    }

    public void aumentarVenta(Venta venta) {
        this.v += venta.v;
    }

    public static ArrayList<Venta> listaVentas(String ventasFormulario) {
        String[] lineaVentas = ventasFormulario.split("\n");
        ArrayList<Venta> ventas = new ArrayList<>();
        boolean seCruzo;

        for (String lineaVenta : lineaVentas) {
            String[] parametrosVenta = lineaVenta.split(", ");
            seCruzo = false;

            Venta venta = new Venta(Integer.parseInt(parametrosVenta[0]),
                    Integer.parseInt(parametrosVenta[1]), Integer.parseInt(parametrosVenta[2]));

            for (Venta ventaAnterior : ventas) {
                if (venta.seCruza(ventaAnterior)) {
                    ventaAnterior.aumentarVenta(venta);
                    seCruzo = true;
                    break;
                }
            }
            if (!seCruzo) {
                ventas.add(venta);
            }
        }
        return ventas;
    }

    public static ArrayList<Venta> listaVentas(ArrayList<Venta> ventasSinRefinar) {
        boolean seCruzo;
        ArrayList<Venta> ventas = new ArrayList<>();

        for (Venta venta : ventasSinRefinar) {
            seCruzo = false;

            for (Venta ventaAnterior : ventas) {
                if (venta.seCruza(ventaAnterior)) {
                    ventaAnterior.aumentarVenta(venta);
                    seCruzo = true;
                    break;
                }
            }
            if (!seCruzo) {
                ventas.add(venta);
            }
        }
        return ventas;
    }

    public static String getVentasSQLString(ArrayList<Venta> listaVentas) {
        StringBuilder queryString = new StringBuilder("nest_venta(");

        for (Venta venta : listaVentas) {
            queryString.append(venta.toSQLString());
            queryString.append(",");
        }
        //Delete last comma added on for loop
        queryString.deleteCharAt(queryString.length() - 1);
        queryString.append(")");
        return queryString.toString();
    }

    public String toSQLString() {
        return String.format("venta_type(%s,%s,%s)", this.x, this.y, this.v);
    }
}
