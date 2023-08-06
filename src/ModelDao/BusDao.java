/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelDao;

import Config.ApiConsumer;
import Config.Conexion;
import Model.Bus;
import SimplifyTool.JsonEncrypter;
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
public class BusDao {
    ApiConsumer api = new ApiConsumer();
    JsonEncrypter encrypt = new JsonEncrypter();
    Conexion cn = new Conexion();
    
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    public void CrearTablaBus(){
        try {
            con = cn.Conexion();
            DatabaseMetaData metaData = (DatabaseMetaData) con.getMetaData();
            rs = metaData.getTables(null, null, "Bus", null);
            if (rs.next()) {
                System.out.println("La tabla Bus ya existe.");
            } else {
                System.out.println("La tabla Bus no existe. Creando tabla...");
                String sql = "CREATE TABLE Bus ("
                        + "id INT NOT NULL AUTO_INCREMENT,"
                        + "modelo VARCHAR(255),"
                        + "marca VARCHAR(255),"
                        + "ruta_id INT,"
                        + "placa VARCHAR(255),"
                        + "fecha_vencimiento_revision_tecnica DATE,"
                        + "capacidad INT,"
                        + "PRIMARY KEY (id))";
                ps = con.prepareStatement(sql);
                ps.executeUpdate(sql);
                System.out.println("La tabla Bus ha sido creada exitosamente.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Ha ocurrido un error al verificar o crear la tabla Bus: " + e.getMessage());
        }
    }
    public void getBusData(int id) throws Exception {
        try {
            HashMap<String, Object> data = new HashMap<>();
            data.put("id", id);

            String json = api.consumerApi("bus", data);

            // Verificar si el directorio "datos" existe, y crearlo en caso contrario
            File datosDir = new File("src/data");
            if (!datosDir.exists()) {
                datosDir.mkdir();
            }
            // Crear un archivo JSON para escribir los datos
            encrypt.encryptJson(json, "src/data/bus");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
    
    
    public void CreateBus() throws SQLException, Exception {
        // Preparar sentencia SQL de inserción
        String sql = "INSERT INTO bus (modelo, marca, ruta_id, placa, fecha_vencimiento_revision_tecnica, capacidad) VALUES (?, ?, ?, ?, ?, ?);";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            JSONObject json = new JSONObject(encrypt.decryptJson("src/data/bus.encrypted"));
            ps.setString(1, json.getString("modelo"));
            ps.setString(2, json.getString("marca"));
            ps.setInt(3, json.getInt("ruta_id"));
            ps.setString(4, json.getString("placa"));
            ps.setString(5, json.getString("fecha_vencimiento_revision_tecnica"));
            ps.setInt(6, json.getInt("capacidad_pasajeros"));
            // Ejecutar sentencia SQL de inserción
            ps.executeUpdate();
        } catch (ClassNotFoundException | SQLException | JSONException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public Bus listarBus(){
        String sql = "Select * from bus";
        Bus bus = new Bus();
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                bus.setId(rs.getInt("id"));
                bus.setModelo(rs.getString("modelo"));
                bus.setMarca(rs.getString("marca"));
                //bus.setRuta(rs.getString("ruta_id"));
                bus.setPlaca(rs.getString("placa"));
                bus.setFecha_revi_tecnica(rs.getString("fecha_vencimiento_revision_tecnica"));
                bus.setCapacidad(rs.getInt("capacidad"));
            }
            return bus;
        } catch (ClassNotFoundException | SQLException e) {
        }
        return bus;
    }
}
