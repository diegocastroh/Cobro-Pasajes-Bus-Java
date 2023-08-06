/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Config;

import View.PanelAdmin;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import org.json.JSONObject;

/**
 *
 * @author lol_2
 */
public class ApiProvider {
    
    private static final int PORT = 8081;
    private static PanelAdmin admin;
    private static final String API_KEY = "sGvF6gZ4Y2eR7bH9cJ3mP8aX5wN1tU0i"; // Aquí pon tu propia clave de API
    private static HttpServer server;
    
    public static void providerApi() throws IOException, Exception {
        server = HttpServer.create(new InetSocketAddress(PORT), 0);

        // Define un manejador que verifica si el encabezado de autorización tiene una clave de API válida
        server.createContext("/api/provider/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String apiKey = exchange.getRequestHeaders().getFirst("Authorization");
                if (apiKey == null || !apiKey.equals("Bearer " + API_KEY)) {
                    exchange.sendResponseHeaders(401, -1);
                    return;
                }
                handleRequest(exchange);
            }
        });
        admin = new PanelAdmin();
        admin.setVisible(true);
        server.start();
        System.out.println("Servidor iniciado en el puerto " + PORT);
    }
    
    private static void handleRequest(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        String[] queryParams = query.split("&");
        String idStr = null;
        for (String param : queryParams) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2 && keyValue[0].equals("id")) {
                idStr = keyValue[1];
                break;
            }
        }
        if (idStr == null||!idStr.equals(admin.txtid.getText())) {
            exchange.sendResponseHeaders(404, -1); // Enviar error si no se encuentra el parámetro "id"
            return;
        }
        int id = Integer.parseInt(idStr);
        
        int boletos = Integer.parseInt(admin.txtboletosvendidos.getText());
        String ruta = admin.txtrutaactual.getText();
        double ganancias = Double.parseDouble(admin.txtgananciatotal.getText());

        // Crea un objeto JSON con la respuesta
        JSONObject responseData = new JSONObject();
        responseData.put("id", id);
        responseData.put("vendidos", boletos);
        responseData.put("ganancias", ganancias);
        responseData.put("ruta", ruta);
        String json = responseData.toString();

        // Establece la cabecera Content-Type en application/json y devuelve la respuesta
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, json.getBytes().length);
        OutputStream output = exchange.getResponseBody();
        output.write(json.getBytes());
        output.flush();
        output.close();
    }
    
    public static void stopServer() {
        if (server != null) {
            server.stop(0);
            System.out.println("Servidor detenido en el puerto " + PORT);
        }
    }
}
