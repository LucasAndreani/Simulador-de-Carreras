package simuladorcarreras.view;

import simuladorcarreras.controller.ConfiguracionCarrera;
import simuladorcarreras.model.*;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class SimularCarreraView extends JFrame {

    private JTextArea textArea;
    private JButton btnIniciarPausar;
    private JButton btnReiniciar;
    private JLabel lblEstado;
    private JProgressBar progressBar;
    private JPanel panelInfo;

    private Carrera carrera;
    private int vueltaActual = 0;
    private Timer timer;
    private boolean carreraIniciada = false;
    private boolean carreraPausada = false;

    private final Color COLOR_FONDO = new Color(45, 45, 45);
    private final Color COLOR_TEXTO = Color.WHITE;
    private final Color COLOR_ACENTO = new Color(0, 123, 255);
    private final Color COLOR_EXITO = new Color(40, 167, 69);
    private final Color COLOR_ADVERTENCIA = new Color(255, 193, 7);

    public SimularCarreraView() {
        initializeComponents();
        setupLayout();
        setupCarrera();
        setupTimer();

        setVisible(true);
    }

    private void initializeComponents() {
        setTitle("Simulacion de Carrera F1");
        setSize(800, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(COLOR_FONDO);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(new Color(30, 30, 30));
        textArea.setForeground(COLOR_TEXTO);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        textArea.setMargin(new Insets(10, 10, 10, 10));

        btnIniciarPausar = createStyledButton("Iniciar Carrera", Color.GRAY);
        btnReiniciar = createStyledButton("Reiniciar", Color.GRAY);

        lblEstado = new JLabel("Carrera lista para iniciar");
        lblEstado.setForeground(COLOR_TEXTO);
        lblEstado.setFont(new Font("Arial", Font.BOLD, 14));

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setString("0%");
        progressBar.setForeground(COLOR_ACENTO);
        progressBar.setBackground(COLOR_FONDO);

        panelInfo = new JPanel(new GridLayout(2, 1, 5, 5));
        panelInfo.setBackground(COLOR_FONDO);
        panelInfo.add(lblEstado);
        panelInfo.add(progressBar);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(COLOR_FONDO);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        panelSuperior.add(panelInfo, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(textArea);

        JLabel tituloLabel = new JLabel("Resultados de la Carrera");
        tituloLabel.setOpaque(true);
        tituloLabel.setBackground(new Color(139, 0, 0));
        tituloLabel.setForeground(Color.WHITE);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 14));
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JPanel panelConTitulo = new JPanel(new BorderLayout());
        panelConTitulo.add(tituloLabel, BorderLayout.NORTH);
        panelConTitulo.add(scrollPane, BorderLayout.CENTER);
        panelConTitulo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        scrollPane.getViewport().setBackground(COLOR_FONDO);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(COLOR_FONDO);
        panelBotones.add(btnIniciarPausar);
        panelBotones.add(btnReiniciar);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelConTitulo, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void setupCarrera() {
        Circuito circuito = ConfiguracionCarrera.circuitoSeleccionado;
        List<Piloto> pilotos = ConfiguracionCarrera.pilotosSeleccionados;

        if (circuito == null || pilotos == null || pilotos.stream().allMatch(p -> p == null)) {
            JOptionPane.showMessageDialog(this,
                    "Faltan datos para simular la carrera.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        carrera = new CarreraF1(1, circuito, ConfiguracionCarrera.cantidadVueltas);
        pilotos.stream().filter(Objects::nonNull).forEach(carrera::agregarPiloto);

        mostrarInformacionInicial();
    }

    private void setupTimer() {
        timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!carreraPausada) {
                    mostrarSiguienteVuelta();
                }
            }
        });

        btnIniciarPausar.addActionListener(e -> toggleCarrera());
        btnReiniciar.addActionListener(e -> reiniciarCarrera());
    }

    private void toggleCarrera() {
        if (!carreraIniciada) {
            carreraIniciada = true;
            carreraPausada = false;
            timer.start();
            btnIniciarPausar.setText("Pausar");
            btnIniciarPausar.setBackground(Color.GRAY);
            lblEstado.setText("Carrera en curso...");
        } else {
            if (carreraPausada) {
                carreraPausada = false;
                timer.start();
                btnIniciarPausar.setText("Pausar");
                btnIniciarPausar.setBackground(Color.GRAY);
                lblEstado.setText("Carrera en curso...");
            } else {
                carreraPausada = true;
                timer.stop();
                btnIniciarPausar.setText("Reanudar");
                btnIniciarPausar.setBackground(Color.GRAY);
                lblEstado.setText("Carrera pausada");
            }
        }
    }

    private void reiniciarCarrera() {
        timer.stop();
        carreraIniciada = false;
        carreraPausada = false;
        vueltaActual = 0;

        setupCarrera();

        btnIniciarPausar.setText("Iniciar Carrera");
        btnIniciarPausar.setBackground(Color.GRAY);
        lblEstado.setText("Carrera lista para iniciar");
        progressBar.setValue(0);
        progressBar.setString("0%");
    }

    private void mostrarInformacionInicial() {
        StringBuilder sb = new StringBuilder();
        sb.append("SIMULACION DE CARRERA F1\n");
        sb.append("====================================\n\n");
        sb.append("Circuito: ").append(ConfiguracionCarrera.circuitoSeleccionado.getNombre()).append("\n");
        sb.append("Vueltas totales: ").append(carrera.getCantidadVueltas()).append("\n");
        sb.append("Pilotos participantes: ").append(carrera.getParticipantes().size()).append("\n\n");

        sb.append("PILOTOS PARTICIPANTES:\n");
        sb.append("----------------------\n");
        for (int i = 0; i < carrera.getParticipantes().size(); i++) {
            Piloto p = carrera.getParticipantes().get(i);
            sb.append(String.format("%d. %s\n", i + 1, p.getNombre()));
        }

        sb.append("\nPresiona 'Iniciar Carrera' para comenzar la simulacion automatica\n");

        textArea.setText(sb.toString());
    }

    private void mostrarSiguienteVuelta() {
        if (carrera.simularSiguienteVuelta()) {
            vueltaActual++;
            actualizarTextoResultados();
            actualizarProgreso();

            if (vueltaActual == carrera.getCantidadVueltas()) {
                finalizarCarrera();
            }
        }
    }

    private void actualizarTextoResultados() {
        List<Piloto> ordenActual = carrera.getParticipantes().stream()
                .sorted(Comparator.comparingDouble(Piloto::getTiempoTotal))
                .collect(Collectors.toList());

        double tiempoLider = ordenActual.get(0).getTiempoTotal();

        StringBuilder sb = new StringBuilder();
        sb.append("RESULTADOS - VUELTA ").append(vueltaActual)
                .append(" de ").append(carrera.getCantidadVueltas()).append("\n");
        sb.append("=========================================================\n\n");

        for (int i = 0; i < ordenActual.size(); i++) {
            Piloto p = ordenActual.get(i);
            double diferencia = p.getTiempoTotal() - tiempoLider;

            String posicion = String.format("%d", i + 1);
            String medalla = i == 0 ? " [1ro]" : i == 1 ? " [2do]" : i == 2 ? " [3ro]" : "";

            sb.append(String.format("%s%s. %-20s | Tiempo: %.2f min | Pit stops: %d | Diferencia: +%.2f\n",
                    posicion,
                    medalla,
                    p.getNombre(),
                    p.getTiempoTotal(),
                    p.getCantidadPitStop(),
                    diferencia));
        }

        sb.append("\n---------------------------------------------------------\n");
        sb.append("Lider: ").append(ordenActual.get(0).getNombre())
                .append(" | Tiempo: ").append(String.format("%.2f min", tiempoLider)).append("\n");

        textArea.setText(sb.toString());
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    private void actualizarProgreso() {
        int porcentaje = (int) ((double) vueltaActual / carrera.getCantidadVueltas() * 100);
        progressBar.setValue(porcentaje);
        progressBar.setString(String.format("Vuelta %d/%d (%d%%)",
                vueltaActual, carrera.getCantidadVueltas(), porcentaje));
    }

    private void finalizarCarrera() {
        timer.stop();
        carreraIniciada = false;

        btnIniciarPausar.setEnabled(false);
        btnIniciarPausar.setText("Finalizada");

        lblEstado.setText("Carrera finalizada!");

        List<Piloto> ordenFinal = carrera.getParticipantes().stream()
                .sorted(Comparator.comparingDouble(Piloto::getTiempoTotal))
                .collect(Collectors.toList());

        JOptionPane.showMessageDialog(this,
                String.format("Carrera finalizada!\n\nGanador: %s\nTiempo total: %.2f minutos",
                        ordenFinal.get(0).getNombre(),
                        ordenFinal.get(0).getTiempoTotal()),
                "Carrera completada!",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
