/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ventanas;

import clases.Conexion;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.*;
import javax.swing.*;

public class RegistrarPacientes extends JFrame {

    private JTextField txt_nombre, txt_mail, txt_telefono, txt_direccion;
    private JButton jButton_Registrar;
    private JLabel jLabel_Wallpaper, jLabel_footer;
    private String user;

    public RegistrarPacientes() {
        user = Login.user;
        initComponents();
        configurarVentana();
    }

    private void initComponents() {
        setLayout(null);

        JLabel jLabel1 = new JLabel("Registrar Paciente");
        jLabel1.setBounds(180, 10, 200, 30);
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24));
        jLabel1.setForeground(Color.WHITE);
        add(jLabel1);

        JLabel jLabel2 = new JLabel("Nombre:");
        jLabel2.setBounds(20, 50, 100, 20);
        jLabel2.setForeground(Color.WHITE);
        add(jLabel2);

        txt_nombre = new JTextField();
        txt_nombre.setBounds(20, 70, 210, 30);
        add(txt_nombre);

        JLabel jLabel3 = new JLabel("eMail:");
        jLabel3.setBounds(20, 110, 100, 20);
        jLabel3.setForeground(Color.WHITE);
        add(jLabel3);

        txt_mail = new JTextField();
        txt_mail.setBounds(20, 130, 210, 30);
        add(txt_mail);

        JLabel jLabel4 = new JLabel("Teléfono:");
        jLabel4.setBounds(20, 170, 100, 20);
        jLabel4.setForeground(Color.WHITE);
        add(jLabel4);

        txt_telefono = new JTextField();
        txt_telefono.setBounds(20, 190, 210, 30);
        add(txt_telefono);

        JLabel jLabel5 = new JLabel("Dirección:");
        jLabel5.setBounds(20, 230, 100, 20);
        jLabel5.setForeground(Color.WHITE);
        add(jLabel5);

        txt_direccion = new JTextField();
        txt_direccion.setBounds(20, 250, 210, 30);
        add(txt_direccion);

        jButton_Registrar = new JButton("Registrar");
        jButton_Registrar.setBounds(350, 100, 120, 50);
        jButton_Registrar.addActionListener(evt -> registrarPaciente());
        add(jButton_Registrar);

        jLabel_footer = new JLabel("Desarrollado por Ignacio Dapello");
        jLabel_footer.setBounds(180, 290, 300, 30);
        jLabel_footer.setForeground(Color.WHITE);
        add(jLabel_footer);

        jLabel_Wallpaper = new JLabel();
        jLabel_Wallpaper.setBounds(0, 0, 530, 350);
        ImageIcon wallpaper = new ImageIcon("src/images/wallpaperPrincipal.jpg");
        jLabel_Wallpaper.setIcon(new ImageIcon(wallpaper.getImage().getScaledInstance(530, 350, Image.SCALE_DEFAULT)));
        add(jLabel_Wallpaper);
    }

    private void configurarVentana() {
        setTitle("Registrar nuevo paciente - Sesión de " + user);
        setSize(530, 350);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("images/icon.png")));
    }

    private void registrarPaciente() {
        String nombre = txt_nombre.getText().trim();
        String mail = txt_mail.getText().trim();
        String telefono = txt_telefono.getText().trim();
        String direccion = txt_direccion.getText().trim();

        if (nombre.isEmpty() || mail.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben ser llenados.");
            return;
        }

        try (Connection cn = Conexion.conectar();
             PreparedStatement pst = cn.prepareStatement(
                     "INSERT INTO pacientes (nombre, telefono, email, direccion, registrado_por) VALUES (?, ?, ?, ?, ?)")) {

            pst.setString(1, nombre);
            pst.setString(2, telefono);
            pst.setString(3, mail);
            pst.setString(4, direccion);
            pst.setString(5, user);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Registro exitoso.");
            limpiarCampos();
        } catch (SQLException e) {
            System.err.println("Error al registrar paciente: " + e);
            JOptionPane.showMessageDialog(this, "Error al registrar paciente, contacte al administrador.");
        }
    }

    private void limpiarCampos() {
        txt_nombre.setText("");
        txt_mail.setText("");
        txt_telefono.setText("");
        txt_direccion.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistrarPacientes().setVisible(true));
    }
}
