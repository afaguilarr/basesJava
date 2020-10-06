import javax.xml.transform.dom.DOMSource;
import java.sql.*;
import java.util.ArrayList;

public class Database {
    private static Connection conn;
    private static Statement sentencia;
    private static ResultSet resultado;

    private static void startConnection() {
        System.out.println("\nConexión a la base de datos...");

        try { // Se carga el driver JDBC-ODBC
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (Exception e) {
            System.out.println("No se pudo cargar el driver JDBC");
            System.out.println(e);
            return;
        }
        try { // Se establece la conexión con la base de datos
            conn = DriverManager.getConnection
                    ("jdbc:oracle:thin:@localhost:1521:xe", "gato", "gato");
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
        ArrayList<Venta> ventas = new ArrayList<>();

        try {
            System.out.println("Seleccionando...");
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

        } catch (SQLException e) {
            System.out.println("Error in select ventas");
            System.out.println("Error: " +
                    e.getMessage());
            closeConnection();
        }
        return ventas;
    }

    static ArrayList<Venta> getVentas(String ciudad, int codigoVendedor) {
        startConnection();
        ArrayList<Venta> ventas = new ArrayList<>();

        try {
            System.out.println("Seleccionando...");
            String query = String.format("SELECT CodigoVendedor, Ciudad, Tventas.* " +
                    "FROM VVCITY, TABLE(VVCITY.Ventas) Tventas WHERE Ciudad = '%s' AND CodigoVendedor = %s", ciudad, codigoVendedor);

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

        } catch (SQLException e) {
            System.out.println("Error in select ventas");
            System.out.println("Error: " +
                    e.getMessage());
            closeConnection();
        }
        return ventas;
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

    static void updateVentas(int codigoVendedor, String ciudad, ArrayList<Venta> listaVentas) {

        String ventasSQLstring = Venta.getVentasSQLString(listaVentas);
        System.out.println(ventasSQLstring);

        startConnection();

        try {
            System.out.println("Actualizando ventas...");
            String query = String.format("UPDATE VVCITY SET Ventas = %s WHERE CodigoVendedor = %s  AND Ciudad = '%s'", ventasSQLstring, codigoVendedor, ciudad);
            System.out.println("Executing query " + query);
            resultado = sentencia.executeQuery(query);
            closeConnection();

        } catch (SQLException e) {
            System.out.println("Error in update ventas");
            System.out.println("Error: " +
                    e.getMessage());
            closeConnection();
        }
    }

    static ArrayList<String> getCiudades() {
        startConnection();
        ArrayList<String> ciudades = new ArrayList<>();

        try {
            System.out.println("Consultando ciudades...");


            String query = String.format("SELECT Nombre_ciudad FROM CITY");
            resultado = sentencia.executeQuery(query);
            while (resultado.next()) {
                ciudades.add(resultado.getString("Nombre_ciudad"));
            }
            closeConnection();

        } catch (SQLException e) {
            System.out.println("Error in select ciudades");
            System.out.println("Error: " +
                    e.getMessage());
            closeConnection();
        }
        return ciudades;

    }

    static ArrayList<Rectangulo> getLocales(String ciudad) {
        startConnection();
        ArrayList<Rectangulo> locales = new ArrayList<>();

        try {
            System.out.println("Consultando locales...");

            String queryTemplate = "SELECT Nombre_ciudad, EXTRACTVALUE(locales,'/locales/rectangulo[%s]/a') AS a, EXTRACTVALUE(locales,'/locales/rectangulo[%s]/b') AS b, EXTRACTVALUE(locales,'/locales/rectangulo[%s]/c') AS c,EXTRACTVALUE(locales,'/locales/rectangulo[%s]/d') AS d FROM CITY WHERE Nombre_ciudad = '%s'";
            int i = 1;

            while (true) {
                String query = String.format(queryTemplate, i, i, i, i, ciudad);
                resultado = sentencia.executeQuery(query);
                if (resultado.next()) {
                    if (resultado.getString("a") == null) {
                        closeConnection();
                        return locales;
                    } else {
                        locales.add(new Rectangulo(resultado.getInt("a"), resultado.getInt("b"), resultado.getInt("c"), resultado.getInt("d")));
                    }
                    i++;
                } else {
                    closeConnection();
                    return locales;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error in select locales");
            System.out.println("Error: " +
                    e.getMessage());
            closeConnection();
        }
        return locales;
    }


    //static void insertarLocales(String nombreCiudad, String XML) {
    static public void insertarLocales(String nombreCiudad, String XML) {
        startConnection();

        try {
            System.out.println("Ingresando...");
            resultado = sentencia.executeQuery
                    (String.format("INSERT INTO CITY VALUES ('%s', '%s')", nombreCiudad, XML));

            System.out.println("Consulta finalizada.");
            closeConnection();

        } catch (SQLException e) {
            System.out.println("Error: " +
                    e.getMessage());
            closeConnection();
        }
    }

    //static void insertarLocales(String nombreCiudad, String XML) {
    static public void updateLocales(String nombreCiudad, String XML) {
        startConnection();

        try {
            System.out.println("Ingresando...");
            resultado = sentencia.executeQuery
                    (String.format("UPDATE CITY SET locales ='%s' WHERE Nombre_ciudad = '%s'",  XML, nombreCiudad));

            System.out.println("Consulta finalizada.");
            closeConnection();

        } catch (SQLException e) {
            System.out.println("Error: " +
                    e.getMessage());
            closeConnection();
        }
    }
}


