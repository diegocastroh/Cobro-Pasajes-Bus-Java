/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SimplifyTool;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.*;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;

public class JsonEncrypter {

    private static final String ALGO = "AES";
    private static final String KEY = "w&G9hL$x2Dp@fE7sT5qR"; // Clave secreta para cifrar y descifrar

    // Función para encriptar un objeto JSON y guardarlo en un archivo
    public static void encryptJson(String json, String outputFilePath) throws Exception {
        // Generar una clave válida de 128 bits a partir de la cadena de texto de la clave
        MessageDigest sha = MessageDigest.getInstance("SHA-256");

        // Convertir la clave en bytes y crear un objeto SecretKeySpec
        byte[] keyBytes = sha.digest(KEY.getBytes(StandardCharsets.UTF_8));
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGO);

        // Crear un objeto Cipher y configurarlo para cifrar en modo ECB (Electronic Codebook)
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        // Cifrar el JSON en una matriz de bytes
        byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedBytes = cipher.doFinal(jsonBytes);

        // Codificar los bytes cifrados en Base64 y escribirlos en un archivo con extensión personalizada
        String encodedData = Base64.getEncoder().encodeToString(encryptedBytes);
        String outputFileName = outputFilePath + ".encrypted";
        File outputFile = new File(outputFileName);
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(encodedData.getBytes(StandardCharsets.UTF_8));
        }
    }

    // Función para desencriptar un archivo y devolver un objeto JSON
    public static String decryptJson(String filePath) throws Exception {
        // Generar una clave válida de 128 bits a partir de la cadena de texto de la clave
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        
        // Leer los bytes cifrados del archivo con extensión personalizada
        byte[] encryptedBytes = Files.readAllBytes(Paths.get(filePath));

        // Decodificar los bytes cifrados de Base64
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedBytes);

        // Convertir la clave en bytes y crear un objeto SecretKeySpec
        byte[] keyBytes = sha.digest(KEY.getBytes(StandardCharsets.UTF_8));
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGO);

        // Crear un objeto Cipher y configurarlo para descifrar en modo ECB (Electronic Codebook)
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        // Descifrar los bytes decodificados y convertirlos en una cadena JSON
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        String json = new String(decryptedBytes, StandardCharsets.UTF_8);

        return json;
    }

}
