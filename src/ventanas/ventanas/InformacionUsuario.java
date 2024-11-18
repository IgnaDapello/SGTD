/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ventanas;

import java.sql.*;
import clases.Conexion;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.*;

public class InformacionUsuario extends javax.swing.JFrame {

    String user = "", user_update = "";
    int ID;

    // Declaración de componentes
    private javax.swing.JTextField txt_nombre;
    private javax.swing.JTextField txt_mail;
    private javax.swing.JTextField txt_telefono;
    private javax.swing.JTextField txt_username;
    private javax.swing.JTextField txt_RegistradoPor;
    private javax.swing.JComboBox<String> cmb_niveles;
    private javax.swing.JComboBox<String> cmb_estatus;
    private javax.swing.JButton jButton_Actualizar;
    private javax.swing.JButton jButton_RestaurarPassword;
    private javax.swing.JLabel jLabel_Titulo;
    private javax.swing.JLabel jLabel_Wallpaper;

    public InformacionUsuario() {
        initComponents();
        user = Login.user;
        user_update = GestionarUsuarios.user_update;

        setTitle("Información del usuario " + user_update + " - Sesión de " + user);
        setSize(630, 450);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        configurarWallpaper();
        jLabel_Titulo.setText("Información del Usuario " + user_update);
        cargarInformacionUsuario();
    }

    private void configurarWallpaper() {
        ImageIcon wallpaper = new ImageIcon("src/images/wallpaperPrincipal.jpg");
        Icon icono = new ImageIcon(wallpaper.getImage().getScaledInstance(jLabel_Wallpaper.getWidth(),
                jLabel_Wallpaper.getHeight(), Image.SCALE_DEFAULT));
        jLabel_Wallpaper.setIcon(icono);
        repaint();
    }

    private void cargarInformacionUsuario() {
        try (Connection cn = Conexion.conectar();
             PreparedStatement pst = cn.prepareStatement("SELECT * FROM usuarios WHERE username = ?")) {
            pst.setString(1, user_update);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                ID = rs.getInt("id_usuario");
                txt_nombre.setText(rs.getString("nombre_usuario"));
                txt_mail.setText(rs.getString("email"));
                txt_telefono.setText(rs.getString("telefono"));
                txt_username.setText(rs.getString("username"));
                txt_RegistradoPor.setText(rs.getString("registrado_por"));
                cmb_niveles.setSelectedItem(rs.getString("tipo_nivel"));
                cmb_estatus.setSelectedItem(rs.getString("estatus"));
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar la información del usuario: " + e);
            JOptionPane.showMessageDialog(null, "Error al cargar datos, contactar al administrador.");
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jLabel_Titulo = new javax.swing.JLabel("Información del Usuario");
        txt_nombre = new javax.swing.JTextField();
        txt_mail = new javax.swing.JTextField();
        txt_telefono = new javax.swing.JTextField();
        txt_username = new javax.swing.JTextField();
        txt_RegistradoPor = new javax.swing.JTextField();
        cmb_niveles = new javax.swing.JComboBox<>(new String[]{"Administrador", "Capturista", "Técnico"});
        cmb_estatus = new javax.swing.JComboBox<>(new String[]{"Activo", "Inactivo"});
        jButton_Actualizar = new javax.swing.JButton("Actualizar Usuario");
        jButton_RestaurarPassword = new javax.swing.JButton("Restaurar Contraseña");
        jLabel_Wallpaper = new javax.swing.JLabel();

        jButton_Actualizar.addActionListener(evt -> jButton_ActualizarActionPerformed(evt));
        jButton_RestaurarPassword.addActionListener(evt -> jButton_RestaurarPasswordActionPerformed(evt));

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        getContentPane().add(jLabel_Titulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));
        getContentPane().add(txt_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 200, -1));
        getContentPane().add(txt_mail, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 200, -1));
        getContentPane().add(txt_telefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 200, -1));
        getContentPane().add(txt_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 200, -1));
        getContentPane().add(txt_RegistradoPor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 200, -1));
        getContentPane().add(cmb_niveles, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 200, -1));
        getContentPane().add(cmb_estatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 200, -1));
        getContentPane().add(jButton_Actualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 300, 150, 30));
        getContentPane().add(jButton_RestaurarPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 350, 150, 30));
        getContentPane().add(jLabel_Wallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 630, 450));

        pack();
    }

    private void jButton_ActualizarActionPerformed(java.awt.event.ActionEvent evt) {
        String nombre = txt_nombre.getText().trim();
        String mail = txt_mail.getText().trim();
        String telefono = txt_telefono.getText().trim();
        String username = txt_username.getText().trim();
        String permisos_string = cmb_niveles.getSelectedItem().toString();
        String estatus_string = cmb_estatus.getSelectedItem().toString();

        if (nombre.isEmpty() || mail.isEmpty() || telefono.isEmpty() || username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debes llenar todos los campos.");
            return;
        }

        try (Connection cn = Conexion.conectar();
             PreparedStatement pst = cn.prepareStatement(
                     "UPDATE usuarios SET nombre_usuario=?, email=?, telefono=?, username=?, tipo_nivel=?, estatus=? WHERE id_usuario=?")) {
            pst.setString(1, nombre);
            pst.setString(2, mail);
            pst.setString(3, telefono);
            pst.setString(4, username);
            pst.setString(5, permisos_string);
            pst.setString(6, estatus_string);
            pst.setInt(7, ID);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Modificación correcta.");
            this.dispose();
        } catch (SQLException e) {
            System.err.println("Error al actualizar: " + e);
            JOptionPane.showMessageDialog(null, "Error al actualizar datos, contactar al administrador.");
        }
    }

    private void jButton_RestaurarPasswordActionPerformed(java.awt.event.ActionEvent evt) {
        new RestaurarPassword().setVisible(true);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new InformacionUsuario().setVisible(true));
    }
}
