package com.example.demo.Controller;

import com.example.demo.Model.Publicacion;
import com.example.demo.Model.Usuario;
import com.example.demo.Repository.PublicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
public class HomeController {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        // Recuperar el usuario de la sesión
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // Si no hay usuario en la sesión, redirigir al login
        if (usuario == null) {
            return "redirect:/login";
        }

        // Pasar el usuario y las publicaciones al modelo para que Thymeleaf lo use
        model.addAttribute("usuario", usuario);
        model.addAttribute("publicaciones", publicacionRepository.findAll(Sort.by(Sort.Order.desc("fecha"))));

        return "home"; // Devolver la vista home
    }

    @PostMapping("/publicar")
    public String publicar(@RequestParam("contenido") String contenido, HttpSession session,
            RedirectAttributes redirectAttributes) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        Publicacion publicacion = new Publicacion(contenido, LocalDateTime.now(), usuario);
        publicacionRepository.save(publicacion);

        redirectAttributes.addFlashAttribute("alert", "¡Publicación realizada con éxito!");
        return "home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Invalidar la sesión
        session.invalidate();

        return "redirect:/login"; // Redirigir al login
    }

    @GetMapping("/home/eliminar")
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
