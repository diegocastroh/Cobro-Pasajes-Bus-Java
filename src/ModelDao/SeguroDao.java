/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelDao;

import Config.ApiConsumer;
import Config.Conexion;
import Model.Insurance;
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
public class SeguroDao {
    ApiConsumer api = new ApiConsumer();
    JsonEncrypter encrypt = new JsonEncrypter();
    Conexion cn = new Conexion();

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public void CrearTablaSeguro() {
        try {
            con = cn.Conexion();
            DatabaseMetaData metaData = (DatabaseMetaData) con.getMetaData();
            rs = metaData.getTables(null, null, "Seguro", null);
            if (rs.next()) {
                System.out.println("La tabla Seguro ya existe.");
            } else {
                System.out.println("La tabla Seguro no existe. Creando tabla...");
                String sql = "CREATE TABLE Seguro ("
                        + "id INT NOT NULL AUTO_INCREMENT,"
                        + "nombre VARCHAR(255),"
                        + "entidad VARCHAR(255),"
                        + "cobertura VARCHAR(255),"
                        + "exclusiones VARCHAR(255),"
                        + "costo DOUBLE(10,2),"
                        + "PRIMARY KEY (id))";
                ps = con.prepareStatement(sql);
                ps.executeUpdate(sql);
                System.out.println("La tabla Seguro ha sido creada exitosamente.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Ha ocurrido un error al verificar o crear la tabla Seguro: " + e.getMessage());
        }
    }

    public void getSeguroData(int id) throws Exception {
        try {
            HashMap<String, Object> data = new HashMap<>();
            data.put("id", id);

            String json = api.consumerApi("insurance", data);

            // Verificar si el directorio "datos" existe, y crearlo en caso contrario
            File datosDir = new File("src/data");
            if (!datosDir.exists()) {
                datosDir.mkdir();
            }
            // Crear un archivo JSON para escribir los datos
            encrypt.encryptJson(json, "src/data/insurancedata");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void CreateSeguro() throws SQLException, Exception {
        // Preparar sentencia SQL de inserción
        String sql = "INSERT INTO seguro (nombre, entidad, cobertura, exclusiones, costo) VALUES (?, ?, ?, ?, ?);";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            JSONObject json = new JSONObject(encrypt.decryptJson("src/data/insurancedata.encrypted"));
            ps.setString(1, json.getString("nombre"));
            ps.setString(2, json.getString("entidad"));
            ps.setString(3, json.getString("cobertura"));
            ps.setString(4, json.getString("exclusiones"));
            ps.setDouble(5, json.getDouble("costo"));
            // Ejecutar sentencia SQL de inserción
            ps.executeUpdate();
        } catch (ClassNotFoundException | SQLException | JSONException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public Insurance listarSeguro(){
        String sql = "Select * from seguro";
        Insurance seguro = new Insurance();
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                seguro.setId(rs.getInt("id"));
                seguro.setNombre(rs.getString("nombre"));
                seguro.setEntidad(rs.getString("entidad"));
                seguro.setCobertura(rs.getString("cobertura"));
                seguro.setExclusiones(rs.getString("exclusiones"));
                seguro.setCosto(rs.getDouble("costo"));
            }
            return seguro;
        } catch (ClassNotFoundException | SQLException e) {
        }
        return seguro;
    }
}
