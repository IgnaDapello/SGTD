/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ventanas;

import java.sql.*;
import clases.Conexion;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Informacion_Paciente extends javax.swing.JFrame {

    DefaultTableModel model = new DefaultTableModel();
    int IDpaciente_update = 0;
    public static int IDtratamiento = 0;
    String user = "";

    // Declaración de componentes
    private JLabel jLabel_Titulo;
    private JLabel jLabel_Wallpaper;
    private JTextField txt_nombre;
    private JTextField txt_mail;
    private JTextField txt_telefono;
    private JTextField txt_direccion;
    private JTextField txt_ultimaModificacion;
    private JTable jTable_equipos;
    private JScrollPane jScrollPane_equipos;
    private JButton jButton_GenerarReporte;

    public Informacion_Paciente() {
        initComponents();
        user = Login.user;
        IDpaciente_update = GestionarPacientes.IDpaciente_update;

        setSize(640, 460);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        configurarWallpaper();
        cargarDatosPaciente();
        cargarTablaTratamientos();
    }

    private void configurarWallpaper() {
        ImageIcon wallpaper = new ImageIcon("src/images/wallpaperPrincipal.jpg");
        Icon icono = new ImageIcon(wallpaper.getImage().getScaledInstance(jLabel_Wallpaper.getWidth(),
                jLabel_Wallpaper.getHeight(), Image.SCALE_DEFAULT));
        jLabel_Wallpaper.setIcon(icono);
        repaint();
    }

    private void cargarDatosPaciente() {
        try (Connection cn = Conexion.conectar();
             PreparedStatement pst = cn.prepareStatement("SELECT * FROM pacientes WHERE id_paciente = ?")) {
            pst.setInt(1, IDpaciente_update);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                setTitle("Información del Paciente " + rs.getString("nombre_paciente") + " - Sesión de " + user);
                jLabel_Titulo.setText("Información del Paciente " + rs.getString("nombre_paciente"));

                txt_nombre.setText(rs.getString("nombre_paciente"));
                txt_mail.setText(rs.getString("mail_paciente"));
                txt_telefono.setText(rs.getString("tel_paciente"));
                txt_direccion.setText(rs.getString("dir_paciente"));
                txt_ultimaModificacion.setText(rs.getString("ultima_modificacion"));
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar datos del paciente: " + e);
            JOptionPane.showMessageDialog(null, "Error al cargar datos del paciente.");
        }
    }

    private void cargarTablaTratamientos() {
        try (Connection cn = Conexion.conectar();
             PreparedStatement pst = cn.prepareStatement("SELECT id_tratamiento, tipo_tratamiento, descripcion, estatus FROM tratamientos WHERE id_paciente = ?")) {
            pst.setInt(1, IDpaciente_update);
            ResultSet rs = pst.executeQuery();

            jTable_equipos = new JTable(model);
            jScrollPane_equipos.setViewportView(jTable_equipos);

            model.setColumnIdentifiers(new Object[]{"ID Tratamiento", "Tipo", "Descripción", "Estatus"});

            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)});
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar tratamientos: " + e);
            JOptionPane.showMessageDialog(null, "Error al cargar tratamientos.");
        }

        jTable_equipos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = jTable_equipos.rowAtPoint(e.getPoint());
                if (fila > -1) {
                    IDtratamiento = (int) model.getValueAt(fila, 0);
                    new InformacionTratamiento().setVisible(true);
                }
            }
        });
    }

    private void initComponents() {
        jLabel_Titulo = new JLabel();
        jLabel_Wallpaper = new JLabel();
        txt_nombre = new JTextField();
        txt_mail = new JTextField();
        txt_telefono = new JTextField();
        txt_direccion = new JTextField();
        txt_ultimaModificacion = new JTextField();
        jScrollPane_equipos = new JScrollPane();
        jButton_GenerarReporte = new JButton("Generar Reporte");

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        getContentPane().add(jLabel_Titulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));
        getContentPane().add(txt_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 200, -1));
        getContentPane().add(txt_mail, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 200, -1));
        getContentPane().add(txt_telefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 200, -1));
        getContentPane().add(txt_direccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 200, -1));
        getContentPane().add(txt_ultimaModificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 200, -1));
        getContentPane().add(jScrollPane_equipos, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, 360, 200));
        getContentPane().add(jButton_GenerarReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 300, 200, 30));
        getContentPane().add(jLabel_Wallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 460));

        jButton_GenerarReporte.addActionListener(evt -> generarReporte());
        pack();
    }

    private void generarReporte() {
        Document documento = new Document();
        try {
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/" + txt_nombre.getText().trim() + ".pdf"));

            documento.open();
            agregarEncabezado(documento);
            agregarDatosPaciente(documento);
            agregarTablaTratamientos(documento);

            documento.close();
            JOptionPane.showMessageDialog(null, "Reporte creado correctamente.");
        } catch (DocumentException | IOException e) {
            System.err.println("Error al generar el PDF: " + e);
            JOptionPane.showMessageDialog(null, "Error al generar el reporte.");
        }
    }

    private void agregarEncabezado(Document documento) throws DocumentException, IOException {
        com.itextpdf.text.Image header = com.itextpdf.text.Image.getInstance("src/images/BannerPDF.jpeg");
        header.scaleToFit(650, 1000);
        header.setAlignment(Element.ALIGN_CENTER);
        documento.add(header);
    }

    private void agregarDatosPaciente(Document documento) throws DocumentException {
        Paragraph parrafo = new Paragraph("Información del Paciente\n\n", FontFactory.getFont("Tahoma", 14, Font.BOLD, BaseColor.DARK_GRAY));
        parrafo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(parrafo);

        PdfPTable tablaPaciente = new PdfPTable(5);
        tablaPaciente.addCell("ID");
        tablaPaciente.addCell("Nombre");
        tablaPaciente.addCell("eMail");
        tablaPaciente.addCell("Teléfono");
        tablaPaciente.addCell("Dirección");

        tablaPaciente.addCell(String.valueOf(IDpaciente_update));
        tablaPaciente.addCell(txt_nombre.getText());
        tablaPaciente.addCell(txt_mail.getText());
        tablaPaciente.addCell(txt_telefono.getText());
        tablaPaciente.addCell(txt_direccion.getText());
        documento.add(tablaPaciente);
    }

    private void agregarTablaTratamientos(Document documento) throws DocumentException {
        PdfPTable tablaTratamientos = new PdfPTable(4);
        tablaTratamientos.addCell("ID Tratamiento");
        tablaTratamientos.addCell("Tipo");
        tablaTratamientos.addCell("Descripción");
        tablaTratamientos.addCell("Estatus");

        for (int i = 0; i < model.getRowCount(); i++) {
            tablaTratamientos.addCell(model.getValueAt(i, 0).toString());
            tablaTratamientos.addCell(model.getValueAt(i, 1).toString());
            tablaTratamientos.addCell(model.getValueAt(i, 2).toString());
            tablaTratamientos.addCell(model.getValueAt(i, 3).toString());
        }
        documento.add(tablaTratamientos);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new Informacion_Paciente().setVisible(true));
    }
}