/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Insurance;
import ModelDao.SeguroDao;
import View.PanelAdmin;

/**
 *
 * @author lol_2
 */
public class InsuranceController {
    PanelAdmin view;
    Insurance insurance;
    SeguroDao insuranceDao;

    public InsuranceController(PanelAdmin view, Insurance insurance, SeguroDao insuranceDao) {
        this.view = view;
        this.insurance = insurance;
        this.insuranceDao = insuranceDao;
        ListarSeguro();
    }

    
    public void ListarSeguro(){
        insurance = insuranceDao.listarSeguro();
        view.txtnombreseguro.setText(insurance.getNombre());
        view.txtcoberturaseguro.setText(insurance.getCobertura());
        view.txtentidadseguro.setText(insurance.getEntidad());
        view.txtexclusionesseguro.setText(insurance.getExclusiones());
        
    }
}
