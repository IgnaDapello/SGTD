package ventanas;

import clases.Conexion;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.*;
import javax.swing.*;

public class RegistrarUsuarios extends JFrame {

    private String user;
    private JTextField txt_nombre, txt_mail, txt_telefono, txt_username;
    private JPasswordField txt_password;
    private JComboBox<String> cmb_niveles;
    private JButton jButton1;
    private JLabel jLabel_Wallpaper;

    public RegistrarUsuarios() {
        user = Login.user;
        initComponents();
        configurarVentana();
    }

    private void configurarVentana() {
        setTitle("Registrar nuevo usuario - Sesión de " + user);
        setSize(630, 360);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("images/icon.png")));

        ImageIcon wallpaper = new ImageIcon("src/images/wallpaperPrincipal.jpg");
        jLabel_Wallpaper.setIcon(new ImageIcon(wallpaper.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT)));
    }

    private void initComponents() {
        setLayout(null);

        JLabel jLabel1 = new JLabel("Registro Nuevo Usuario");
        jLabel1.setBounds(210, 10, 200, 30);
        add(jLabel1);

        JLabel jLabel2 = new JLabel("Nombre:");
        jLabel2.setBounds(20, 50, 100, 20);
        add(jLabel2);

        txt_nombre = new JTextField();
        txt_nombre.setBounds(20, 70, 210, 30);
        add(txt_nombre);

        JLabel jLabel3 = new JLabel("eMail:");
        jLabel3.setBounds(20, 110, 100, 20);
        add(jLabel3);

        txt_mail = new JTextField();
        txt_mail.setBounds(20, 130, 210, 30);
        add(txt_mail);

        JLabel jLabel4 = new JLabel("Teléfono:");
        jLabel4.setBounds(20, 170, 100, 20);
        add(jLabel4);

        txt_telefono = new JTextField();
        txt_telefono.setBounds(20, 190, 210, 30);
        add(txt_telefono);

        JLabel jLabel5 = new JLabel("Permisos de:");
        jLabel5.setBounds(20, 230, 100, 20);
        add(jLabel5);

        cmb_niveles = new JComboBox<>(new String[]{"Administrador", "Capturista", "Técnico"});
        cmb_niveles.setBounds(20, 250, 150, 30);
        add(cmb_niveles);

        JLabel jLabel6 = new JLabel("Username:");
        jLabel6.setBounds(380, 50, 100, 20);
        add(jLabel6);

        txt_username = new JTextField();
        txt_username.setBounds(380, 70, 210, 30);
        add(txt_username);

        JLabel jLabel7 = new JLabel("Password:");
        jLabel7.setBounds(380, 110, 100, 20);
        add(jLabel7);

        txt_password = new JPasswordField();
        txt_password.setBounds(380, 130, 210, 30);
        add(txt_password);

        jButton1 = new JButton("Registrar");
        jButton1.setBounds(470, 170, 120, 100);
        jButton1.addActionListener(evt -> registrarUsuario());
        add(jButton1);

        JLabel jLabel_footer = new JLabel("Desarrollado por Ignacio Dapello");
        jLabel_footer.setBounds(240, 300, 200, 20);
        add(jLabel_footer);

        jLabel_Wallpaper = new JLabel();
        jLabel_Wallpaper.setBounds(0, 0, 630, 350);
        add(jLabel_Wallpaper);
    }

    private void registrarUsuario() {
        int permisos_cmb = cmb_niveles.getSelectedIndex() + 1;
        String nombre = txt_nombre.getText().trim();
        String mail = txt_mail.getText().trim();
        String telefono = txt_telefono.getText().trim();
        String username = txt_username.getText().trim();
        String pass = new String(txt_password.getPassword()).trim();
        String permisos_string = "";
        int validacion = 0;

        if (nombre.isEmpty()) validacion++;
        if (mail.isEmpty()) validacion++;
        if (telefono.isEmpty()) validacion++;
        if (username.isEmpty()) validacion++;
        if (pass.isEmpty()) validacion++;

        switch (permisos_cmb) {
            case 1 -> permisos_string = "Administrador";
            case 2 -> permisos_string = "Recepcionista";
            case 3 -> permisos_string = "Odontologo";
        }

        if (validacion == 0) {
            try (Connection cn = Conexion.conectar()) {
                PreparedStatement pst = cn.prepareStatement("SELECT username FROM usuarios WHERE username = ?");
                pst.setString(1, username);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Nombre de usuario no disponible.");
                } else {
                    try (PreparedStatement pst2 = cn.prepareStatement("INSERT INTO usuarios VALUES (?,?,?,?,?,?,?,?,?)")) {
                        pst2.setInt(1, 0);
                        pst2.setString(2, nombre);
                        pst2.setString(3, mail);
                        pst2.setString(4, telefono);
                        pst2.setString(5, username);
                        pst2.setString(6, pass);
                        pst2.setString(7, permisos_string);
                        pst2.setString(8, "Activo");
                        pst2.setString(9, user);
                        pst2.executeUpdate();

                        limpiarCampos();
                        JOptionPane.showMessageDialog(this, "Registro exitoso!");
                        dispose();
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error al registrar usuario: " + e);
                JOptionPane.showMessageDialog(this, "Error al registrar, contacte al administrador.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debes llenar todos los campos.");
        }
    }

    private void limpiarCampos() {
        txt_nombre.setText("");
        txt_mail.setText("");
        txt_telefono.setText("");
        txt_username.setText("");
        txt_password.setText("");
        cmb_niveles.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistrarUsuarios().setVisible(true));
    }
}

