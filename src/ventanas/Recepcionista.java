/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
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
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.*;
import javax.swing.WindowConstants;

public class Recepcionista extends JFrame {

    private JLabel jLabel_NombreUsuario;
    private JButton jButton_RegistrarPaciente;
    private JButton jButton_GestionarPacientes;
    private JButton jButton_ImprimirPacientes;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel_Wallpaper;

    String user, nombreUsuario;
    int sesionUsuario;

    public Recepcionista() {
        initComponents();
        user = Login.user;
        sesionUsuario = Administrador.sesion_usuario;

        setSize(570, 330);
        setResizable(false);
        setTitle("Recepcionista - Sesión de " + user);
        setLocationRelativeTo(null);

        configurarCierreVentana();
        configurarWallpaper();
        cargarNombreUsuario();
    }

    private void configurarCierreVentana() {
        if (sesionUsuario == 1) {
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        } else {
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
    }

    private void configurarWallpaper() {
        ImageIcon wallpaper = new ImageIcon("src/images/wallpaperPrincipal.jpg");
        Icon icono = new ImageIcon(wallpaper.getImage().getScaledInstance(jLabel_Wallpaper.getWidth(),
                jLabel_Wallpaper.getHeight(), Image.SCALE_DEFAULT));
        jLabel_Wallpaper.setIcon(icono);
        repaint();
    }

    private void cargarNombreUsuario() {
        try (Connection cn = Conexion.conectar();
             PreparedStatement pst = cn.prepareStatement("SELECT nombre_usuario FROM usuarios WHERE username = ?")) {
            pst.setString(1, user);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                nombreUsuario = rs.getString("nombre_usuario");
                jLabel_NombreUsuario.setText("Bienvenido " + nombreUsuario);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar el nombre del recepcionista: " + e);
        }
    }

    private void initComponents() {
        // Inicializar componentes manualmente
        jLabel_NombreUsuario = new JLabel("Bienvenido");
        jButton_RegistrarPaciente = new JButton();
        jButton_GestionarPacientes = new JButton();
        jButton_ImprimirPacientes = new JButton();
        jLabel3 = new JLabel("Registrar Paciente");
        jLabel4 = new JLabel("Gestionar Paciente");
        jLabel5 = new JLabel("Imprimir Pacientes");
        jLabel_Wallpaper = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(null);

        jLabel_NombreUsuario.setFont(new java.awt.Font("Arial", 1, 20));
        jLabel_NombreUsuario.setBounds(10, 10, 300, 30);
        getContentPane().add(jLabel_NombreUsuario);

        // Configurar botones y agregar imágenes
        jButton_RegistrarPaciente.setBounds(40, 80, 120, 100);
        jButton_RegistrarPaciente.setIcon(new ImageIcon(getClass().getResource("/images/add.png")));
        jButton_RegistrarPaciente.addActionListener(evt -> registrarPaciente());
        getContentPane().add(jButton_RegistrarPaciente);

        jButton_GestionarPacientes.setBounds(220, 80, 120, 100);
        jButton_GestionarPacientes.setIcon(new ImageIcon(getClass().getResource("/images/informationuser.png")));
        jButton_GestionarPacientes.addActionListener(evt -> gestionarPacientes());
        getContentPane().add(jButton_GestionarPacientes);

        jButton_ImprimirPacientes.setBounds(400, 80, 120, 100);
        jButton_ImprimirPacientes.setIcon(new ImageIcon(getClass().getResource("/images/impresora.png")));
        jButton_ImprimirPacientes.addActionListener(evt -> imprimirPacientes());
        getContentPane().add(jButton_ImprimirPacientes);

        jLabel3.setBounds(50, 180, 150, 20);
        getContentPane().add(jLabel3);

        jLabel4.setBounds(230, 180, 150, 20);
        getContentPane().add(jLabel4);

        jLabel5.setBounds(420, 180, 150, 20);
        getContentPane().add(jLabel5);

        jLabel_Wallpaper.setBounds(0, 0, 570, 300);
        getContentPane().add(jLabel_Wallpaper);

        pack();
    }

    private void registrarPaciente() {
        new RegistrarPacientes().setVisible(true);
    }

    private void gestionarPacientes() {
        new GestionarPacientes().setVisible(true);
    }

    private void imprimirPacientes() {
        Document documento = new Document();
    try {
        String ruta = System.getProperty("user.home");
        PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/ListaPacientes.pdf"));

        com.itextpdf.text.Image header = com.itextpdf.text.Image.getInstance("src/images/BannerPDF.jpeg");
        header.scaleToFit(650, 1000);
        header.setAlignment(Chunk.ALIGN_CENTER);

        Paragraph parrafo = new Paragraph("Lista de Pacientes\n\n", FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.DARK_GRAY));
        parrafo.setAlignment(Paragraph.ALIGN_CENTER);

        documento.open();
        documento.add(header);
        documento.add(parrafo);

        PdfPTable tabla = new PdfPTable(5);
        tabla.addCell("ID Paciente");
        tabla.addCell("Nombre");
        tabla.addCell("eMail");
        tabla.addCell("Teléfono");
        tabla.addCell("Dirección");

        try (Connection cn = Conexion.conectar();
             PreparedStatement pst = cn.prepareStatement("SELECT * FROM pacientes");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                // Manejo de valores null para evitar errores
                tabla.addCell(rs.getString(1) != null ? rs.getString(1) : "");
                tabla.addCell(rs.getString(2) != null ? rs.getString(2) : "");
                tabla.addCell(rs.getString(3) != null ? rs.getString(3) : "");
                tabla.addCell(rs.getString(4) != null ? rs.getString(4) : "");
                tabla.addCell(rs.getString(5) != null ? rs.getString(5) : "");
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar la base de datos: " + e);
            JOptionPane.showMessageDialog(null, "Error al consultar datos, contacte al administrador.");
        }

        documento.add(tabla);
        documento.close();
        JOptionPane.showMessageDialog(null, "PDF generado correctamente.");
    } catch (DocumentException | IOException e) {
        System.err.println("Error al generar PDF: " + e);
        JOptionPane.showMessageDialog(null, "Error al generar PDF, contacte al administrador.");
    }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new Recepcionista().setVisible(true));
    }
}
