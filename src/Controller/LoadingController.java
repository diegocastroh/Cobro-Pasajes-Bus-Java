/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Config.ApiConsumer;
import Config.Conexion;
import Model.Bus;
import ModelDao.BusDao;
import com.mysql.cj.jdbc.DatabaseMetaData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author lol_2
 */
public class LoadingController {
    ApiConsumer api = new ApiConsumer();
    Bus bus;
    BusDao busdao;
    
    
    Conexion cn = new Conexion();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    public void CrearTablaBus(){
        try {
            con = cn.Conexion();
            DatabaseMetaData metaData = (DatabaseMetaData) con.getMetaData();
            rs = metaData.getTables(null, null, "bus", null);
            if (rs.next()) {
                System.out.println("La tabla bus ya existe.");
            } else {
                System.out.println("La tabla bus no existe. Creando tabla...");
                String sql = "CREATE TABLE bus ("
                        + "id INT NOT NULL AUTO_INCREMENT,"
                        + "modelo VARCHAR(255),"
                        + "marca VARCHAR(255),"
                        + "ruta VARCHAR(255),"
                        + "placa VARCHAR(255),"
                        + "fecha_revi_tecnica VARCHAR(255),"
                        + "capacidad INT,"
                        + "PRIMARY KEY (id))";
                ps = con.prepareStatement(sql);
                ps.executeUpdate(sql);
                System.out.println("La tabla bus ha sido creada exitosamente.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Ha ocurrido un error al verificar o crear la tabla bus: " + e.getMessage());
        }
    }
    
}
