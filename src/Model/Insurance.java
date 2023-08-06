/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author lol_2
 */
public class Insurance {
    private int id;
    private String nombre;
    private String entidad;
    private String cobertura;
    private String exclusiones;
    private double costo;

    public Insurance() {
    }

    public Insurance(int id, String nombre, String entidad, String cobertura, String exclusiones, double costo) {
        this.id = id;
        this.nombre = nombre;
        this.entidad = entidad;
        this.cobertura = cobertura;
        this.exclusiones = exclusiones;
        this.costo = costo;
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

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getCobertura() {
        return cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    public String getExclusiones() {
        return exclusiones;
    }

    public void setExclusiones(String exclusiones) {
        this.exclusiones = exclusiones;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
    
    
}
