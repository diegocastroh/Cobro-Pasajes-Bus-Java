/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Usuarios;
import ModelDao.UsuariosDao;
import SimplifyTool.JsonEncrypter;
import View.PanelAdmin;
import org.json.JSONObject;

/**
 *
 * @author lol_2
 */
public class UserController {
    PanelAdmin view;
    Usuarios user;
    UsuariosDao userDao;

    public UserController(PanelAdmin view, Usuarios user, UsuariosDao userDao) throws Exception {
        this.view = view;
        this.user = user;
        this.userDao = userDao;
        ListarUsuario();
    }
    
    public void ListarUsuario() throws Exception{
        user = userDao.listarUser();
        view.txtnombrechofer1.setText(user.getNombre());
        view.txtdnichofer1.setText(String.valueOf(user.getDni()));
        view.txtactulicenciachofer1.setText(user.getLicencia());
        view.txtlicenciachofer1.setText(user.getTipo_licencia());
        view.txtveclicenciachofer1.setText(user.getFecha_vencimiento_licencia());
        view.txthoraentradachofer1.setText(user.getHora_entrada());
        view.txthorasalidachofer1.setText(user.getHora_salida());
        JsonEncrypter encrypter = new JsonEncrypter();
        JSONObject json = new JSONObject(encrypter.decryptJson("src/data/access.encrypted"));
        view.txtid.setText(String.valueOf(json.getInt("id")));
        view.txtid.setVisible(false);
    }
}
