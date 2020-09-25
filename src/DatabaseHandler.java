import com.sun.javaws.jnl.XMLFormat;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandler
{
    private Connection conn;
    private Statement sentencia;
    private ResultSet resultado;

    private void startConnection() {
        System.out.println( "Conexión a la base de datos..." );

        try{ // Se carga el driver JDBC-ODBC
            Class.forName ("oracle.jdbc.driver.OracleDriver");
        } catch( Exception e ) {
            System.out.println("No se pudo cargar el driver JDBC");
            return;
        }
        try{ // Se establece la conexión con la base de datos
            conn = DriverManager.getConnection
                    ("jdbc:oracle:thin:@localhost:1521:xe","gato", "gato");
            sentencia = conn.createStatement();
        } catch( SQLException e ) {
            System.out.println( "No hay conexión con la base de datos." );
        }
    }

    private void closeConnection(){
        try{
            conn.close(); //Cierre de la conexión
            System.out.println( "Conexión con la BD cerrada" );
        } catch(SQLException e) {
            System.out.println("Error: " +
                    e.getMessage());
        }
    }

    public void insertarLocales(String nombreCiudad, String XML){
        startConnection();

        try {
            System.out.println( "Ingresando..." );
            resultado = sentencia.executeQuery
                    (String.format("INSERT INTO CITY VALUES (%s, %s)", nombreCiudad, XML));

            System.out.println("Consulta finalizada.");
            closeConnection();

        } catch( SQLException e ) {
            System.out.println("Error: " +
                    e.getMessage());
        }
    }
}


