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

public class GraficarTratamientos extends javax.swing.JFrame {

    String user;
    int[] vectorTratamientosCantidad = new int[11];
    String[] vectorTratamientosNombre = new String[11];
    int limpieza, extraccion, ortodoncia, empaste, blanqueamiento, endodoncia, implantes, carillas, puentes, coronas, otros;

    // Declaración del componente faltante
    private javax.swing.JLabel jLabel_Wallpaper;

    public GraficarTratamientos() {
        initComponents();

        user = Login.user;
        setTitle("Odontólogo - Sesión de " + user);
        setSize(550, 450);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        configurarWallpaper();
        cargarDatosTratamientos();
        repaint();
    }

    private void configurarWallpaper() {
        ImageIcon wallpaper = new ImageIcon("src/images/wallpaperPrincipal.jpg");
        Icon icono = new ImageIcon(wallpaper.getImage().getScaledInstance(jLabel_Wallpaper.getWidth(),
                jLabel_Wallpaper.getHeight(), Image.SCALE_DEFAULT));
        jLabel_Wallpaper.setIcon(icono);
        repaint();
    }

    private void initComponents() {
        jLabel_Wallpaper = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        getContentPane().add(jLabel_Wallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 550, 450));
        pack();
    }

    private void cargarDatosTratamientos() {
        try (Connection cn = Conexion.conectar();
             PreparedStatement pst = cn.prepareStatement(
                     "SELECT tipo_tratamiento, COUNT(tipo_tratamiento) AS Cantidad FROM tratamientos GROUP BY tipo_tratamiento")) {
            ResultSet rs = pst.executeQuery();
            int posicion = 0;
            while (rs.next()) {
                vectorTratamientosNombre[posicion] = rs.getString(1);
                vectorTratamientosCantidad[posicion] = rs.getInt(2);

                switch (vectorTratamientosNombre[posicion].toLowerCase()) {
                    case "limpieza" -> limpieza = vectorTratamientosCantidad[posicion];
                    case "extraccion" -> extraccion = vectorTratamientosCantidad[posicion];
                    case "ortodoncia" -> ortodoncia = vectorTratamientosCantidad[posicion];
                    case "empaste" -> empaste = vectorTratamientosCantidad[posicion];
                    case "blanqueamiento" -> blanqueamiento = vectorTratamientosCantidad[posicion];
                    case "endodoncia" -> endodoncia = vectorTratamientosCantidad[posicion];
                    case "implantes" -> implantes = vectorTratamientosCantidad[posicion];
                    case "carillas" -> carillas = vectorTratamientosCantidad[posicion];
                    case "puentes" -> puentes = vectorTratamientosCantidad[posicion];
                    case "coronas" -> coronas = vectorTratamientosCantidad[posicion];
                    case "otros" -> otros = vectorTratamientosCantidad[posicion];
                }
                posicion++;
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e);
            JOptionPane.showMessageDialog(null, "Error al consultar datos. Contacte al administrador.");
        }
    }

    @Override
    public Image getIconImage() {
        return Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("images/icon.png"));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int totalTratamientos = limpieza + extraccion + ortodoncia + empaste + blanqueamiento + endodoncia + implantes + carillas + puentes + coronas + otros;
        if (totalTratamientos == 0) {
            JOptionPane.showMessageDialog(this, "No hay datos para mostrar.");
            return;
        }

        int inicio = 0;
        int[] grados = {
                limpieza * 360 / totalTratamientos,
                extraccion * 360 / totalTratamientos,
                ortodoncia * 360 / totalTratamientos,
                empaste * 360 / totalTratamientos,
                blanqueamiento * 360 / totalTratamientos,
                endodoncia * 360 / totalTratamientos,
                implantes * 360 / totalTratamientos,
                carillas * 360 / totalTratamientos,
                puentes * 360 / totalTratamientos,
                coronas * 360 / totalTratamientos,
                otros * 360 / totalTratamientos
        };

        Color[] colores = {
                new Color(175, 122, 197), new Color(0, 255, 0), new Color(0, 255, 255),
                new Color(55, 0, 255), new Color(255, 255, 255), new Color(247, 220, 111),
                new Color(21, 42, 160), new Color(215, 96, 0), new Color(215, 96, 140),
                new Color(215, 196, 125), new Color(93, 173, 226)
        };

        String[] nombres = {
                "Limpieza", "Extracción", "Ortodoncia", "Empaste",
                "Blanqueamiento", "Endodoncia", "Implantes", "Carillas",
                "Puentes", "Coronas", "Otros"
        };

        for (int i = 0; i < grados.length; i++) {
            g.setColor(colores[i]);
            g.fillArc(25, 100, 270, 270, inicio, grados[i]);
            g.fillRect(310, 120 + (i * 30), 20, 20);
            g.drawString(vectorTratamientosCantidad[i] + " " + nombres[i], 340, 135 + (i * 30));
            inicio += grados[i];
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new GraficarTratamientos().setVisible(true));
    }
}
