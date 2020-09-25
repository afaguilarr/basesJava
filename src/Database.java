import com.sun.javaws.jnl.XMLFormat;

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
            return;
        }
        try { // Se establece la conexión con la base de datos
            conn = DriverManager.getConnection
                    ("jdbc:oracle:thin:@localhost:1521:xe", "gato", "gato");
            sentencia = conn.createStatement();
        } catch (SQLException e) {
            System.out.println("No hay conexión con la base de datos.");
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
                int codigo_vendedor = resultado.getInt("CodigoVendedor");
                String ciudad2 = resultado.getString("Ciudad");
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
        }
        return null;
    }


    public void insertarLocales(String nombreCiudad, String XML) {
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
        }
    }
}


