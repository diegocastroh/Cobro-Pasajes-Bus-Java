/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelDao;

import Config.ApiConsumer;
import Config.Conexion;
import Model.Ticket;
import SimplifyTool.JsonEncrypter;
import com.mysql.cj.jdbc.DatabaseMetaData;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author lol_2
 */
public class BoletoDao {

    ApiConsumer api = new ApiConsumer();
    JsonEncrypter encrypt = new JsonEncrypter();
    Conexion cn = new Conexion();
    
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public void CrearTablaBoleto() {
        try {
            con = cn.Conexion();
            DatabaseMetaData metaData = (DatabaseMetaData) con.getMetaData();
            rs = metaData.getTables(null, null, "Boleto", null);
            if (rs.next()) {
                System.out.println("La tabla Boleto ya existe.");
            } else {
                System.out.println("La tabla Boleto no existe. Creando tabla...");
                String sql = "CREATE TABLE Boleto ("
                        + "id INT NOT NULL AUTO_INCREMENT,"
                        + "ruta_id INT,"
                        + "precio DOUBLE,"
                        + "tipo VARCHAR(255),"
                        + "PRIMARY KEY (id))";
                ps = con.prepareStatement(sql);
                ps.executeUpdate(sql);
                // Cerrar el PreparedStatement y la conexión a la base de datos
                ps.close();
                con.close();
                System.out.println("La tabla Boleto ha sido creada exitosamente.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Ha ocurrido un error al verificar o crear la tabla Boleto: " + e.getMessage());
        }
    }

    public void getBoletoData(int id) throws Exception {
        try {
            HashMap<String, Object> data = new HashMap<>();
            data.put("id", id);

            String jsonArray = api.consumerApi("tickets", data);

            // Verificar si el directorio "datos" existe, y crearlo en caso contrario
            File datosDir = new File("src/data");
            if (!datosDir.exists()) {
                datosDir.mkdir();
            }
            //Utilizo la función de encriptar convirtiendo el JSON en String y la ubicación si extensión 
            encrypt.encryptJson(jsonArray, "src/data/ticketdata");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void CreateBoleto() throws SQLException, Exception {
        // Preparar sentencia SQL de inserción
        String sql = "INSERT INTO boleto (ruta_id, precio, tipo) VALUES (?, ?, ?);";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);

            JSONArray json = new JSONArray(encrypt.decryptJson("src/data/ticketdata.encrypted"));

            for (Object obj : json) {
                JSONObject jsonObj = (JSONObject) obj;
                ps.setInt(1, jsonObj.getInt("ruta_id"));
                ps.setDouble(2, jsonObj.getDouble("precio"));
                ps.setString(3, jsonObj.getString("tipo"));
                // Ejecutar sentencia SQL de inserción
                ps.executeUpdate();
            }
            // Ejecutar sentencia SQL de inserción
            ps.executeUpdate();
            // Cerrar el PreparedStatement y la conexión a la base de datos
            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException | JSONException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Ticket> ListarBoletos() {
        // Crear la sentencia SQL para obtener los datos de la tabla "boleto"
        String sql = "SELECT id, ruta_id, precio, tipo FROM boleto";
        // Crear una lista para almacenar los boletos
        List<Ticket> boletos = new ArrayList<>();
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Ticket boleto = new Ticket();
                boleto.setId(rs.getInt("id"));
                boleto.setPrecio(rs.getDouble("precio"));
                boleto.setRuta_id(rs.getInt("ruta_id"));
                boleto.setTipo(rs.getString("tipo"));
                boletos.add(boleto);
            }
            // Cerrar el PreparedStatement y la conexión a la base de datos
            ps.close();
            con.close();
            return boletos;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return boletos;
    }
}
