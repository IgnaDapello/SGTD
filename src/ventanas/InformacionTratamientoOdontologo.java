package ventanas;

import clases.Conexion;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.*;
import javax.swing.*;

public class InformacionTratamientoOdontologo extends javax.swing.JFrame {

    int IDtratamiento = 0;
    String user = "";

    // Declaración de componentes
    private JComboBox<String> cmb_tipoTratamiento;
    private JComboBox<String> cmb_estatus;
    private JTextField txt_modelo;
    private JTextField txt_numSerie;
    private JTextField txt_fechaIngreso;
    private JTextField txt_ultimaModificacion;
    private JTextPane jTextPane_observaciones;
    private JTextPane jTextPane_comentariosOdontologo;
    private JLabel jLabel_Wallpaper;
    private JLabel jLabel_revisionOdontologo;
    private JButton jButton_Actualizar;
    private JLabel jLabel_Titulo;

    public InformacionTratamientoOdontologo() {
        initComponents();
        user = Login.user;
        IDtratamiento = GestionarTratamientos.IDtratamiento_update;

        cargarInformacionTratamiento();
        configurarVentana();
    }

    private void cargarInformacionTratamiento() {
        try (Connection cn = Conexion.conectar();
             PreparedStatement pst = cn.prepareStatement(
                     "SELECT * FROM tratamientos WHERE id_tratamiento = ?")) {
            pst.setInt(1, IDtratamiento);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                cmb_tipoTratamiento.setSelectedItem(rs.getString("tipo_tratamiento"));
                cmb_estatus.setSelectedItem(rs.getString("estatus"));
                txt_modelo.setText(rs.getString("modelo"));
                txt_numSerie.setText(rs.getString("num_serie"));
                txt_ultimaModificacion.setText(rs.getString("ultima_modificacion"));

                String dia = rs.getString("dia_ingreso");
                String mes = rs.getString("mes_ingreso");
                String anio = rs.getString("anio_ingreso");
                txt_fechaIngreso.setText(dia + " de " + mes + " de " + anio);

                jTextPane_observaciones.setText(rs.getString("observaciones"));
                jTextPane_comentariosOdontologo.setText(rs.getString("comentarios_tecnicos"));
                jLabel_revisionOdontologo.setText("Comentarios del odontólogo: " + rs.getString("revision_tecnica_de"));
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar la información del tratamiento: " + e);
            JOptionPane.showMessageDialog(null, "Error al cargar los datos del tratamiento.");
        }
    }

    private void configurarVentana() {
        setTitle("Tratamiento ID " + IDtratamiento + " - Sesión de " + user);
        setSize(670, 530);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        ImageIcon wallpaper = new ImageIcon("src/images/wallpaperPrincipal.jpg");
        jLabel_Wallpaper.setIcon(new ImageIcon(wallpaper.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT)));
        repaint();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jLabel_Titulo = new JLabel("Información del Tratamiento");
        cmb_tipoTratamiento = new JComboBox<>(new String[]{"Limpieza", "Ortodoncia", "Extracción", "Implante"});
        cmb_estatus = new JComboBox<>(new String[]{"Iniciado", "En Espera", "En Tratamiento", "Completado", "Cancelado"});
        txt_modelo = new JTextField();
        txt_numSerie = new JTextField();
        txt_fechaIngreso = new JTextField();
        txt_ultimaModificacion = new JTextField();
        jTextPane_observaciones = new JTextPane();
        jTextPane_comentariosOdontologo = new JTextPane();
        jLabel_Wallpaper = new JLabel();
        jLabel_revisionOdontologo = new JLabel("Comentarios del odontólogo:");
        jButton_Actualizar = new JButton("Actualizar Tratamiento");

        jButton_Actualizar.addActionListener(evt -> actualizarTratamiento());

        setLayout(null);

        jLabel_Titulo.setBounds(200, 10, 250, 30);
        add(jLabel_Titulo);
        cmb_tipoTratamiento.setBounds(10, 80, 150, 30);
        add(cmb_tipoTratamiento);
        cmb_estatus.setBounds(520, 80, 150, 30);
        add(cmb_estatus);
        txt_modelo.setBounds(10, 140, 150, 30);
        add(txt_modelo);
        txt_numSerie.setBounds(10, 200, 150, 30);
        add(txt_numSerie);
        txt_fechaIngreso.setBounds(10, 260, 150, 30);
        add(txt_fechaIngreso);
        txt_ultimaModificacion.setBounds(10, 320, 150, 30);
        add(txt_ultimaModificacion);
        jTextPane_observaciones.setBounds(200, 140, 450, 100);
        add(jTextPane_observaciones);
        jTextPane_comentariosOdontologo.setBounds(200, 260, 450, 100);
        add(jTextPane_comentariosOdontologo);
        jLabel_revisionOdontologo.setBounds(200, 380, 450, 30);
        add(jLabel_revisionOdontologo);
        jButton_Actualizar.setBounds(440, 410, 210, 35);
        add(jButton_Actualizar);
        jLabel_Wallpaper.setBounds(0, 0, 670, 530);
        add(jLabel_Wallpaper);
    }

    private void actualizarTratamiento() {
        String estatus = cmb_estatus.getSelectedItem().toString();
        String comentarios = jTextPane_comentariosOdontologo.getText();

        try (Connection cn = Conexion.conectar();
             PreparedStatement pst = cn.prepareStatement(
                     "UPDATE tratamientos SET estatus = ?, comentarios_tecnicos = ?, revision_tecnica_de = ? WHERE id_tratamiento = ?")) {
            pst.setString(1, estatus);
            pst.setString(2, comentarios);
            pst.setString(3, user);
            pst.setInt(4, IDtratamiento);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Actualización exitosa.");
            dispose();
        } catch (SQLException e) {
            System.err.println("Error al actualizar tratamiento: " + e);
            JOptionPane.showMessageDialog(null, "Error al actualizar, contacte al administrador.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InformacionTratamientoOdontologo().setVisible(true));
    }
}
