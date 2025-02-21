package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(HttpSession session) {
        if (session.getAttribute("usuario") != null) {
            return "redirect:/home"; // Si está autenticado, ir a home
        }
        return "redirect:/login"; // Si no, ir a login
    }

}
