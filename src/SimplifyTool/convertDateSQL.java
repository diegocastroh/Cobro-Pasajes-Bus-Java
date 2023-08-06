/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SimplifyTool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author lol_2
 */
public class convertDateSQL {

    public java.sql.Date convertDate(String Date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsed = dateFormat.parse(Date);
        java.sql.Date fechaConvertida = new java.sql.Date(parsed.getTime());
        return fechaConvertida;
    }

    public java.sql.Time convertTime(String hora) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
        java.util.Date fecha = formato.parse(hora);
        return new java.sql.Time(fecha.getTime());
    }

}
