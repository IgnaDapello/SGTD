package ventanas;

import java.sql.*;
import clases.Conexion;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.WindowConstants;

public class Administrador extends javax.swing.JFrame {

    String user, nombre_usuario;
    public static int sesion_usuario;

    // Declaración de los componentes
    private javax.swing.JButton jButton_RegistrarUsuario;
    private javax.swing.JButton jButton_GestionarUsuarios;
    private javax.swing.JButton jButton_Creatividad;
    private javax.swing.JButton jButton_Recepcionista;
    private javax.swing.JButton jButton_Odontologo;
    private javax.swing.JButton jButton_AcercaDe;
    private javax.swing.JLabel jLabel_NombreUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel_Wallpaper;

    public Administrador() {
        initComponents();
        user = Login.user;
        sesion_usuario = 1;

        setSize(660, 440);
        setResizable(false);
        setTitle("Administrador - Sesión de " + user);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Configuración del fondo de pantalla
        ImageIcon wallpaper = new ImageIcon("src/images/wallpaperPrincipal.jpg");
        Icon icono = new ImageIcon(wallpaper.getImage().getScaledInstance(jLabel_Wallpaper.getWidth(),
                jLabel_Wallpaper.getHeight(), Image.SCALE_DEFAULT));
        jLabel_Wallpaper.setIcon(icono);
        this.repaint();

        // Obtener el nombre del usuario
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement("SELECT nombre_usuario FROM usuarios WHERE username = ?");
            pst.setString(1, user);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                nombre_usuario = rs.getString("nombre_usuario");
                jLabel_NombreUsuario.setText(nombre_usuario);
            }
            cn.close();
        } catch (SQLException e) {
            System.err.println("Error al obtener el nombre del usuario: " + e);
        }
    }

    @Override
    public Image getIconImage() {
        return Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("images/icon.png"));
    }

    private void initComponents() {
        jLabel_NombreUsuario = new javax.swing.JLabel();
        jButton_RegistrarUsuario = new javax.swing.JButton();
        jButton_GestionarUsuarios = new javax.swing.JButton();
        jButton_Creatividad = new javax.swing.JButton();
        jButton_Recepcionista = new javax.swing.JButton();
        jButton_Odontologo = new javax.swing.JButton();
        jButton_AcercaDe = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel_Wallpaper = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel_NombreUsuario.setFont(new java.awt.Font("Arial", 1, 20));
        jLabel_NombreUsuario.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_NombreUsuario.setText("Nombre Usuario");
        getContentPane().add(jLabel_NombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jButton_RegistrarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addUser.png")));
        jButton_RegistrarUsuario.addActionListener(evt -> jButton_RegistrarUsuarioActionPerformed(evt));
        getContentPane().add(jButton_RegistrarUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 120, 100));

        jButton_GestionarUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/informationuser.png")));
        jButton_GestionarUsuarios.addActionListener(evt -> jButton_GestionarUsuariosActionPerformed(evt));
        getContentPane().add(jButton_GestionarUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 70, 120, 100));

        jButton_Creatividad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/proximamente.png")));
        getContentPane().add(jButton_Creatividad, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 70, 120, 100));

        jButton_Recepcionista.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/capturista.png")));
        jButton_Recepcionista.addActionListener(evt -> jButton_RecepcionistaActionPerformed(evt));
        getContentPane().add(jButton_Recepcionista, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 120, 100));

        jButton_Odontologo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/odontologo.png")));
        jButton_Odontologo.addActionListener(evt -> jButton_OdontologoActionPerformed(evt));
        getContentPane().add(jButton_Odontologo, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 240, 120, 100));

        jButton_AcercaDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/malarisa.png")));
        jButton_AcercaDe.addActionListener(evt -> jButton_AcercaDeActionPerformed(evt));
        getContentPane().add(jButton_AcercaDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 240, 120, 100));

        jLabel1.setText("Registrar Usuario");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, -1, -1));

        jLabel2.setText("Gestionar Usuarios");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 170, -1, -1));

        jLabel3.setText("Creatividad");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 170, -1, -1));

        jLabel4.setText("Panel Recepcionista");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, -1, -1));

        jLabel5.setText("Panel Odontólogo");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 340, -1, -1));

        jLabel6.setText("Acerca De");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 340, -1, -1));

        jLabel7.setText("Desarrollado por Ignacio Dapello");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 380, -1, -1));
        getContentPane().add(jLabel_Wallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 660, 440));

        pack();
    }

    private void jButton_RegistrarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {
        new RegistrarUsuarios().setVisible(true);
    }

    private void jButton_GestionarUsuariosActionPerformed(java.awt.event.ActionEvent evt) {
        new GestionarUsuarios().setVisible(true);
    }

    private void jButton_RecepcionistaActionPerformed(java.awt.event.ActionEvent evt) {
        new Recepcionista().setVisible(true);
    }

    private void jButton_OdontologoActionPerformed(java.awt.event.ActionEvent evt) {
        new Odontologo().setVisible(true);
    }

    private void jButton_AcercaDeActionPerformed(java.awt.event.ActionEvent evt) {
        new AcercaDe().setVisible(true);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new Administrador().setVisible(true));
    }
}

