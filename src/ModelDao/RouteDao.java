/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelDao;

import Config.ApiConsumer;
import Config.Conexion;
import Model.Route;
import SimplifyTool.JsonEncrypter;
import SimplifyTool.convertDateSQL;
import com.mysql.cj.jdbc.DatabaseMetaData;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author lol_2
 */
public class RouteDao {

    ApiConsumer api = new ApiConsumer();
    JsonEncrypter encrypt = new JsonEncrypter();
    Conexion cn = new Conexion();

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public void CrearTablaRuta() {
        try {
            con = cn.Conexion();
            DatabaseMetaData metaData = (DatabaseMetaData) con.getMetaData();
            rs = metaData.getTables(null, null, "Ruta", null);
            if (rs.next()) {
                System.out.println("La tabla Ruta ya existe.");
            } else {
                System.out.println("La tabla Ruta no existe. Creando tabla...");
                String sql = "CREATE TABLE Ruta ("
                        + "id INT NOT NULL AUTO_INCREMENT,"
                        + "nombre VARCHAR(255),"
                        + "origen VARCHAR(255),"
                        + "destino VARCHAR(255),"
                        + "tiempo_estimado TIME,"
                        + "PRIMARY KEY (id))";
                ps = con.prepareStatement(sql);
                ps.executeUpdate(sql);
                System.out.println("La tabla Ruta ha sido creada exitosamente.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Ha ocurrido un error al verificar o crear la tabla Ruta: " + e.getMessage());
        }
    }

    public void getRutaData(int id) throws Exception {
        try {
            HashMap<String, Object> data = new HashMap<>();
            data.put("id", id);

            String json = api.consumerApi("route", data);

            // Verificar si el directorio "datos" existe, y crearlo en caso contrario
            File datosDir = new File("src/data");
            if (!datosDir.exists()) {
                datosDir.mkdir();
            }
            // Crear un archivo JSON para escribir los datos
            encrypt.encryptJson(json, "src/data/routedata");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void CreateRuta() throws SQLException, Exception {
        // Preparar sentencia SQL de inserción
        String sql = "INSERT INTO ruta (nombre, origen, destino, tiempo_estimado) VALUES (?, ?, ?, ?);";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            convertDateSQL cv = new convertDateSQL();
            JSONObject json = new JSONObject(encrypt.decryptJson("src/data/routedata.encrypted"));
            ps.setString(1, json.getString("nombre"));
            ps.setString(2, json.getString("origen"));
            ps.setString(3, json.getString("destino"));
            ps.setTime(4, cv.convertTime(json.getString("tiempo_estimado")));
            // Ejecutar sentencia SQL de inserción
            ps.executeUpdate();
        } catch (ClassNotFoundException | SQLException | JSONException e) {
            System.out.println(e.getMessage());
        }
    }
    public Route listarRuta(){
        String sql = "Select * from ruta";
        Route route = new Route();
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                route.setId(rs.getInt("id"));
                route.setNombre(rs.getString("nombre"));
                route.setOrigen(rs.getString("origen"));
                route.setDestino(rs.getString("destino"));
                route.setTiempo_estimado(rs.getString("tiempo_estimado"));
            }
            return route;
        } catch (ClassNotFoundException | SQLException e) {
        }
        return route;
    }
}
