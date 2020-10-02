import javax.xml.transform.dom.DOMSource;
import java.sql.*;
import java.util.ArrayList;

public class Database {
    private static Connection conn;
    private static Statement sentencia;
    private static ResultSet resultado;

    private static void startConnection() {
        System.out.println("Conexión a la base de datos...");

        try { // Se carga el driver JDBC-ODBC
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (Exception e) {
            System.out.println("No se pudo cargar el driver JDBC");
            System.out.println(e);
            return;
        }
        try { // Se establece la conexión con la base de datos
            conn = DriverManager.getConnection
                    ("jdbc:oracle:thin:@DESKTOP-ARFSUQM:1521:xe", "acaros", "Incubus1991");
            sentencia = conn.createStatement();
        } catch (SQLException e) {
            System.out.println("No hay conexión con la base de datos.");
            System.out.println(e);
        }
    }

    private static void closeConnection() {
        try {
            conn.close(); //Cierre de la conexión
            System.out.println("Conexión con la BD cerrada");
        } catch (SQLException e) {
            System.out.println("Error: " +
                    e.getMessage());
        }
    }

    static ArrayList<Venta> getVentas(String ciudad) {
        startConnection();

        try {
            System.out.println("Seleccionando...");
            ArrayList<Venta> ventas = new ArrayList<>();
            String query = String.format("SELECT CodigoVendedor, Ciudad, Tventas.* " +
                    "FROM VVCITY, TABLE(VVCITY.Ventas) Tventas WHERE Ciudad = '%s'", ciudad);

            resultado = sentencia.executeQuery(query);
            while (resultado.next()) {
//                int codigo_vendedor = resultado.getInt("CodigoVendedor");
//                String ciudad2 = resultado.getString("Ciudad");
                int x = resultado.getInt("x");
                int y = resultado.getInt("y");
                int v = resultado.getInt("v");

                ventas.add(new Venta(x, y, v));
            }
            closeConnection();
            return ventas;

        } catch (SQLException e) {
            System.out.println("Error in select ventas");
            System.out.println("Error: " +
                    e.getMessage());
            closeConnection();
        }
        return null;
    }

    static void insertVentas(int codigoVendedor, String ciudad, ArrayList<Venta> listaVentas) {

        String ventasSQLstring = Venta.getVentasSQLString(listaVentas);
        System.out.println(ventasSQLstring);

        startConnection();

        try {
            System.out.println("Insertando ventas...");
            String query = String.format("INSERT INTO VVCITY VALUES(%s,'%s',%s)", codigoVendedor, ciudad, ventasSQLstring);
            System.out.println("Executing query " + query);
            resultado = sentencia.executeQuery(query);
            closeConnection();

        } catch (SQLException e) {
            System.out.println("Error in insert ventas");
            System.out.println("Error: " +
                    e.getMessage());
            closeConnection();
        }
    }
    static ArrayList<Rectangulo> getLocales(String ciudad) {
        startConnection();

        try {
            System.out.println("Consultando locales...");
            ArrayList<Rectangulo> locales = new ArrayList<>();

            String query= String.format("SELECT Nombre_ciudad, EXTRACTVALUE(doc,'/figuras/rectangulo/a') AS a,EXTRACTVALUE(doc,'/figuras/rectangulo/b') AS b,EXTRACTVALUE(doc,'/figuras/rectangulo/c') AS c,EXTRACTVALUE(doc,'/figuras/rectangulo/d') AS d FROM figura " +
            "FROM CITY WHERE Nombre_ciudad = '%s'", ciudad);
            resultado = sentencia.executeQuery(query);
            while (resultado.next())
            {
                locales.add(new Rectangulo(resultado.getInt("a"), resultado.getInt("b"), resultado.getInt("c"),resultado.getInt("d")));

            }

            System.out.println( "Locales");
            System.out.println( locales);

            closeConnection();
            return locales;

        } catch (SQLException e) {
            System.out.println("Error in select ventas");
            System.out.println("Error: " +
                    e.getMessage());
            closeConnection();
        }
        return null;
    }


    //static void insertarLocales(String nombreCiudad, String XML) {
        static public void insertarLocales(String nombreCiudad, DOMSource XML) {
        startConnection();

        try {
            System.out.println("Ingresando...");
            resultado = sentencia.executeQuery
                    (String.format("INSERT INTO CITY VALUES (%s, %s)", nombreCiudad, XML));

            System.out.println("Consulta finalizada.");
            closeConnection();

        } catch (SQLException e) {
            System.out.println("Error: " +
                    e.getMessage());
            closeConnection();
        }
    }
    //static void insertarLocales(String nombreCiudad, String XML) {
    static public void updateLocales(String nombreCiudad, DOMSource XML) {
        startConnection();

        try {
            System.out.println("Ingresando...");
            resultado = sentencia.executeQuery
                    (String.format("UPDATE FROM CITY set VALUES (%s, %s)  WHERE Nombre_ciudad = '%s'", nombreCiudad, XML));

            System.out.println("Consulta finalizada.");
            closeConnection();

        } catch (SQLException e) {
            System.out.println("Error: " +
                    e.getMessage());
            closeConnection();
        }
    }
}


