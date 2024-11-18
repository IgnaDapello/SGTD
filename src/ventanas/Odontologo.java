package ventanas;

import clases.Conexion;
import java.sql.*;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class Odontologo extends javax.swing.JFrame {

    String user, nombreUsuario;
    int sesionUsuario;

    public Odontologo() {
        initComponents();
        user = Login.user;

        // Bandera para indicar sesión de administrador o usuario normal
        try {
            sesionUsuario = Administrador.sesion_usuario;
        } catch (Exception e) {
            sesionUsuario = 0; // Valor por defecto si no se accede desde Administrador
        }

        configurarVentana();
        cargarNombreUsuario();
    }

    private void configurarVentana() {
        setSize(570, 330);
        setResizable(false);
        setTitle("Odontólogo - Sesión de " + user);
        setLocationRelativeTo(null);

        // Configuración del cierre de la ventana
        if (sesionUsuario == 1) {
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        } else {
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }

        // Configurar fondo de pantalla
        ImageIcon wallpaper = new ImageIcon("src/images/wallpaperPrincipal.jpg");
        Icon icono = new ImageIcon(wallpaper.getImage().getScaledInstance(jLabel_Wallpaper.getWidth(),
                jLabel_Wallpaper.getHeight(), Image.SCALE_DEFAULT));
        jLabel_Wallpaper.setIcon(icono);
        repaint();
    }

    private void cargarNombreUsuario() {
        try (Connection cn = Conexion.conectar();
             PreparedStatement pst = cn.prepareStatement(
                     "SELECT nombre_usuario FROM usuarios WHERE username = ?")) {
            pst.setString(1, user);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                nombreUsuario = rs.getString("nombre_usuario");
                jLabel_NombreUsuario.setText("Bienvenido " + nombreUsuario);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar el nombre del odontólogo: " + e);
            JOptionPane.showMessageDialog(null, "Error al cargar el nombre del usuario.");
        }
    }

    @Override
    public Image getIconImage() {
        return Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("images/icon.png"));
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel_NombreUsuario = new javax.swing.JLabel();
        jButton_GestionarTratamientos = new javax.swing.JButton();
        jButton_GraficaEstatus = new javax.swing.JButton();
        jButton_GraficaTratamientos = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel_Wallpaper = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel_NombreUsuario.setFont(new java.awt.Font("Arial", 1, 20));
        jLabel_NombreUsuario.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jLabel_NombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jButton_GestionarTratamientos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/apoyo-tecnico.png")));
        jButton_GestionarTratamientos.addActionListener(evt -> gestionarTratamientos());
        getContentPane().add(jButton_GestionarTratamientos, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 120, 100));

        jButton_GraficaEstatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/grafica.png")));
        jButton_GraficaEstatus.addActionListener(evt -> graficarEstatus());
        getContentPane().add(jButton_GraficaEstatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, 120, 100));

        jButton_GraficaTratamientos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/grafica.png")));
        jButton_GraficaTratamientos.addActionListener(evt -> graficarTratamientos());
        getContentPane().add(jButton_GraficaTratamientos, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 80, 120, 100));

        jLabel3.setText("Gestión de Tratamientos");
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, -1, -1));

        jLabel4.setText("Gráfica de estatus");
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 180, -1, -1));

        jLabel5.setText("Gráfica de tratamientos");
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 180, -1, -1));

        jLabel7.setText("Desarrollado por Ignacio Dapello");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, -1, -1));
        getContentPane().add(jLabel_Wallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 300));

        pack();
    }

    private void gestionarTratamientos() {
        new GestionarTratamientos().setVisible(true);
    }

    private void graficarEstatus() {
        new GraficarEstatus().setVisible(true);
    }

    private void graficarTratamientos() {
        new GraficarTratamientos().setVisible(true);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new Odontologo().setVisible(true));
    }

    // Variables declaration
    private javax.swing.JButton jButton_GestionarTratamientos;
    private javax.swing.JButton jButton_GraficaEstatus;
    private javax.swing.JButton jButton_GraficaTratamientos;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel_NombreUsuario;
    private javax.swing.JLabel jLabel_Wallpaper;
}
