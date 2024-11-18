package ventanas;

import java.sql.*;
import clases.Conexion;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import java.util.HashMap;
import java.util.Map;

public class GraficarEstatus extends javax.swing.JFrame {

    String user;
    Map<String, Integer> estatusMap = new HashMap<>();
    int iniciado, enEspera, enTratamiento, completado, cancelado;

    // Declaración de componentes
    private javax.swing.JLabel jLabel_Wallpaper;

    public GraficarEstatus() {
        initComponents();
        user = Login.user;

        setTitle("Odontólogo - Sesión de " + user);
        setSize(550, 450);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // Configuración del fondo de pantalla
        ImageIcon wallpaper = new ImageIcon("src/images/wallpaperPrincipal.jpg");
        Icon icono = new ImageIcon(wallpaper.getImage().getScaledInstance(jLabel_Wallpaper.getWidth(),
                jLabel_Wallpaper.getHeight(), Image.SCALE_DEFAULT));
        jLabel_Wallpaper.setIcon(icono);
        this.repaint();

        obtenerDatosEstatus();
        repaint();
    }

    @Override
    public Image getIconImage() {
        return Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("images/icon.png"));
    }

    private void initComponents() {
        jLabel_Wallpaper = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        getContentPane().add(jLabel_Wallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 550, 450));
        pack();
    }

    private void obtenerDatosEstatus() {
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                    "SELECT estatus, COUNT(estatus) AS Cantidad FROM tratamientos GROUP BY estatus");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String estatus = rs.getString("estatus");
                int cantidad = rs.getInt("Cantidad");
                estatusMap.put(estatus, cantidad);
            }

            iniciado = estatusMap.getOrDefault("Iniciado", 0);
            enEspera = estatusMap.getOrDefault("En Espera", 0);
            enTratamiento = estatusMap.getOrDefault("En Tratamiento", 0);
            completado = estatusMap.getOrDefault("Completado", 0);
            cancelado = estatusMap.getOrDefault("Cancelado", 0);

            cn.close();

        } catch (SQLException e) {
            System.err.println("Error al obtener datos de estatus: " + e);
            JOptionPane.showMessageDialog(null, "Error al obtener los datos. Contacte al administrador.");
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int estatusMasRepetido = obtenerEstatusMasRepetido();

        if (estatusMasRepetido == 0) {
            JOptionPane.showMessageDialog(this, "No hay datos para mostrar.");
            return;
        }

        int largoIniciado = iniciado * 400 / estatusMasRepetido;
        int largoEnEspera = enEspera * 400 / estatusMasRepetido;
        int largoEnTratamiento = enTratamiento * 400 / estatusMasRepetido;
        int largoCompletado = completado * 400 / estatusMasRepetido;
        int largoCancelado = cancelado * 400 / estatusMasRepetido;

        // Iniciado - Amarillo
        g.setColor(new Color(240, 249, 0));
        g.fillRect(100, 100, largoIniciado, 40);
        g.drawString("Iniciado", 10, 118);
        g.drawString("Cantidad: " + iniciado, 10, 133);

        // En Espera - Rojo
        g.setColor(new Color(255, 0, 0));
        g.fillRect(100, 150, largoEnEspera, 40);
        g.drawString("En Espera", 10, 168);
        g.drawString("Cantidad: " + enEspera, 10, 183);

        // En Tratamiento - Negro
        g.setColor(new Color(0, 0, 0));
        g.fillRect(100, 200, largoEnTratamiento, 40);
        g.drawString("En Tratamiento", 10, 218);
        g.drawString("Cantidad: " + enTratamiento, 10, 233);

        // Completado - Blanco
        g.setColor(new Color(255, 255, 255));
        g.fillRect(100, 250, largoCompletado, 40);
        g.drawString("Completado", 10, 268);
        g.drawString("Cantidad: " + completado, 10, 283);

        // Cancelado - Verde
        g.setColor(new Color(0, 85, 0));
        g.fillRect(100, 300, largoCancelado, 40);
        g.drawString("Cancelado", 10, 318);
        g.drawString("Cantidad: " + cancelado, 10, 333);
    }

    private int obtenerEstatusMasRepetido() {
        return Math.max(Math.max(Math.max(iniciado, enEspera), Math.max(enTratamiento, completado)), cancelado);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new GraficarEstatus().setVisible(true));
    }
}
