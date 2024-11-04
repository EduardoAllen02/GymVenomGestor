/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.panels;

/**
 *
 * @author Yeyian PC
 */
import javax.swing.*;
import java.awt.*;
public class AsistenciasPanel extends JPanel {
    public AsistenciasPanel() {
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Registro de Asistencias", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Contenido adicional como una lista o tabla de asistencias
        JTextArea attendanceArea = new JTextArea();
        add(new JScrollPane(attendanceArea), BorderLayout.CENTER);
    }
}