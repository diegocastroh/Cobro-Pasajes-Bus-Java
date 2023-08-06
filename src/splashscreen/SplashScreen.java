/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package splashscreen;

import ModelDao.BoletoDao;
import ModelDao.BusDao;
import ModelDao.RouteDao;
import ModelDao.SeguroDao;
import ModelDao.UsuariosDao;
import SimplifyTool.JsonEncrypter;
import java.awt.Color;
import javax.swing.JDialog;
import org.json.JSONObject;

/**
 *
 * @author RAVEN
 */
public class SplashScreen extends javax.swing.JDialog {

    /**
     * Creates new form SplashScreen
     */
    BoletoDao boletoDao = new BoletoDao();
    BusDao busDao = new BusDao();
    UsuariosDao userDao = new UsuariosDao();
    SeguroDao seguroDao = new SeguroDao();
    RouteDao rutaDao = new RouteDao();
    public SplashScreen(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        getContentPane().setBackground(new Color(0, 0, 0));
        //  To disable key Alt+F4 to close dialog
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        curvesPanel1 = new splashscreen.CurvesPanel();
        jLabel1 = new javax.swing.JLabel();
        pro = new splashscreen.ProgressBarCustom();
        lbStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        curvesPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/giphy-downsized.gif"))); // NOI18N
        curvesPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 150, 500, 450));
        curvesPanel1.add(pro, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 560, 200, 10));

        lbStatus.setForeground(new java.awt.Color(200, 200, 200));
        lbStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbStatus.setText("Status");
        curvesPanel1.add(lbStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 580, 200, -1));

        getContentPane().add(curvesPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 3, 1280, 800));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        new Thread(new Runnable() {
            @Override
            public void run() {
                String location = "src/data/access.encrypted";
                JsonEncrypter encrypt = new JsonEncrypter();
                try {
                    doTask("Conectar a la Base de Datos", 0);
                    doTask("Creando tabla Usuario", 10);
                    userDao.CrearTablaUsuario();
                    doTask("Creando tabla Tickets", 20);
                    boletoDao.CrearTablaBoleto();
                    doTask("Creando tabla Bus", 30);
                    busDao.CrearTablaBus();
                    doTask("Creando tabla Ruta", 40);
                    rutaDao.CrearTablaRuta();
                    doTask("Creando tabla Seguro", 45);
                    seguroDao.CrearTablaSeguro();
                    doTask("Obteniendo datos", 50);
                    JSONObject json = new JSONObject(encrypt.decryptJson(location));
                    userDao.getUserData(json.getInt("driver_id"));
                    busDao.getBusData(json.getInt("id"));
                    boletoDao.getBoletoData(json.getInt("route_id"));
                    rutaDao.getRutaData(json.getInt("route_id"));
                    seguroDao.getSeguroData(json.getInt("insurance_id"));
                    doTask("Creando Usuario", 60);
                    userDao.CreateUser();
                    doTask("Almacenando datos del Bus", 70);
                    busDao.CreateBus();
                    doTask("Actualizando los boletos", 80);
                    boletoDao.CreateBoleto();
                    doTask("Actualizando Ruta", 90);
                    rutaDao.CreateRuta();
                    doTask("Obteniendo datos del Seguro", 100);
                    seguroDao.CreateSeguro();
                    doTask("Terminando ...", 100);
                    dispose();
                    curvesPanel1.stop();    //  To Stop animation
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }//GEN-LAST:event_formWindowOpened

    private void doTask(String taskName, int progress) throws Exception {
        lbStatus.setText(taskName);
        Thread.sleep(1000); //  For Test
        pro.setValue(progress);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SplashScreen dialog = new SplashScreen(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private splashscreen.CurvesPanel curvesPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lbStatus;
    private splashscreen.ProgressBarCustom pro;
    // End of variables declaration//GEN-END:variables
}
