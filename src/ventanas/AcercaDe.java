
package ventanas;

import clases.Conexion;
import java.sql.*;
import java.io.FileOutputStream;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class AcercaDe extends javax.swing.JFrame {
    String user, nombre_usuario;
    int sesion_usuario;
    
    /**
     * Creates new form Recepcionista
     */
    public AcercaDe() {
        initComponents();
        user = Login.user;
        sesion_usuario = Administrador.sesion_usuario;
        
        setSize(400,300);
        setResizable(false);
        setTitle("Acerca de - Sesion de " + user);
        setLocationRelativeTo(null);
        
        if (sesion_usuario == 1) {
            
            //significa que la sesion pertenece a un Administrador (la variable obtiene el valor 1 en la clase Administrador) (solo si inicia sesion un Admin, es una bandera)
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        } else {
            //si no entra, significa que es un capturista 
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
        
         ImageIcon wallpaper = new ImageIcon("src/images/wallpaperPrincipal.jpg");
        //con esto hace que la interfaz se adapte a las dimensiones de la imagen
        Icon icono = new ImageIcon(wallpaper.getImage().getScaledInstance(jLabel_Wallpaper.getWidth(),
                  jLabel_Wallpaper.getHeight(), Image.SCALE_DEFAULT));    
        jLabel_Wallpaper.setIcon(icono);
        this.repaint(); //el This no es 100% necesario aqui, directamente puedes llamar a repaint() asi solo. **RECORDAR que el this hace referencia a ESTE metodo. 
        // hasta aqui
        
        
    }
    
    
    @Override 
    public Image getIconImage(){
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("images/icon.png"));
        return retValue;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel_1 = new javax.swing.JLabel();
        jLabel_2 = new javax.swing.JLabel();
        jLabel_Wallpaper = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel_1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel_1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_1.setText("Sistema desarrollado por Ignacio Dapello");
        getContentPane().add(jLabel_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, -1, -1));

        jLabel_2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel_2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_2.setText("Aqui poner link de portfolio");
        getContentPane().add(jLabel_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, -1, -1));
        getContentPane().add(jLabel_Wallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 300));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(AcercaDe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AcercaDe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AcercaDe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AcercaDe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AcercaDe().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel_1;
    private javax.swing.JLabel jLabel_2;
    private javax.swing.JLabel jLabel_Wallpaper;
    // End of variables declaration//GEN-END:variables
}
