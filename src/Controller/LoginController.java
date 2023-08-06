/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Config.ApiConsumer;
import Config.ApiProvider;
import Model.Bus;
import ModelDao.BusDao;
import ModelDao.UsuariosDao;
import View.Login;
import View.PanelAdmin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lol_2
 */
public class LoginController implements ActionListener {
    Login view;
    UsuariosDao userDao;

    public LoginController(Login view, UsuariosDao userDao) {
        this.view = view;
        this.userDao = userDao;
        
        //Inicialización botón login
        this.view.btnlogin.addActionListener(this);
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==view.btnlogin) {
            String placa = view.txtplaca.getText();
            String dni = view.txtdni.getText();
            String password = String.valueOf(view.txtpassword.getPassword());
            
            try {
                if (userDao.ConsultUser(placa, dni, password)) {
                    view.dispose();
                    new splashscreen.SplashScreen(null, true).setVisible(true);
                    ApiProvider api = new ApiProvider();
                    api.providerApi();
                }else{
                    view.txterror.setText("Datos erroneos");
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
}
