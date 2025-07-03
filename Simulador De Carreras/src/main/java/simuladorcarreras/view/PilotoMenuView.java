package simuladorcarreras.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PilotoMenuView extends JFrame implements ActionListener {

    private JButton btnCrearPiloto;
    private JButton btnConfigurarCarrera;

    private final Color COLOR_FONDO = new Color(45, 45, 45);
    private final Color COLOR_TEXTO = Color.WHITE;
    private final Color COLOR_ACENTO = new Color(0, 123, 255);
    private final Color COLOR_EXITO = new Color(40, 167, 69);
    private final Color COLOR_ADVERTENCIA = new Color(255, 193, 7);

    public PilotoMenuView() {
        super("Menú de Pilotos");
        initializeComponents();
        setupLayout();
        setupEventListeners();
        setVisible(true);
    }

    private void initializeComponents() {
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(COLOR_FONDO);

        btnCrearPiloto = createStyledButton("Crear Piloto", Color.GRAY);
        btnConfigurarCarrera = createStyledButton("Configurar Carrera", Color.GRAY);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 50));

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
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel lblTitulo = new JLabel("SIMULADOR DE CARRERAS F1");
        lblTitulo.setForeground(COLOR_TEXTO);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        panelSuperior.add(lblTitulo, BorderLayout.CENTER);

        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(COLOR_FONDO);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCentral.add(btnCrearPiloto, gbc);

        gbc.gridy = 1;
        panelCentral.add(btnConfigurarCarrera, gbc);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
    }

    private void setupEventListeners() {
        btnCrearPiloto.addActionListener(this);
        btnConfigurarCarrera.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton src = (JButton) e.getSource();
        if (src == btnCrearPiloto) {
            new AltaPilotoView();
            dispose();
        } else if (src == btnConfigurarCarrera) {
            new ConfigurarCarreraView();
            dispose();
        }
    }
}