package simuladorcarreras.view;

import simuladorcarreras.controller.LoginController;
import simuladorcarreras.model.Usuario;
import simuladorcarreras.util.Sesion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame implements ActionListener {

    private JLabel uLabel;
    private JTextField userTField;
    private JLabel pLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel successLabel;

    private LoginController loginController = new LoginController();

    private final Color COLOR_FONDO = new Color(45, 45, 45);
    private final Color COLOR_TEXTO = Color.WHITE;
    private final Color COLOR_GRIS = Color.GRAY;
    private final Color COLOR_CAMPO = new Color(60, 60, 60);
    private final Color COLOR_EXITO = new Color(40, 167, 69);
    private final Color COLOR_ERROR = new Color(220, 53, 69);

    public LoginView() {
        super("Login - Simulador F1");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        getContentPane().setBackground(COLOR_FONDO);

        uLabel = new JLabel("Usuario");
        uLabel.setBounds(10, 20, 80, 25);
        uLabel.setForeground(COLOR_TEXTO);
        uLabel.setFont(new Font("Arial", Font.BOLD, 12));
        add(uLabel);

        userTField = new JTextField(20);
        userTField.setBounds(100, 20, 165, 25);
        userTField.setBackground(COLOR_CAMPO);
        userTField.setForeground(COLOR_TEXTO);
        userTField.setBorder(BorderFactory.createLineBorder(COLOR_GRIS, 1));
        userTField.setCaretColor(COLOR_TEXTO);
        add(userTField);

        pLabel = new JLabel("Contraseña");
        pLabel.setBounds(10, 50, 80, 25);
        pLabel.setForeground(COLOR_TEXTO);
        pLabel.setFont(new Font("Arial", Font.BOLD, 12));
        add(pLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 50, 165, 25);
        passwordField.setBackground(COLOR_CAMPO);
        passwordField.setForeground(COLOR_TEXTO);
        passwordField.setBorder(BorderFactory.createLineBorder(COLOR_GRIS, 1));
        passwordField.setCaretColor(COLOR_TEXTO);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        loginButton.setBackground(COLOR_GRIS);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 12));
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(this);

        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(COLOR_GRIS.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(COLOR_GRIS);
            }
        });
        add(loginButton);

        successLabel = new JLabel("");
        successLabel.setBounds(10, 110, 300, 25);
        successLabel.setFont(new Font("Arial", Font.BOLD, 12));
        add(successLabel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String user = userTField.getText();
        String pass = new String(passwordField.getPassword());

        Usuario usuarioLogueado = loginController.iniciarSesion(user, pass);

        if (usuarioLogueado != null) {
            simuladorcarreras.util.Sesion.setUsuarioActual(usuarioLogueado);
            successLabel.setText("Login exitoso");
            successLabel.setForeground(COLOR_EXITO);
            new SeleccionarTipoCarreraView();
            dispose();
        } else {
            successLabel.setText("Credenciales inválidas");
            successLabel.setForeground(COLOR_ERROR);
        }
    }
}