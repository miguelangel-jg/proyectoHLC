package com.example.demo.Controller;

import com.example.demo.Model.Publicacion;
import com.example.demo.Model.Usuario;
import com.example.demo.Repository.PublicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
public class PerfilController {

    @Autowired
    private PublicacionRepository publicacionRepository;

    // Método para mostrar el perfil
    @GetMapping("/perfil")
    public String perfil(HttpSession session, Model model) {
        // Obtener el usuario de la sesión
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // Verificar si el usuario está autenticado
        if (usuario == null) {
            return "redirect:/login"; // Redirige a la página de login si no está autenticado
        }

        // Obtener las publicaciones del usuario ordenadas por fecha descendente
        List<Publicacion> publicaciones = publicacionRepository.findByUsuarioOrderByFechaDesc(usuario);

        // Pasar el usuario y las publicaciones al modelo
        model.addAttribute("usuario", usuario);
        model.addAttribute("publicaciones", publicaciones);

        return "perfil"; // Nombre de la vista (perfil.html)
    }

    // Método para eliminar una publicación
    @PostMapping("/perfil/eliminar")
    public String eliminarPublicacion(@RequestParam Integer id, HttpSession session) {
        // Obtener el usuario de la sesión
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // Verificar si el usuario está autenticado
        if (usuario == null) {
            return "redirect:/login"; // Redirige a la página de login si no está autenticado
        }

        // Buscar la publicación por ID
        Publicacion publicacion = publicacionRepository.findById(id).orElse(null);

        // Verificar si la publicación existe y si pertenece al usuario
        if (publicacion != null && publicacion.getUsuario().equals(usuario)) {
            // Eliminar la publicación de la base de datos
            publicacionRepository.deleteById(id); // Usamos deleteById aquí
        }

        // Redirigir al perfil después de eliminar la publicación
        return "redirect:/perfil";
    }

}
