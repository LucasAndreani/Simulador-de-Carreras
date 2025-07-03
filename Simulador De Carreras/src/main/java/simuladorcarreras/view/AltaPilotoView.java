package simuladorcarreras.view;

import simuladorcarreras.dao.EscuderiaDAO;
import simuladorcarreras.dao.PilotoDAO;
import simuladorcarreras.dao.VehiculoDAO;
import simuladorcarreras.model.Escuderia;
import simuladorcarreras.model.Piloto;
import simuladorcarreras.model.Vehiculo;
import simuladorcarreras.util.Sesion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AltaPilotoView extends JFrame {

    private JTextField nombreField, nacionalidadField, ataqueField;
    private JComboBox<Escuderia> comboEscuderias;
    private JButton guardarBtn, volverBtn;
    private JLabel statusLabel;

    private final Color COLOR_FONDO = new Color(45, 45, 45);
    private final Color COLOR_TEXTO = Color.WHITE;
    private final Color COLOR_ACENTO = new Color(0, 123, 255);
    private final Color COLOR_EXITO = new Color(40, 167, 69);
    private final Color COLOR_ADVERTENCIA = new Color(255, 193, 7);

    public AltaPilotoView() {
        initializeComponents();
        setupLayout();
        setupEventListeners();
        setVisible(true);
    }

    private void initializeComponents() {
        setTitle("Alta de Piloto");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(COLOR_FONDO);

        nombreField = createStyledTextField();
        nacionalidadField = createStyledTextField();
        ataqueField = createStyledTextField();

        comboEscuderias = new JComboBox<>();
        comboEscuderias.setBackground(new Color(60, 60, 60));
        comboEscuderias.setForeground(COLOR_TEXTO);
        comboEscuderias.setFont(new Font("Arial", Font.PLAIN, 12));
        comboEscuderias.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 1));
        cargarEscuderias();

        guardarBtn = createStyledButton("Guardar", Color.GRAY);
        volverBtn = createStyledButton("Volver", Color.GRAY);

        statusLabel = new JLabel("");
        statusLabel.setForeground(COLOR_ADVERTENCIA);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setBackground(new Color(60, 60, 60));
        field.setForeground(COLOR_TEXTO);
        field.setFont(new Font("Arial", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 80), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setCaretColor(COLOR_TEXTO);
        return field;
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

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(COLOR_TEXTO);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        return label;
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(COLOR_FONDO);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel lblTitulo = new JLabel("CREAR NUEVO PILOTO");
        lblTitulo.setForeground(COLOR_TEXTO);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        panelSuperior.add(lblTitulo, BorderLayout.CENTER);

        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(COLOR_FONDO);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0.3;
        panelCentral.add(createStyledLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        panelCentral.add(nombreField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0.3;
        panelCentral.add(createStyledLabel("Nacionalidad:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        panelCentral.add(nacionalidadField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0.3;
        panelCentral.add(createStyledLabel("Nivel de Ataque:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        panelCentral.add(ataqueField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.weightx = 0.3;
        panelCentral.add(createStyledLabel("Escudería:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        panelCentral.add(comboEscuderias, gbc);

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(COLOR_FONDO);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(COLOR_FONDO);
        panelBotones.add(guardarBtn);
        panelBotones.add(volverBtn);

        JPanel panelStatus = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelStatus.setBackground(COLOR_FONDO);
        panelStatus.add(statusLabel);

        panelInferior.add(panelBotones, BorderLayout.NORTH);
        panelInferior.add(panelStatus, BorderLayout.CENTER);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void setupEventListeners() {
        guardarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarPiloto();
            }
        });

        volverBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PilotoMenuView();
                dispose();
            }
        });
    }

    private void cargarEscuderias() {
        EscuderiaDAO dao = new EscuderiaDAO();
        List<Escuderia> escuderias = dao.obtenerTodas();
        for (Escuderia e : escuderias) {
            comboEscuderias.addItem(e);
        }
    }

    private void guardarPiloto() {
        try {
            String nombre = nombreField.getText().trim();
            String nacionalidad = nacionalidadField.getText().trim();
            int ataque = Integer.parseInt(ataqueField.getText().trim());

            if (nombre.isEmpty() || nacionalidad.isEmpty()) {
                statusLabel.setText("Nombre y Nacionalidad no pueden estar vacíos.");
                statusLabel.setForeground(COLOR_ADVERTENCIA);
                return;
            }

            Escuderia escuderiaSeleccionada = (Escuderia) comboEscuderias.getSelectedItem();
            if (escuderiaSeleccionada == null) {
                statusLabel.setText("Debes seleccionar una escudería.");
                statusLabel.setForeground(COLOR_ADVERTENCIA);
                return;
            }

            VehiculoDAO vehiculoDAO = new VehiculoDAO();
            Vehiculo vehiculo = vehiculoDAO.buscarPorEscuderiaId(escuderiaSeleccionada.getId());

            if (vehiculo == null) {
                statusLabel.setText("No hay vehículo asignado a esa escudería.");
                statusLabel.setForeground(COLOR_ADVERTENCIA);
                return;
            }

            Integer usuarioId = Sesion.getUsuarioActual().getId();
            Piloto piloto = new Piloto(nombre, nacionalidad, ataque, false, usuarioId);
            piloto.setEscuderiaId(escuderiaSeleccionada.getId());
            piloto.setVehiculo(vehiculo); // Asignar el vehículo al piloto

            PilotoDAO daoPiloto = new PilotoDAO();
            boolean creado = daoPiloto.crearPiloto(piloto);

            if (creado) {
                statusLabel.setText("Piloto guardado con éxito.");
                statusLabel.setForeground(COLOR_EXITO);
            } else {
                statusLabel.setText("Error al guardar piloto.");
                statusLabel.setForeground(COLOR_ADVERTENCIA);
            }

        } catch (NumberFormatException ex) {
            statusLabel.setText("Nivel de ataque debe ser un número.");
            statusLabel.setForeground(COLOR_ADVERTENCIA);
        }
    }
}