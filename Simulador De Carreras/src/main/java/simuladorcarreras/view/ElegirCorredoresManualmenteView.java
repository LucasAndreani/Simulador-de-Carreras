package simuladorcarreras.view;

import simuladorcarreras.dao.PilotoDAO;
import simuladorcarreras.model.Piloto;
import simuladorcarreras.util.Sesion;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ElegirCorredoresManualmenteView extends JFrame {

    private List<Piloto> pilotosSeleccionados = new ArrayList<>();
    private JPanel panelLista;
    private JLabel lblContador;
    private JButton btnFinalizar;

    private ConfigurarCarreraView ventanaAnterior;

    private final Color COLOR_FONDO = new Color(45, 45, 45);
    private final Color COLOR_TEXTO = Color.WHITE;
    private final Color COLOR_PANEL_PILOTO = new Color(60, 60, 60);
    private final Color COLOR_PANEL_SELECCIONADO = new Color(35, 35, 35);

    public ElegirCorredoresManualmenteView(ConfigurarCarreraView anterior) {
        this.ventanaAnterior = anterior;
        initializeComponents();
        setupLayout();
        cargarPilotos();
        setupEventListeners();
        setVisible(true);
    }

    private void initializeComponents() {
        setTitle("Elegir Corredores Manualmente");
        setSize(650, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(COLOR_FONDO);

        panelLista = new JPanel();
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));
        panelLista.setBackground(COLOR_FONDO);

        lblContador = new JLabel("Seleccionados: 0 / 20");
        lblContador.setForeground(COLOR_TEXTO);
        lblContador.setFont(new Font("Arial", Font.BOLD, 14));

        btnFinalizar = createStyledButton("Confirmar Selección", Color.GRAY);
        btnFinalizar.setEnabled(false);
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

        JLabel lblTitulo = new JLabel("SELECCIONAR CORREDORES MANUALMENTE");
        lblTitulo.setForeground(COLOR_TEXTO);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblInstrucciones = new JLabel("Selecciona exactamente 20 pilotos para la carrera");
        lblInstrucciones.setForeground(new Color(180, 180, 180));
        lblInstrucciones.setFont(new Font("Arial", Font.PLAIN, 12));
        lblInstrucciones.setHorizontalAlignment(SwingConstants.CENTER);

        panelSuperior.add(lblTitulo, BorderLayout.NORTH);
        panelSuperior.add(lblInstrucciones, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(panelLista);
        scroll.getViewport().setBackground(COLOR_FONDO);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 2));
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JLabel tituloLista = new JLabel("Pilotos Disponibles");
        tituloLista.setOpaque(true);
        tituloLista.setBackground(new Color(139, 0, 0)); // Fondo rojo igual que las otras ventanas
        tituloLista.setForeground(Color.WHITE);
        tituloLista.setFont(new Font("Arial", Font.BOLD, 14));
        tituloLista.setHorizontalAlignment(SwingConstants.CENTER);
        tituloLista.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JPanel panelConTitulo = new JPanel(new BorderLayout());
        panelConTitulo.add(tituloLista, BorderLayout.NORTH);
        panelConTitulo.add(scroll, BorderLayout.CENTER);
        panelConTitulo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(COLOR_FONDO);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelContador = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelContador.setBackground(new Color(60, 60, 60));
        panelContador.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        panelContador.add(lblContador);

        panelInferior.add(panelContador, BorderLayout.WEST);
        panelInferior.add(btnFinalizar, BorderLayout.EAST);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelConTitulo, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void setupEventListeners() {
        btnFinalizar.addActionListener(e -> {
            ventanaAnterior.setCorredoresSeleccionadosManualmente(pilotosSeleccionados);
            dispose();
        });
    }

    private void cargarPilotos() {
        PilotoDAO dao = new PilotoDAO();
        int usuarioId = Sesion.getUsuarioActual().getId();

        List<Piloto> todos = dao.obtenerPilotosReales();
        List<Piloto> creadosPorUsuario = dao.obtenerPorUsuario(usuarioId);

        List<Piloto> listaFinal = new ArrayList<>(todos);
        for (Piloto p : creadosPorUsuario) {
            if (!listaFinal.contains(p)) listaFinal.add(p);
        }

        for (Piloto p : listaFinal) {
            JPanel panelPiloto = createPilotoPanel(p);
            panelLista.add(panelPiloto);
            panelLista.add(Box.createRigidArea(new Dimension(0, 5))); // Espaciado entre elementos
        }

        panelLista.revalidate();
    }

    private JPanel createPilotoPanel(Piloto p) {
        JPanel panelPiloto = new JPanel(new BorderLayout());
        panelPiloto.setBackground(COLOR_PANEL_PILOTO);
        panelPiloto.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 80), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        panelPiloto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JPanel panelInfo = new JPanel(new GridLayout(2, 1, 0, 2));
        panelInfo.setBackground(COLOR_PANEL_PILOTO);

        JLabel lblNombre = new JLabel(p.getNombre());
        lblNombre.setForeground(COLOR_TEXTO);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel lblDetalles = new JLabel(String.format("Nacionalidad: %s | Nivel de Ataque: %d",
                p.getNacionalidad(), p.getNivelAtaque()));
        lblDetalles.setForeground(new Color(180, 180, 180));
        lblDetalles.setFont(new Font("Arial", Font.PLAIN, 12));

        panelInfo.add(lblNombre);
        panelInfo.add(lblDetalles);

        JButton btnSeleccionar = createStyledButton("Seleccionar", Color.GRAY);
        btnSeleccionar.setPreferredSize(new Dimension(120, 40));

        btnSeleccionar.addActionListener(e -> {
            if (pilotosSeleccionados.size() < 20 && !pilotosSeleccionados.contains(p)) {
                pilotosSeleccionados.add(p);
                lblContador.setText("Seleccionados: " + pilotosSeleccionados.size() + " / 20");

                panelPiloto.setBackground(COLOR_PANEL_SELECCIONADO);
                panelInfo.setBackground(COLOR_PANEL_SELECCIONADO);
                btnSeleccionar.setText("Seleccionado");
                btnSeleccionar.setEnabled(false);
                btnSeleccionar.setBackground(new Color(100, 100, 100));

                if (pilotosSeleccionados.size() == 20) {
                    deshabilitarTodosLosBotones();
                    btnFinalizar.setEnabled(true);
                    btnFinalizar.setBackground(Color.GRAY);
                }
            }
        });

        panelPiloto.add(panelInfo, BorderLayout.CENTER);
        panelPiloto.add(btnSeleccionar, BorderLayout.EAST);

        return panelPiloto;
    }

    private void deshabilitarTodosLosBotones() {
        for (Component c : panelLista.getComponents()) {
            if (c instanceof JPanel) {
                JPanel panel = (JPanel) c;
                for (Component comp : panel.getComponents()) {
                    if (comp instanceof JButton) {
                        JButton btn = (JButton) comp;
                        if (btn.isEnabled()) {
                            btn.setEnabled(false);
                            btn.setBackground(new Color(100, 100, 100));
                        }
                    }
                }
            }
        }
    }
}