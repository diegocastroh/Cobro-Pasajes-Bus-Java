/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author lol_2
 */
public class Bus {

    private int id;
    private String modelo;
    private String marca;
    private String ruta;
    private String placa;
    private String fecha_revi_tecnica;
    private int capacidad;

    public Bus() {
    }

    public Bus(int id, String modelo, String marca, String ruta, String placa, String fecha_revi_tecnica, int capacidad) {
        this.id = id;
        this.modelo = modelo;
        this.marca = marca;
        this.ruta = ruta;
        this.placa = placa;
        this.fecha_revi_tecnica = fecha_revi_tecnica;
        this.capacidad = capacidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getFecha_revi_tecnica() {
        return fecha_revi_tecnica;
    }

    public void setFecha_revi_tecnica(String fecha_revi_tecnica) {
        this.fecha_revi_tecnica = fecha_revi_tecnica;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
    
}
