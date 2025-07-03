package simuladorcarreras.view;

import simuladorcarreras.controller.ConfiguracionCarrera;
import simuladorcarreras.dao.EscuderiaDAO;
import simuladorcarreras.dao.PilotoDAO;
import simuladorcarreras.model.Piloto;
import simuladorcarreras.model.TipoCircuito;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ConfigurarCarreraView extends JFrame {

    private DefaultListModel<String> modeloListaCorredores;
    private JList<String> listaCorredores;
    private JButton btnUsarPilotosReales;
    private JButton btnElegirCorredores;
    private JButton btnIrAElegirCircuito;
    private JComboBox<Integer> comboVueltas;

    private List<Piloto> corredoresSeleccionados = new ArrayList<>(20);

    private final Color COLOR_FONDO = new Color(45, 45, 45);
    private final Color COLOR_TEXTO = Color.WHITE;
    private final Color COLOR_ACENTO = new Color(0, 123, 255);
    private final Color COLOR_EXITO = new Color(40, 167, 69);
    private final Color COLOR_ADVERTENCIA = new Color(255, 193, 7);

    public ConfigurarCarreraView() {
        initializeComponents();
        setupLayout();
        setupEventListeners();
        setVisible(true);
    }

    private void initializeComponents() {
        setTitle("Configurar Carrera");
        setSize(600, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(COLOR_FONDO);

        comboVueltas = new JComboBox<>(new Integer[]{20, 50, 70});
        comboVueltas.setSelectedItem(50);
        comboVueltas.setBackground(new Color(60, 60, 60));
        comboVueltas.setForeground(COLOR_TEXTO);
        comboVueltas.setFont(new Font("Arial", Font.BOLD, 12));

        modeloListaCorredores = new DefaultListModel<>();
        for (int i = 0; i < 20; i++) {
            modeloListaCorredores.addElement("Lugar " + (i + 1) + ": [vacío]");
            corredoresSeleccionados.add(null);
        }

        listaCorredores = new JList<>(modeloListaCorredores);
        listaCorredores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaCorredores.setBackground(new Color(30, 30, 30));
        listaCorredores.setForeground(COLOR_TEXTO);
        listaCorredores.setFont(new Font("Consolas", Font.PLAIN, 12));
        listaCorredores.setSelectionBackground(COLOR_ACENTO);
        listaCorredores.setSelectionForeground(Color.WHITE);

        btnUsarPilotosReales = createStyledButton("Usar pilotos reales", Color.GRAY);
        btnElegirCorredores = createStyledButton("Elegir corredores manualmente", Color.GRAY);
        btnIrAElegirCircuito = createStyledButton("Ir a elegir circuito", Color.GRAY);
        btnIrAElegirCircuito.setEnabled(false);
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
                if (button.isEnabled()) {
                    button.setBackground(color.brighter());
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(color);
                }
            }
        });

        return button;
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(COLOR_FONDO);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitulo = new JLabel("CONFIGURACION DE CARRERA F1");
        lblTitulo.setForeground(COLOR_TEXTO);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panelVueltas = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelVueltas.setBackground(COLOR_FONDO);

        JLabel lblVueltas = new JLabel("Cantidad de vueltas:");
        lblVueltas.setForeground(COLOR_TEXTO);
        lblVueltas.setFont(new Font("Arial", Font.BOLD, 14));

        panelVueltas.add(lblVueltas);
        panelVueltas.add(comboVueltas);

        panelSuperior.add(lblTitulo, BorderLayout.NORTH);
        panelSuperior.add(panelVueltas, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(listaCorredores);
        scrollPane.getViewport().setBackground(COLOR_FONDO);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 2));

        JLabel tituloLista = new JLabel("Lista de Corredores (20 lugares)");
        tituloLista.setOpaque(true);
        tituloLista.setBackground(new Color(139, 0, 0)); // Fondo rojo igual que SimularCarreraView
        tituloLista.setForeground(Color.WHITE);
        tituloLista.setFont(new Font("Arial", Font.BOLD, 14));
        tituloLista.setHorizontalAlignment(SwingConstants.CENTER);
        tituloLista.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JPanel panelConTitulo = new JPanel(new BorderLayout());
        panelConTitulo.add(tituloLista, BorderLayout.NORTH);
        panelConTitulo.add(scrollPane, BorderLayout.CENTER);
        panelConTitulo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBotones.setBackground(COLOR_FONDO);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelBotones.add(btnUsarPilotosReales);
        panelBotones.add(btnElegirCorredores);
        panelBotones.add(btnIrAElegirCircuito);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelConTitulo, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void setupEventListeners() {
        btnUsarPilotosReales.addActionListener(e -> {
            PilotoDAO pilotoDAO = new PilotoDAO();
            EscuderiaDAO escuderiaDAO = new EscuderiaDAO();
            List<Piloto> pilotosReales = pilotoDAO.obtenerPilotosReales();

            for (int i = 0; i < 20; i++) {
                if (i < pilotosReales.size()) {
                    Piloto p = pilotosReales.get(i);
                    corredoresSeleccionados.set(i, p);

                    String escuderiaNombre = p.getEscuderiaId() != null ?
                            escuderiaDAO.obtenerNombreEscuderiaPorId(p.getEscuderiaId()) : "Sin escudería";

                    int primerPilotoId = p.getEscuderiaId() != null ? escuderiaDAO.obtenerPrimerPilotoId(p.getEscuderiaId()) : -1;
                    int segundoPilotoId = p.getEscuderiaId() != null ? escuderiaDAO.obtenerSegundoPilotoId(p.getEscuderiaId()) : -1;

                    String posicion;
                    if (p.getId() == primerPilotoId) {
                        posicion = "Primer Piloto";
                    } else if (p.getId() == segundoPilotoId) {
                        posicion = "Segundo Piloto";
                    } else {
                        posicion = "Suplente";
                    }

                    String texto = String.format("Lugar %d: %s (%s) - %s - %s - Ataque: %d",
                            i + 1,
                            p.getNombre(),
                            p.getNacionalidad(),
                            escuderiaNombre,
                            posicion,
                            p.getNivelAtaque());

                    modeloListaCorredores.set(i, texto);
                } else {
                    corredoresSeleccionados.set(i, null);
                    modeloListaCorredores.set(i, "Lugar " + (i + 1) + ": [vacío]");
                }
            }

            boolean listaCompleta = corredoresSeleccionados.stream().noneMatch(p -> p == null);
            btnIrAElegirCircuito.setEnabled(listaCompleta);

            if (listaCompleta) {
                btnIrAElegirCircuito.setBackground(Color.GRAY);
            }
        });

        btnElegirCorredores.addActionListener(e -> {
            new ElegirCorredoresManualmenteView(this);
        });

        btnIrAElegirCircuito.addActionListener(e -> {
            ConfiguracionCarrera.pilotosSeleccionados = new ArrayList<>(corredoresSeleccionados);
            ConfiguracionCarrera.cantidadVueltas = (Integer) comboVueltas.getSelectedItem();

            JOptionPane.showMessageDialog(this, "¡Pasando a elegir el circuito!");
            new ElegirCircuitoView(TipoCircuito.F1);
            dispose();
        });
    }

    public void setCorredoresSeleccionadosManualmente(List<Piloto> seleccionados) {
        EscuderiaDAO escuderiaDAO = new EscuderiaDAO();
        for (int i = 0; i < 20; i++) {
            Piloto p = seleccionados.get(i);
            corredoresSeleccionados.set(i, p);

            String escuderiaNombre = p.getEscuderiaId() != null ?
                    escuderiaDAO.obtenerNombreEscuderiaPorId(p.getEscuderiaId()) : "Sin escudería";

            int primerPilotoId = p.getEscuderiaId() != null ? escuderiaDAO.obtenerPrimerPilotoId(p.getEscuderiaId()) : -1;
            int segundoPilotoId = p.getEscuderiaId() != null ? escuderiaDAO.obtenerSegundoPilotoId(p.getEscuderiaId()) : -1;

            String posicion;
            if (p.getId() == primerPilotoId) {
                posicion = "Primer Piloto";
            } else if (p.getId() == segundoPilotoId) {
                posicion = "Segundo Piloto";
            } else {
                posicion = "Suplente";
            }

            String texto = String.format("Lugar %d: %s (%s) - %s - %s - Ataque: %d",
                    i + 1,
                    p.getNombre(),
                    p.getNacionalidad(),
                    escuderiaNombre,
                    posicion,
                    p.getNivelAtaque());

            modeloListaCorredores.set(i, texto);
        }

        btnIrAElegirCircuito.setEnabled(true);
        btnIrAElegirCircuito.setBackground(Color.GRAY);
    }
}