package ventanas;

import clases.Conexion;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.*;
import javax.swing.*;

public class InformacionTratamiento extends javax.swing.JFrame {

    int IDpaciente_update = 0, IDtratamiento = 0;
    String user = "", nom_paciente = "";

    // Declaración de componentes
    private javax.swing.JComboBox<String> cmb_tipoTratamiento;
    private javax.swing.JComboBox<String> cmb_descripcion;
    private javax.swing.JComboBox<String> cmb_estatus;
    private javax.swing.JTextField txt_fecha;
    private javax.swing.JTextField txt_NombrePaciente;
    private javax.swing.JTextPane jTextPane_observaciones;
    private javax.swing.JTextPane jTextPane_comentariosOdontologo;
    private javax.swing.JTextField txt_ultima_Modificacion;
    private javax.swing.JLabel jLabel_Wallpaper;
    private javax.swing.JLabel jLabel_revisionTecnicaDe;
    private javax.swing.JButton jButton_Actualizar;

    public InformacionTratamiento() {
        initComponents();
        user = Login.user;
        IDtratamiento = Informacion_Paciente.IDtratamiento;
        IDpaciente_update = GestionarPacientes.IDpaciente_update;

        obtenerDatosPaciente();
        obtenerDatosTratamiento();
        configurarVentana();
    }

    private void obtenerDatosPaciente() {
        try (Connection cn = Conexion.conectar()) {
            PreparedStatement pst = cn.prepareStatement("SELECT nombre_paciente FROM pacientes WHERE id_paciente = ?");
            pst.setInt(1, IDpaciente_update);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                nom_paciente = rs.getString("nombre_paciente");
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar el nombre del paciente: " + e);
            JOptionPane.showMessageDialog(null, "Error con la base de datos.");
        }
    }

    private void obtenerDatosTratamiento() {
        try (Connection cn = Conexion.conectar()) {
            PreparedStatement pst = cn.prepareStatement("SELECT * FROM tratamientos WHERE id_tratamiento = ?");
            pst.setInt(1, IDtratamiento);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                cmb_tipoTratamiento.setSelectedItem(rs.getString("tipo_tratamiento"));
                cmb_descripcion.setSelectedItem(rs.getString("descripcion"));
                cmb_estatus.setSelectedItem(rs.getString("estatus"));
                txt_ultima_Modificacion.setText(rs.getString("ultima_modificacion"));

                String dia = rs.getString("dia_ingreso");
                String mes = rs.getString("mes_ingreso");
                String annio = rs.getString("annio_ingreso");
                txt_fecha.setText(dia + " del " + mes + " del " + annio);

                jTextPane_observaciones.setText(rs.getString("observaciones"));
                jTextPane_comentariosOdontologo.setText(rs.getString("comentarios_odontologos"));
                jLabel_revisionTecnicaDe.setText("Comentarios y actualización del odontólogo: " + rs.getString("revision_tecnica_de"));
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar la información del tratamiento: " + e);
            JOptionPane.showMessageDialog(null, "Error con la base de datos.");
        }
    }

    private void configurarVentana() {
        setTitle("Tratamiento del paciente " + nom_paciente);
        setSize(670, 530);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        ImageIcon wallpaper = new ImageIcon("src/images/wallpaperPrincipal.jpg");
        Icon icono = new ImageIcon(wallpaper.getImage().getScaledInstance(jLabel_Wallpaper.getWidth(),
                jLabel_Wallpaper.getHeight(), Image.SCALE_DEFAULT));
        jLabel_Wallpaper.setIcon(icono);
        repaint();

        txt_NombrePaciente.setText(nom_paciente);
    }

    private void initComponents() {
        cmb_tipoTratamiento = new javax.swing.JComboBox<>(new String[]{"Limpieza", "Ortodoncia", "Extracción", "Implante", "Endodoncia", "Empaste", "Blanqueamiento"});
        cmb_descripcion = new javax.swing.JComboBox<>(new String[]{"Descripción 1", "Descripción 2", "Descripción 3"});
        cmb_estatus = new javax.swing.JComboBox<>(new String[]{"Iniciado", "En Espera", "En Tratamiento", "Completado", "Cancelado"});
        txt_fecha = new javax.swing.JTextField();
        txt_NombrePaciente = new javax.swing.JTextField();
        jTextPane_observaciones = new javax.swing.JTextPane();
        jTextPane_comentariosOdontologo = new javax.swing.JTextPane();
        txt_ultima_Modificacion = new javax.swing.JTextField();
        jLabel_Wallpaper = new javax.swing.JLabel();
        jLabel_revisionTecnicaDe = new javax.swing.JLabel();
        jButton_Actualizar = new javax.swing.JButton("Actualizar");

        jButton_Actualizar.addActionListener(evt -> jButton_ActualizarActionPerformed(evt));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        getContentPane().add(jLabel_Wallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 530));
        pack();
    }

    private void jButton_ActualizarActionPerformed(java.awt.event.ActionEvent evt) {
        String tipo_tratamiento = cmb_tipoTratamiento.getSelectedItem().toString();
        String descripcion = cmb_descripcion.getSelectedItem().toString();
        String estatus = cmb_estatus.getSelectedItem().toString();
        String observaciones = jTextPane_observaciones.getText();

        if (observaciones.isEmpty()) {
            observaciones = "Sin observaciones";
        }

        try (Connection cn = Conexion.conectar()) {
            PreparedStatement pst = cn.prepareStatement(
                    "UPDATE tratamientos SET tipo_tratamiento=?, descripcion=?, observaciones=?, estatus=?, ultima_modificacion=? WHERE id_tratamiento = ?");
            pst.setString(1, tipo_tratamiento);
            pst.setString(2, descripcion);
            pst.setString(3, observaciones);
            pst.setString(4, estatus);
            pst.setString(5, user);
            pst.setInt(6, IDtratamiento);

            pst.executeUpdate();
            Limpiar();
            txt_ultima_Modificacion.setText(user);
            JOptionPane.showMessageDialog(null, "Actualización Correcta!");
            this.dispose();
        } catch (SQLException e) {
            System.err.println("Error al actualizar tratamiento: " + e);
            JOptionPane.showMessageDialog(null, "Error al actualizar tratamiento, contactar al administrador.");
        }
    }

    public void Limpiar() {
        txt_fecha.setText("");
        txt_NombrePaciente.setText("");
        jTextPane_observaciones.setText("");
        txt_ultima_Modificacion.setText("");
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new InformacionTratamiento().setVisible(true));
    }
}

