/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author lol_2
 */
public class Usuarios {
    private int id;
    private String nombre;
    private int dni;
    private String licencia;
    private String tipo_licencia;
    private String fecha_vencimiento_licencia;
    private String seguro;
    private String hora_entrada;
    private String hora_salida;

    public Usuarios() {
    }

    public Usuarios(int id, String nombre, int dni, String licencia, String tipo_licencia, String fecha_vencimiento_licencia, String seguro, String hora_entrada, String hora_salida) {
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
        this.licencia = licencia;
        this.tipo_licencia = tipo_licencia;
        this.fecha_vencimiento_licencia = fecha_vencimiento_licencia;
        this.seguro = seguro;
        this.hora_entrada = hora_entrada;
        this.hora_salida = hora_salida;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public String getTipo_licencia() {
        return tipo_licencia;
    }

    public void setTipo_licencia(String tipo_licencia) {
        this.tipo_licencia = tipo_licencia;
    }

    public String getFecha_vencimiento_licencia() {
        return fecha_vencimiento_licencia;
    }

    public void setFecha_vencimiento_licencia(String fecha_vencimiento_licencia) {
        this.fecha_vencimiento_licencia = fecha_vencimiento_licencia;
    }

    public String getSeguro() {
        return seguro;
    }

    public void setSeguro(String seguro) {
        this.seguro = seguro;
    }

    public String getHora_entrada() {
        return hora_entrada;
    }

    public void setHora_entrada(String hora_entrada) {
        this.hora_entrada = hora_entrada;
    }

    public String getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(String hora_salida) {
        this.hora_salida = hora_salida;
    }

}
