/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Config.ApiConsumer;
import Config.ApiProvider;
import SimplifyTool.JsonEncrypter;
import View.Login;
import View.PanelAdmin;
import java.io.File;
import java.io.FileReader;
import org.json.JSONObject;

/**
 *
 * @author lol_2
 */
public class main {

    public static void main(String[] args) throws Exception {
        JsonEncrypter encrypter = new JsonEncrypter();

        File archivo = new File("src/data/access.encrypted");
        if (archivo.exists()) {
            // Leer el contenido del archivo y convertirlo a un objeto JSON
            JSONObject acceso = new JSONObject(encrypter.decryptJson("src/data/access.encrypted"));
            if (acceso.getString("acceso").equals("autorizado")) {
                ApiProvider provider = new ApiProvider();
                provider.providerApi();
            }
        } else {
            Login login = new Login();
            login.setVisible(true);
        }

    }
}
