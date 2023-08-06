/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Config.ApiProvider;
import Config.Conexion;
import View.Login;
import View.PanelAdmin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author lol_2
 */
public class AdminController implements ActionListener {

    PanelAdmin view;

    public AdminController(PanelAdmin view) {
        this.view = view;
        view.btnCerrarSesion.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.btnCerrarSesion) {
            String rutaCarpeta = "src/data";
            File carpeta = new File(rutaCarpeta);
            if (carpeta.exists() && carpeta.isDirectory()) {
                eliminarCarpetasRecursivamente(carpeta);
                System.out.println("La carpeta y su contenido han sido eliminados.");
            } else {
                System.out.println("La carpeta no existe o no es un directorio válido.");
            }
            ApiProvider provider = new ApiProvider();
            Conexion con = new Conexion();
            con.deleteDataBase();
            provider.stopServer();
            Login login = new Login();
            login.setVisible(true);
            view.dispose();
        }
    }

    private static void eliminarCarpetasRecursivamente(File carpeta) {
        File[] archivos = carpeta.listFiles();
        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isDirectory()) {
                    eliminarCarpetasRecursivamente(archivo);
                } else {
                    archivo.delete();
                }
            }
        }
        carpeta.delete();
    }

}
