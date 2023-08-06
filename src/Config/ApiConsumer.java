package Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 *
 * @author lol_2
 */
public class ApiConsumer {

    private static String apiurl = "http://127.0.0.1:8000/api/consumer/?data=";

    public String consumerApi(String table, HashMap<String, Object> data) throws MalformedURLException, IOException {

        // Obtener las credenciales de autenticación
        String apiKey = "1|XPu9Lz7Bfny5AkKiIRWNOFZFf3L0lIXgT8U8q56F";

        StringBuilder sb = new StringBuilder();

        for (String i : data.keySet()) {
            sb.append("&");
            sb.append(i);
            sb.append("=");
            sb.append(data.get(i));
        }
        // Crear la conexión HTTP
        String geturl = apiurl + table + sb.toString();
        URL url = new URL(geturl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        // Establecer los encabezados de autenticación
        con.setRequestProperty("Authorization", "Bearer " + apiKey);
        // Procesar la respuesta HTTP
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Convertir la respuesta a un objeto JSON
        return response.toString();
    }

   
    
}
