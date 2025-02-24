package com.example.demo.Controller;

import com.example.demo.Model.Publicacion;
import com.example.demo.Model.Usuario;
import com.example.demo.Repository.PublicacionRepository;
import com.example.demo.Repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class PublicacionController {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Ruta para mostrar las publicaciones de un usuario
    @GetMapping("/publicaciones")
    public String publicaciones(@RequestParam("usuario") String username, Model model, HttpSession session) {
        // Buscar el usuario por su nombre de usuario
        Optional<Usuario> optionalUsuario = usuarioRepository.findByUsername(username);

        // Recuperar el usuario de la sesión
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // Pasar el usuario y las publicaciones al modelo para que Thymeleaf lo use
        model.addAttribute("usuario", usuario);

        if (optionalUsuario.isPresent()) {
            // Si el usuario existe, mostrar sus publicaciones
            Usuario usuarioB = optionalUsuario.get();
            model.addAttribute("publicaciones", publicacionRepository.findByUsuarioOrderByFechaDesc(usuarioB));
        } else {
            // Si el usuario no existe, mostrar un mensaje
            model.addAttribute("error", "El usuario no existe");
            model.addAttribute("publicaciones", null); // No mostrar publicaciones
        }

        return "publicaciones"; // Vista que mostrará las publicaciones del usuario
    }

    @GetMapping("/publicaciones/eliminar")
    public String eliminarPublicacion(@RequestParam Integer id, HttpSession session,
            RedirectAttributes redirectAttributes) {
        // Buscar la publicación por ID
        Publicacion publicacion = publicacionRepository.findById(id).orElse(null);

        // Verificar si la publicación existe
        if (publicacion != null) {
            // Eliminar la publicación de la base de datos
            publicacionRepository.deleteById(id); // Usamos deleteById aquí
            redirectAttributes.addFlashAttribute("alert", "Publicación eliminada correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("error", "La publicación no existe.");
        }

        // Redirigir a /home después de eliminar la publicación
        return "redirect:/home";
    }
}
