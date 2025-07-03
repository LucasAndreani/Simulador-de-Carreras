package simuladorcarreras.view;

import simuladorcarreras.dao.EscuderiaDAO;
import simuladorcarreras.dao.PilotoDAO;
import simuladorcarreras.model.Escuderia;
import simuladorcarreras.model.Piloto;
import simuladorcarreras.util.Sesion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SeleccionarPilotoView extends JFrame {

    private JComboBox<Piloto> comboPilotos;
    private JButton btnConfirmar;
    private Escuderia escuderiaSeleccionada;

    public SeleccionarPilotoView(Escuderia escuderia) {
        this.escuderiaSeleccionada = escuderia;

        setTitle("Seleccionar Piloto - Escudería: " + escuderia.getNombre());
        setSize(350, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        comboPilotos = new JComboBox<>();
        cargarPilotos();

        btnConfirmar = new JButton("Confirmar");

        add(new JLabel("Selecciona un piloto:"));
        add(comboPilotos);
        add(btnConfirmar);

        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Piloto seleccionado = (Piloto) comboPilotos.getSelectedItem();
                if (seleccionado != null) {
                    JOptionPane.showMessageDialog(null, "Piloto seleccionado: " + seleccionado.getNombre());
                    dispose();
                }
            }
        });

        setVisible(true);
    }

    private void cargarPilotos() {
        PilotoDAO daoPiloto = new PilotoDAO();
        EscuderiaDAO daoEscuderia = new EscuderiaDAO();

        int usuarioId = Sesion.getUsuarioActual().getId();

        List<Piloto> titulares = daoPiloto.obtenerPorEscuderia(escuderiaSeleccionada.getId());
        List<Piloto> suplentes = daoEscuderia.obtenerSuplentesPorEscuderia(escuderiaSeleccionada.getId());

        Set<Integer> idsAgregados = new HashSet<>();

        for (Piloto p : titulares) {
            if (p.isEsReal()) {
                comboPilotos.addItem(p);
                idsAgregados.add(p.getId());
            }
        }

        for (Piloto p : titulares) {
            if (!p.isEsReal() && p.getUsuarioId() != null && p.getUsuarioId().equals(usuarioId) && !idsAgregados.contains(p.getId())) {
                comboPilotos.addItem(p);
                idsAgregados.add(p.getId());
            }
        }

        for (Piloto p : suplentes) {
            if (!p.isEsReal() && p.getUsuarioId() != null && p.getUsuarioId().equals(usuarioId) && !idsAgregados.contains(p.getId())) {
                comboPilotos.addItem(p);
                idsAgregados.add(p.getId());
            }
        }
    }
}
