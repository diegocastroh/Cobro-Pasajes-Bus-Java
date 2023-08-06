/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Route;
import ModelDao.RouteDao;
import View.PanelAdmin;

/**
 *
 * @author lol_2
 */
public class RouteController {
    Route route;
    RouteDao routeDao;
    PanelAdmin view;

    public RouteController(Route route, RouteDao routeDao, PanelAdmin view) {
        this.route = route;
        this.routeDao = routeDao;
        this.view = view;
        listarRuta();
    }
    
    public void listarRuta(){
        route = routeDao.listarRuta();
        view.txtdestinoruta.setText(route.getDestino());
        view.txtorigenruta.setText(route.getOrigen());
       view.txtrutaactual.setText(route.getNombre());
        view.txtnombreruta.setText(route.getNombre());
        view.txttiempoestimadoruta.setText(route.getTiempo_estimado());
    }
}
