/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelDao;

import Config.Conexion;
import Model.Bus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author lol_2
 */
public class LoginDao {

    Conexion cn = null;
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public void LeerBus() {
        try {
            // Obtener la conexión con la base de datos
            con = cn.Conexion();
            // Consulta SQL para obtener los datos de la tabla "usuarios"
            String consulta = "SELECT * FROM usuarios";
            ps = con.prepareStatement(consulta);
            // Ejecutar la consulta y obtener el resultado
            rs = ps.executeQuery();

            // Recorrer el resultado y mostrar los datos de la tabla "usuarios"
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                System.out.println("ID: " + id + ", Nombre: " + nombre + ", Correo: " + correo);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Cerrar los objetos ResultSet, PreparedStatement y Connection
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void CrearBus(Bus bus) {
        try {
            // Obtener la conexión con la base de datos
            con = cn.Conexion();
            // Consulta SQL para obtener los datos de la tabla "usuarios"
            String consulta = "INSERT INTO usuarios (nombre, correo, contrasena) VALUES (?,?,?)";
            ps = con.prepareStatement(consulta);
            ps.setString(1, bus.getModelo());
            ps.setString(1, bus.getMarca());
            ps.setString(1, bus.getPlaca());
            // Ejecutar la consulta y obtener el número de filas afectadas
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Usuario agregado correctamente");
            } else {
                System.out.println("No se pudo agregar el usuario");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();

        } finally {
            // Cerrar los objetos ResultSet, PreparedStatement y Connection
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
