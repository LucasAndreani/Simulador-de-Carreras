package simuladorcarreras.view;

import simuladorcarreras.dao.EscuderiaDAO;
import simuladorcarreras.model.Escuderia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SeleccionarEscuderiaView extends JFrame implements ActionListener {

    private JComboBox<Escuderia> comboEscuderias;
    private JButton btnConfirmar, btnVolver;

    public SeleccionarEscuderiaView() {
        super("Seleccionar Escudería");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        comboEscuderias = new JComboBox<>();
        cargarEscuderias();
        add(comboEscuderias);

        btnConfirmar = new JButton("Confirmar");
        btnVolver = new JButton("Volver");

        btnConfirmar.addActionListener(this);
        btnVolver.addActionListener(this);

        add(btnConfirmar);
        add(btnVolver);

        setVisible(true);
    }

    private void cargarEscuderias() {
        EscuderiaDAO dao = new EscuderiaDAO();
        List<Escuderia> escuderias = dao.obtenerTodas();
        for (Escuderia e : escuderias) {
            comboEscuderias.addItem(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnConfirmar) {
            Escuderia seleccionada = (Escuderia) comboEscuderias.getSelectedItem();
            if (seleccionada != null) {
                new PilotoMenuView();
                this.dispose();
            }
        } else if (e.getSource() == btnVolver) {
            new SeleccionarTipoCarreraView();
            this.dispose();
        }
    }
}
