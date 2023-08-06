/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Bus;
import ModelDao.BusDao;
import View.PanelAdmin;

/**
 *
 * @author lol_2
 */
public class BusController {
    Bus bus;
    BusDao busDao;
    PanelAdmin view;

    public BusController(Bus bus, BusDao busDao, PanelAdmin view) {
        this.bus = bus;
        this.busDao = busDao;
        this.view = view;
        ListarBus();
    }
    
    public void ListarBus(){
        bus = busDao.listarBus();
        view.txtmodelobus.setText(bus.getModelo());
        view.txtfechavencrevtec.setText(bus.getFecha_revi_tecnica());
        view.txtmarcabus.setText(bus.getMarca());
        view.txtplacabus.setText(bus.getPlaca());
        view.txtcantidadbus.setText(""+bus.getCapacidad());
    }
}
