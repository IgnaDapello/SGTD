package ventanas;

import java.sql.*;
import clases.Conexion;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class GestionarUsuarios extends javax.swing.JFrame {

    String user;
    public static String user_update = "";
    DefaultTableModel model = new DefaultTableModel();

    public GestionarUsuarios() {
        initComponents();
        user = Login.user;

        setSize(645, 335);
        setTitle("Usuarios registrados - Sesión de Recepcionista");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        ImageIcon wallpaper = new ImageIcon("src/images/wallpaperPrincipal.jpg");
        Icon icono = new ImageIcon(wallpaper.getImage().getScaledInstance(jLabel_Wallpaper.getWidth(),
                jLabel_Wallpaper.getHeight(), Image.SCALE_DEFAULT));
        jLabel_Wallpaper.setIcon(icono);
        this.repaint();

        llenarTablaUsuarios();
        agregarMouseListenerTabla();
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("images/icon.png"));
        return retValue;
    }

    private void llenarTablaUsuarios() {
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                    "SELECT id_usuario, nombre_usuario, username, tipo_nivel, estatus FROM usuarios");
            ResultSet rs = pst.executeQuery();

            jTable_usuarios = new JTable(model);
            jScrollPane1.setViewportView(jTable_usuarios);

            model.addColumn("ID");
            model.addColumn("Nombre");
            model.addColumn("Username");
            model.addColumn("Permisos");
            model.addColumn("Estatus");

            while (rs.next()) {
                Object[] fila = new Object[5];
                for (int i = 0; i < 5; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                model.addRow(fila);
            }
            cn.close();

        } catch (SQLException e) {
            System.err.println("Error al llenar la tabla de usuarios: " + e);
            JOptionPane.showMessageDialog(null, "Error al mostrar la información. Contacte al administrador.");
        }
    }

    private void agregarMouseListenerTabla() {
        jTable_usuarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila_point = jTable_usuarios.rowAtPoint(e.getPoint());
                int columna_point = 2;

                if (fila_point > -1) {
                    user_update = (String) model.getValueAt(fila_point, columna_point);
                    InformacionUsuario informacionUsuario = new InformacionUsuario();
                    informacionUsuario.setVisible(true);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel_Footer = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_usuarios = new javax.swing.JTable();
        jLabel_Wallpaper = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Usuarios del Sistema");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, -1, -1));

        jLabel_Footer.setText("Desarrollado por Ignacio Dapello");
        getContentPane().add(jLabel_Footer, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 280, -1, -1));

        jTable_usuarios.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] {"ID", "Nombre", "Username", "Permisos", "Estatus"}
        ));
        jScrollPane1.setViewportView(jTable_usuarios);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 70, 630, 180));
        getContentPane().add(jLabel_Wallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 645, 335));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new GestionarUsuarios().setVisible(true));
    }

    // Variables declaration
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel_Footer;
    private javax.swing.JLabel jLabel_Wallpaper;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_usuarios;
}

