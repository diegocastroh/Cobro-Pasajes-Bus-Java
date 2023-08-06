package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Conexion {

    // Constantes para la conexión con la base de datos
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "contrasena";

    // Método para establecer la conexión con la base de datos
    public Connection Conexion() throws SQLException, ClassNotFoundException {
        Connection conexion = null;

        // Cargar el driver de la base de datos
        Class.forName(DRIVER);

        // Establecer la conexión con la base de datos
        conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);

        // Verificar si se ha creado la base de datos y, en caso contrario, crearla
        String crearBD = "CREATE DATABASE IF NOT EXISTS userbus;";
        PreparedStatement pstmt = conexion.prepareStatement(crearBD);
        pstmt.executeUpdate();

        // Establecer la base de datos creada como la base de datos activa
        String usarBD = "USE userbus;";
        pstmt = conexion.prepareStatement(usarBD);
        pstmt.executeUpdate();

        return conexion;
    }
    
    public void deleteDataBase(){
        try {
            Connection conexion = null;
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            String sql = "DROP DATABASE userbus";
            PreparedStatement ps = conexion.prepareStatement(sql) ;
            ps.executeUpdate(sql);

            System.out.println("La base de datos '" + "userbus" + "' ha sido eliminada correctamente.");
            
            ps.close();
            conexion.close();
        } catch (SQLException e) {
            System.out.println("Error al eliminar la base de datos: " + e.getMessage());
        }
    }

}
