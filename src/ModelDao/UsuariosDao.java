/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelDao;

import Config.ApiConsumer;
import Config.Conexion;
import Model.Usuarios;
import SimplifyTool.JsonEncrypter;
import SimplifyTool.convertDateSQL;
import com.mysql.cj.jdbc.DatabaseMetaData;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author lol_2
 */
public class UsuariosDao {

    JsonEncrypter encrypt = new JsonEncrypter();
    ApiConsumer api = new ApiConsumer();
    Conexion cn = new Conexion();
    
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public boolean ConsultUser(String placa, String dni, String password) throws Exception {
        boolean acceso = false;
        try {
            HashMap<String, Object> data = new HashMap<>();
            data.put("dni", dni);
            data.put("placa", placa);
            data.put("password", password);

            JSONObject jsonObject = new JSONObject(api.consumerApi("verification", data));

            // Realizar la validación
            if (!jsonObject.getString("acceso").equals("no_autorizado")) {

                // Verificar si el directorio "datos" existe, y crearlo en caso contrario
                File datosDir = new File("src/data");
                if (!datosDir.exists()) {
                    datosDir.mkdir();
                }
                //Utilizo la función de encriptar convirtiendo el JSON en String y la ubicación si extensión 
                encrypt.encryptJson(jsonObject.toString(), "src/data/access");
                acceso = true;
            }

            return acceso;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return acceso;
    }

    public void getUserData(int id) throws Exception {
        try {
            HashMap<String, Object> data = new HashMap<>();
            data.put("id", id);

            String json = api.consumerApi("driver", data);

            // Verificar si el directorio "datos" existe, y crearlo en caso contrario
            File datosDir = new File("src/data");
            if (!datosDir.exists()) {
                datosDir.mkdir();
            }
            //Utilizo la función de encriptar convirtiendo el JSON en String y la ubicación si extensión 
            encrypt.encryptJson(json, "src/data/userdata");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void CrearTablaUsuario() {
        try {
            con = cn.Conexion();
            DatabaseMetaData metaData = (DatabaseMetaData) con.getMetaData();
            rs = metaData.getTables(null, null, "Usuario", null);
            if (rs.next()) {
                System.out.println("La tabla Usuario ya existe.");
            } else {
                System.out.println("La tabla Usuario no existe. Creando tabla...");
                String sql = "CREATE TABLE Usuario ("
                        + "id INT NOT NULL AUTO_INCREMENT,"
                        + "nombre VARCHAR(255),"
                        + "dni INT,"
                        + "licencia VARCHAR(255),"
                        + "tipo_licencia VARCHAR(255),"
                        + "fecha_vencimiento_licencia DATE,"
                        + "seguro VARCHAR(255),"
                        + "hora_entrada TIME,"
                        + "hora_salida TIME,"
                        + "PRIMARY KEY (id))";
                ps = con.prepareStatement(sql);
                ps.executeUpdate(sql);
                System.out.println("La tabla Usuario ha sido creada exitosamente.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Ha ocurrido un error al verificar o crear la tabla Usuario: " + e.getMessage());
        }
    }

    public void CreateUser() throws Exception {
        // Preparar sentencia SQL de inserción
        String sql = "INSERT INTO usuario (nombre, dni, licencia, tipo_licencia, fecha_vencimiento_licencia, seguro, hora_entrada, hora_salida) VALUES (?, ?, ?, ?, ?, ?, ?,?);";
        convertDateSQL cdsql = new convertDateSQL();
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            JSONObject json = new JSONObject(encrypt.decryptJson("src/data/userdata.encrypted"));
            ps.setString(1, json.getString("nombre"));
            ps.setString(2, json.getString("dni"));
            ps.setString(3, json.getString("licencia_conducir"));
            ps.setString(4, json.getString("tipo_licencia"));
            ps.setDate(5, cdsql.convertDate(json.getString("fecha_vencimiento_licencia")));
            ps.setInt(6, json.getInt("tipo_seguro_id"));
            ps.setTime(7, cdsql.convertTime(json.getString("hora_ingreso")));
            ps.setTime(8, cdsql.convertTime(json.getString("hora_salida")));
            // Ejecutar sentencia SQL de inserción
            ps.executeUpdate();
        } catch (ClassNotFoundException | SQLException | JSONException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }
    public Usuarios listarUser(){
        String sql = "Select * from usuario";
        Usuarios user = new Usuarios();
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setNombre(rs.getString("nombre"));
                user.setDni(rs.getInt("dni"));
                user.setLicencia(rs.getString("licencia"));
                user.setTipo_licencia(rs.getString("tipo_licencia"));
                user.setFecha_vencimiento_licencia(rs.getString("fecha_vencimiento_licencia"));
                user.setSeguro(rs.getString("seguro"));
                user.setHora_entrada(rs.getString("hora_entrada"));
                user.setHora_salida(rs.getString("hora_salida"));
            }
            return user;
        } catch (ClassNotFoundException | SQLException e) {
        }
        return user;
    }
}
