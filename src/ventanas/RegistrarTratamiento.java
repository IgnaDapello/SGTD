package ventanas;

import clases.Conexion;
import java.sql.*;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Calendar;
import javax.swing.*;

public class RegistrarTratamiento extends JFrame {

    private int IDpaciente_update;
    private String user, nom_paciente;
    private JTextField txt_nombrePaciente;
    private JComboBox<String> cmb_tipoTratamiento;
    private JTextPane jTextPane_descripcion;
    private JButton jButton_Registrar;
    private JLabel jLabel_Wallpaper;

    public RegistrarTratamiento() {
        user = Login.user;
        IDpaciente_update = GestionarPacientes.IDpaciente_update;
        initComponents();
        cargarNombrePaciente();
        configurarVentana();
    }

    private void initComponents() {
        setLayout(null);

        JLabel jLabel_Titulo = new JLabel("Registro de Tratamiento");
        jLabel_Titulo.setBounds(200, 10, 300, 30);
        jLabel_Titulo.setFont(new java.awt.Font("Tahoma", 0, 24));
        jLabel_Titulo.setForeground(Color.WHITE);
        add(jLabel_Titulo);

        JLabel jLabel_NombrePaciente = new JLabel("Nombre del paciente:");
        jLabel_NombrePaciente.setBounds(10, 60, 150, 20);
        jLabel_NombrePaciente.setForeground(Color.WHITE);
        add(jLabel_NombrePaciente);

        txt_nombrePaciente = new JTextField();
        txt_nombrePaciente.setBounds(10, 80, 210, 30);
        txt_nombrePaciente.setEditable(false);
        txt_nombrePaciente.setBackground(new Color(153, 153, 255));
        add(txt_nombrePaciente);

        JLabel jLabel_TipoTratamiento = new JLabel("Tipo de Tratamiento:");
        jLabel_TipoTratamiento.setBounds(10, 120, 150, 20);
        jLabel_TipoTratamiento.setForeground(Color.WHITE);
        add(jLabel_TipoTratamiento);

        cmb_tipoTratamiento = new JComboBox<>(new String[]{
                "Limpieza Dental", "Ortodoncia", "Extracción", "Implante", "Blanqueamiento"
        });
        cmb_tipoTratamiento.setBounds(10, 140, 210, 30);
        add(cmb_tipoTratamiento);

        JLabel jLabel_Descripcion = new JLabel("Descripción:");
        jLabel_Descripcion.setBounds(10, 180, 100, 20);
        jLabel_Descripcion.setForeground(Color.WHITE);
        add(jLabel_Descripcion);

        jTextPane_descripcion = new JTextPane();
        JScrollPane jScrollPane1 = new JScrollPane(jTextPane_descripcion);
        jScrollPane1.setBounds(10, 200, 610, 150);
        add(jScrollPane1);

        jButton_Registrar = new JButton("Registrar Tratamiento");
        jButton_Registrar.setBounds(410, 370, 210, 35);
        jButton_Registrar.addActionListener(evt -> registrarTratamiento());
        add(jButton_Registrar);

        jLabel_Wallpaper = new JLabel();
        jLabel_Wallpaper.setBounds(0, 0, 640, 455);
        ImageIcon wallpaper = new ImageIcon("src/images/wallpaperPrincipal.jpg");
        jLabel_Wallpaper.setIcon(new ImageIcon(wallpaper.getImage().getScaledInstance(640, 455, Image.SCALE_DEFAULT)));
        add(jLabel_Wallpaper);
    }

    private void configurarVentana() {
        setTitle("Registrar nuevo tratamiento para " + nom_paciente);
        setSize(640, 455);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("images/icon.png")));
    }

    private void cargarNombrePaciente() {
        try (Connection cn = Conexion.conectar();
             PreparedStatement pst = cn.prepareStatement("SELECT nombre_paciente FROM pacientes WHERE id_paciente = ?")) {
            pst.setInt(1, IDpaciente_update);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                nom_paciente = rs.getString("nombre_paciente");
                txt_nombrePaciente.setText(nom_paciente);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar nombre del paciente: " + e);
            JOptionPane.showMessageDialog(this, "Error al cargar datos del paciente.");
        }
    }

    private void registrarTratamiento() {
        String tipoTratamiento = (String) cmb_tipoTratamiento.getSelectedItem();
        String descripcion = jTextPane_descripcion.getText().trim();
        String estatus = "Nuevo ingreso";

        if (descripcion.isEmpty()) {
            descripcion = "Sin descripción";
        }

        Calendar calendar = Calendar.getInstance();
        String diaIngreso = String.valueOf(calendar.get(Calendar.DATE));
        String mesIngreso = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String anioIngreso = String.valueOf(calendar.get(Calendar.YEAR));

        try (Connection cn = Conexion.conectar();
             PreparedStatement pst = cn.prepareStatement(
                     "INSERT INTO tratamientos (id_paciente, tipo_tratamiento, descripcion, estatus, dia_ingreso, mes_ingreso, anio_ingreso, ultima_modificacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {

            pst.setInt(1, IDpaciente_update);
            pst.setString(2, tipoTratamiento);
            pst.setString(3, descripcion);
            pst.setString(4, estatus);
            pst.setString(5, diaIngreso);
            pst.setString(6, mesIngreso);
            pst.setString(7, anioIngreso);
            pst.setString(8, user);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Tratamiento registrado exitosamente.");
            dispose();
        } catch (SQLException e) {
            System.err.println("Error al registrar tratamiento: " + e);
            JOptionPane.showMessageDialog(this, "Error al registrar tratamiento, contacte al administrador.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistrarTratamiento().setVisible(true));
    }
}
