package com.example.demo.Controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Model.Usuario;
import com.example.demo.Repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/login")
    public String login(HttpSession session) {
        if (session.getAttribute("usuario") != null) {
            return "redirect:/home"; // Si ya está autenticado, redirigir al home
        }
        return "login"; // Mostrar el login si no está autenticado
    }

    @PostMapping("/loginUsuario")
    public String loginUsuario(@RequestParam String username,
            @RequestParam String password,
            Model model, HttpSession session) {

        // Buscar al usuario en la base de datos
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
        if (!usuarioOpt.isPresent()) {
            model.addAttribute("error", "Usuario no encontrado.");
            return "login"; // Retorna la vista de login con error
        }

        // Verificar la contraseña
        Usuario usuario = usuarioOpt.get();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            model.addAttribute("error", "Contraseña incorrecta.");
            return "login"; // Retorna la vista de login con error
        }

        // Si las credenciales son correctas, guardar la sesión
        session.setAttribute("usuario", usuario);

        return "redirect:/home"; // Cambia "/home" a la página que quieras
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // Vista para registro
    }

    @PostMapping("/registrar")
    public String registrarUsuario(@RequestParam String username,
            @RequestParam String password,
            Model model) {
        // Comprobar si el usuario ya existe
        Optional<Usuario> existente = usuarioRepository.findByUsername(username);
        if (existente.isPresent()) {
            model.addAttribute("error", "El nombre de usuario ya existe.");
            return "register";
        }

        // Encriptar la contraseña
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncriptada = passwordEncoder.encode(password);

        // Guardar el usuario en la base de datos
        Usuario usuario = new Usuario(username, passwordEncriptada);
        usuarioRepository.save(usuario);

        return "redirect:/login";
    }

}
