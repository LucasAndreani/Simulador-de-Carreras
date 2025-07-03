package simuladorcarreras.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeleccionarTipoCarreraView extends JFrame implements ActionListener {

    private JButton btnF1, btnMotoGP, btnNascar, btnRally;

    private final Color COLOR_FONDO = new Color(45, 45, 45);
    private final Color COLOR_TEXTO = Color.WHITE;
    private final Color COLOR_ACENTO = new Color(0, 123, 255);
    private final Color COLOR_EXITO = new Color(40, 167, 69);
    private final Color COLOR_ADVERTENCIA = new Color(255, 193, 7);

    public SeleccionarTipoCarreraView() {
        initializeComponents();
        setupLayout();
        setupEventListeners();
        setVisible(true);
    }

    private void initializeComponents() {
        setTitle("Seleccionar Tipo de Carrera");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(COLOR_FONDO);

        btnF1 = createStyledButton("Fórmula 1", Color.GRAY);
        btnMotoGP = createStyledButton("MotoGP", Color.GRAY);
        btnNascar = createStyledButton("Nascar", Color.GRAY);
        btnRally = createStyledButton("Rally", Color.GRAY);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(180, 80));

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

        JLabel lblTitulo = new JLabel("SELECCIONAR TIPO DE CARRERA");
        lblTitulo.setForeground(COLOR_TEXTO);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblSubtitulo = new JLabel("Elegí la categoría de carrera que deseas simular");
        lblSubtitulo.setForeground(new Color(180, 180, 180));
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);

        panelSuperior.add(lblTitulo, BorderLayout.NORTH);
        panelSuperior.add(lblSubtitulo, BorderLayout.CENTER);

        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(COLOR_FONDO);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0; gbc.gridy = 0;
        panelCentral.add(btnF1, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        panelCentral.add(btnMotoGP, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelCentral.add(btnNascar, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        panelCentral.add(btnRally, gbc);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInferior.setBackground(COLOR_FONDO);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JLabel lblInfo = new JLabel("Actualmente solo está disponible Fórmula 1");
        lblInfo.setForeground(new Color(180, 180, 180));
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 12));
        panelInferior.add(lblInfo);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void setupEventListeners() {
        btnF1.addActionListener(this);
        btnMotoGP.addActionListener(this);
        btnNascar.addActionListener(this);
        btnRally.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton src = (JButton) e.getSource();

        if (src == btnF1) {
            new PilotoMenuView();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Esta categoría está en desarrollo.");
        }
    }
}