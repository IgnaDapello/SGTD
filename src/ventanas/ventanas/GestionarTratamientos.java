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

public class GestionarTratamientos extends javax.swing.JFrame {

    String user;
    public static int IDtratamiento_update = 0;
    DefaultTableModel model = new DefaultTableModel();

    public GestionarTratamientos() {
        initComponents();
        user = Login.user;

        setSize(630, 380);
        setResizable(false);
        setTitle("Recepcionista - Sesión de " + user);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        ImageIcon wallpaper = new ImageIcon("src/images/wallpaperPrincipal.jpg");
        Icon icono = new ImageIcon(wallpaper.getImage().getScaledInstance(jLabel_Wallpaper.getWidth(),
                jLabel_Wallpaper.getHeight(), Image.SCALE_DEFAULT));
        jLabel_Wallpaper.setIcon(icono);
        this.repaint();

        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                    "SELECT id_tratamiento, tipo_tratamiento, descripcion, estatus FROM tratamientos");
            ResultSet rs = pst.executeQuery();

            jTable_tratamientos = new JTable(model);
            jScrollPane_tratamientos.setViewportView(jTable_tratamientos);

            model.addColumn("ID");
            model.addColumn("Tipo");
            model.addColumn("Descripción");
            model.addColumn("Estatus");

            while (rs.next()) {
                Object[] fila = new Object[4];
                for (int i = 0; i < 4; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                model.addRow(fila);
            }
            cn.close();

        } catch (SQLException e) {
            System.err.println("Error al llenar la tabla de tratamientos: " + e);
            JOptionPane.showMessageDialog(null, "Error al cargar la información. Contacte al administrador.");
        }

        obtenerDatosTabla();
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("images/icon.png"));
        return retValue;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane_tratamientos = new javax.swing.JScrollPane();
        jTable_tratamientos = new javax.swing.JTable();
        jLabel_Footer = new javax.swing.JLabel();
        cmb_estatus = new javax.swing.JComboBox<>();
        btnMostrar = new javax.swing.JButton();
        jLabel_Wallpaper = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Tratamientos Registrados");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, -1, -1));

        jTable_tratamientos.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] {"ID", "Tipo", "Descripción", "Estatus"}
        ));
        jScrollPane_tratamientos.setViewportView(jTable_tratamientos);

        getContentPane().add(jScrollPane_tratamientos, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 70, 630, 180));

        jLabel_Footer.setText("Desarrollado por Ignacio Dapello");
        getContentPane().add(jLabel_Footer, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 340, -1, -1));

        cmb_estatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"Todos", "Iniciado", "En progreso", "Completado", "Cancelado"}));
        getContentPane().add(cmb_estatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 40, 130, -1));

        btnMostrar.setBackground(new java.awt.Color(153, 153, 255));
        btnMostrar.setFont(new java.awt.Font("Microsoft JhengHei", 0, 18));
        btnMostrar.setForeground(new java.awt.Color(255, 255, 255));
        btnMostrar.setText("Mostrar");
        btnMostrar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnMostrar.addActionListener(evt -> mostrarActionPerformed());
        getContentPane().add(btnMostrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 260, 210, 35));

        getContentPane().add(jLabel_Wallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 380));

        pack();
    }

    private void mostrarActionPerformed() {
        String seleccion = cmb_estatus.getSelectedItem().toString();
        String query;

        model.setRowCount(0);

        try {
            Connection cn = Conexion.conectar();
            if ("Todos".equals(seleccion)) {
                query = "SELECT id_tratamiento, tipo_tratamiento, descripcion, estatus FROM tratamientos";
            } else {
                query = "SELECT id_tratamiento, tipo_tratamiento, descripcion, estatus FROM tratamientos WHERE estatus = '" + seleccion + "'";
            }

            PreparedStatement pst = cn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[4];
                for (int i = 0; i < 4; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                model.addRow(fila);
            }
            cn.close();

        } catch (SQLException e) {
            System.err.println("Error al recuperar los registros de tratamientos: " + e);
        }
    }

    public void obtenerDatosTabla() {
        jTable_tratamientos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila_point = jTable_tratamientos.rowAtPoint(e.getPoint());
                int columna_point = 0;

                if (fila_point > -1) {
                    IDtratamiento_update = (int) model.getValueAt(fila_point, columna_point);
                    InformacionTratamiento info = new InformacionTratamiento();
                    info.setVisible(true);
                }
            }
        });
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new GestionarTratamientos().setVisible(true));
    }

    // Variables declaration
    private javax.swing.JButton btnMostrar;
    private javax.swing.JComboBox<String> cmb_estatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel_Footer;
    private javax.swing.JLabel jLabel_Wallpaper;
    private javax.swing.JScrollPane jScrollPane_tratamientos;
    private javax.swing.JTable jTable_tratamientos;
}

