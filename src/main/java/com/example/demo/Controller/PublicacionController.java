package com.example.demo.Controller;

import com.example.demo.Model.Usuario;
import com.example.demo.Repository.PublicacionRepository;
import com.example.demo.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class PublicacionController {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Ruta para mostrar las publicaciones de un usuario
    @GetMapping("/publicaciones")
    public String publicaciones(@RequestParam("usuario") String username, Model model) {
        // Buscar el usuario por su nombre de usuario
        Optional<Usuario> optionalUsuario = usuarioRepository.findByUsername(username);

        if (optionalUsuario.isPresent()) {
            // Si el usuario existe, mostrar sus publicaciones
            Usuario usuario = optionalUsuario.get();
            model.addAttribute("usuario", usuario);
            model.addAttribute("publicaciones", publicacionRepository.findByUsuarioOrderByFechaDesc(usuario));
        } else {
            // Si el usuario no existe, mostrar un mensaje
            model.addAttribute("error", "El usuario no existe");
            return "error"; // Redirigir a una vista de error o alguna página
        }

        return "publicaciones"; // Vista que mostrará las publicaciones del usuario
    }
}
