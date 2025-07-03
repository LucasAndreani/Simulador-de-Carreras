package simuladorcarreras.controller;

import simuladorcarreras.dao.UsuarioDAO;
import simuladorcarreras.model.Usuario;

public class LoginController {
    private UsuarioDAO usuarioDAO;

    public LoginController() {
        usuarioDAO = new UsuarioDAO();
    }

    public Usuario iniciarSesion(String nombreUsuario, String contrasenia) {
        return usuarioDAO.validarLogin(nombreUsuario, contrasenia);
    }
}
