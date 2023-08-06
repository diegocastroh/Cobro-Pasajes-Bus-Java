/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Ticket;
import ModelDao.BoletoDao;
import View.PanelAdmin;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import swing.PanelShadow;

/**
 *
 * @author lol_2
 */
public class TicketController implements ActionListener {

    PanelAdmin view;
    BoletoDao boletoDao;
    Ticket boleto;

    public TicketController(PanelAdmin view, BoletoDao boletoDao, Ticket boleto) {
        this.view = view;
        this.boletoDao = boletoDao;
        this.boleto = boleto;

        ListarBoletos();

    }

    public void ListarBoletos() {
        List<JPanel> panels = new ArrayList<>();
        List<Ticket> boletos = boletoDao.ListarBoletos();
        for (Ticket boleto : boletos) {
            JPanel panel = new JPanel();
            panel.setName("boleto" + boleto.getId());

            // Agregar MouseListener al panel
            panel.addMouseListener(new PanelMouseListener());

            panel.setBackground(Color.white);
            panel.setBorder(new BevelBorder(0));
            panel.setLayout(new GridLayout(0, 2));

            //Panel izquierdo
            JPanel izquierdo = new JPanel();
            izquierdo.setBackground(Color.white);
            izquierdo.setLayout(new BoxLayout(izquierdo, BoxLayout.Y_AXIS));

            JLabel icono = new JLabel("");
            icono.setIcon(new ImageIcon("src/Img/bus (1).png"));
            icono.setAlignmentX(Component.CENTER_ALIGNMENT);
            icono.setAlignmentY(Component.CENTER_ALIGNMENT);
            izquierdo.add(Box.createVerticalGlue());
            izquierdo.add(icono);
            izquierdo.add(Box.createVerticalGlue());

            //Panel derecho
            JPanel derecho = new JPanel();
            derecho.setLayout(new GridLayout(2, 0));

            JPanel ptitulo = new JPanel();
            ptitulo.setBackground(Color.white);
            ptitulo.setLayout(new BoxLayout(ptitulo, BoxLayout.Y_AXIS));
            JLabel tipo = new JLabel(boleto.getTipo());
            tipo.setFont(new Font("SansSerif", Font.BOLD, 20));
            tipo.setAlignmentX(Component.CENTER_ALIGNMENT);
            tipo.setAlignmentY(Component.CENTER_ALIGNMENT);
            ptitulo.add(Box.createVerticalGlue());
            ptitulo.add(tipo);
            ptitulo.add(Box.createVerticalGlue());

            JPanel pprecio = new JPanel();
            //pprecio.setLayout(new BoxLayout(pprecio, BoxLayout.Y_AXIS));
            pprecio.setBackground(Color.white);
            JLabel simbolo = new JLabel("S/.");
            simbolo.setFont(new Font("SansSerif", Font.BOLD, 28));
            JLabel precio = new JLabel(String.valueOf(boleto.getPrecio()));
            precio.setFont(new Font("SansSerif", Font.BOLD, 28));
            //precio.setAlignmentX(Component.CENTER_ALIGNMENT);
            //precio.setAlignmentY(Component.CENTER_ALIGNMENT);
            //pprecio.add(Box.createVerticalGlue());
            pprecio.add(simbolo);
            pprecio.add(precio);
            //pprecio.add(Box.createVerticalGlue());

            derecho.setBackground(Color.white);
            derecho.add(ptitulo);
            derecho.add(pprecio);
            panel.add(izquierdo);
            panel.add(derecho);

            panels.add(panel);
            view.panelBoletos.add(panel);
        }
        view.panelBoletos.updateUI();
    }

    // Clase interna que implementa MouseListener
    private class PanelMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            JPanel panel = (JPanel) e.getSource();
            String panelName = panel.getName();
            // Obtener el panel derecho
            JPanel derecho = (JPanel) panel.getComponent(1);

            // Obtener el precio del JLabel
            JPanel pprecio = (JPanel) derecho.getComponent(1);
            JLabel precioLabel = (JLabel) pprecio.getComponent(1);
            double precio = Double.parseDouble(precioLabel.getText());
            // Lógica para manejar el evento del panel en el controlador
            // Utiliza el panelName para identificar el panel específico
            int confirm = JOptionPane.showConfirmDialog(null, "¿Desea comprar este boleto?");
            if (confirm == 0) {
                double ganancias = Double.parseDouble(view.txtgananciatotal.getText());
                view.txtgananciatotal.setText(String.valueOf(ganancias + precio));
                int cantidad = Integer.parseInt(view.txtboletosvendidos.getText());
                view.txtboletosvendidos.setText(String.valueOf(cantidad + 1));
            }
        }

        // Implementar los métodos restantes de MouseListener
        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
