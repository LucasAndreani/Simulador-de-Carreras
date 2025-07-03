package simuladorcarreras.view;

import simuladorcarreras.controller.ConfiguracionCarrera;
import simuladorcarreras.dao.CircuitoDAO;
import simuladorcarreras.model.Circuito;
import simuladorcarreras.model.TipoCircuito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ElegirCircuitoView extends JFrame {

    private JTable tablaCircuitos;
    private JButton btnConfirmar;
    private Circuito circuitoSeleccionado = null;

    private final Color COLOR_FONDO = new Color(45, 45, 45);
    private final Color COLOR_TEXTO = Color.WHITE;
    private final Color COLOR_ACENTO = new Color(0, 123, 255);
    private final Color COLOR_GRIS = Color.GRAY;
    private final Color COLOR_ADVERTENCIA = new Color(255, 193, 7);

    public ElegirCircuitoView(TipoCircuito tipoCircuito) {
        initializeComponents(tipoCircuito);
        setupLayout();
        setupEventListeners(tipoCircuito);
        setVisible(true);
    }

    private void initializeComponents(TipoCircuito tipoCircuito) {
        setTitle("Elegir Circuito (" + tipoCircuito.name() + ")");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(COLOR_FONDO);

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Nombre");
        modelo.addColumn("Ubicación");
        modelo.addColumn("Longitud (km)");
        modelo.addColumn("Dificultad");
        modelo.addColumn("Terreno");

        tablaCircuitos = new JTable(modelo);
        tablaCircuitos.setBackground(new Color(30, 30, 30));
        tablaCircuitos.setForeground(COLOR_TEXTO);
        tablaCircuitos.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaCircuitos.setRowHeight(25);
        tablaCircuitos.setSelectionBackground(COLOR_ACENTO);
        tablaCircuitos.setSelectionForeground(Color.WHITE);
        tablaCircuitos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaCircuitos.setGridColor(new Color(60, 60, 60));
        tablaCircuitos.setShowGrid(true);

        JTableHeader header = tablaCircuitos.getTableHeader();
        header.setBackground(new Color(139, 0, 0));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setBackground(new Color(30, 30, 30));
        centerRenderer.setForeground(COLOR_TEXTO);

        for (int i = 0; i < tablaCircuitos.getColumnCount(); i++) {
            tablaCircuitos.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        CircuitoDAO dao = new CircuitoDAO();
        List<Circuito> lista = dao.obtenerPorTipo(tipoCircuito);

        for (Circuito c : lista) {
            modelo.addRow(new Object[]{
                    c.getNombre(),
                    c.getUbicacion(),
                    c.getLongitudKm(),
                    c.getDificultad(),
                    c.getTerreno().name()
            });
        }

        btnConfirmar = createStyledButton("Confirmar selección", COLOR_GRIS);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
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
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        JLabel lblTitulo = new JLabel("SELECCIONAR CIRCUITO");
        lblTitulo.setForeground(COLOR_TEXTO);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        panelSuperior.add(lblTitulo, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(tablaCircuitos);
        scrollPane.getViewport().setBackground(COLOR_FONDO);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 2));
        scrollPane.setBackground(COLOR_FONDO);

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(COLOR_FONDO);
        panelTabla.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBoton.setBackground(COLOR_FONDO);
        panelBoton.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));
        panelBoton.add(btnConfirmar);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);
    }

    private void setupEventListeners(TipoCircuito tipoCircuito) {
        CircuitoDAO dao = new CircuitoDAO();
        List<Circuito> lista = dao.obtenerPorTipo(tipoCircuito);

        btnConfirmar.addActionListener(e -> {
            int filaSeleccionada = tablaCircuitos.getSelectedRow();
            if (filaSeleccionada != -1) {
                circuitoSeleccionado = lista.get(filaSeleccionada);
                ConfiguracionCarrera.circuitoSeleccionado = circuitoSeleccionado;

                JOptionPane.showMessageDialog(this, "Circuito seleccionado: " + circuitoSeleccionado.getNombre());

                new SimularCarreraView();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Seleccioná un circuito.");
            }
        });
    }

    public Circuito getCircuitoSeleccionado() {
        return circuitoSeleccionado;
    }
}