package com.letterfood.controller;

import com.letterfood.dto.LoginDTO;
import com.letterfood.model.Usuario;
import com.letterfood.service.UsuarioService;

public class UsuarioController {
    private UsuarioService usuarioService = new UsuarioService();

    public void registrarUsuario(Usuario usuario) {
        usuarioService.registrarUsuario(usuario);
    }

    public String autenticarUsuario(LoginDTO loginDTO) {
        return usuarioService.autenticarUsuario(loginDTO);
    }
}
