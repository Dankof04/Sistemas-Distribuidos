package com.ubu.practica_pokemon.controllers;

import com.ubu.practica_pokemon.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    // Muestra el formulario de login
    @GetMapping("/login")
    public String verLogin() {
        return "login";
    }

    // Muestra el formulario de registro
    @GetMapping("/register")
    public String verRegistro() {
        return "register";
    }

    // Procesa el registro de un nuevo usuario
    @PostMapping("/register")
    public String procesarRegistro(@RequestParam String username,
                                   @RequestParam String password,
                                   Model model) {
        try {
            userService.registrarUsuario(username, password);
            // Si sale bien, vamos al login con un mensaje de éxito en la URL
            return "redirect:/login?success";
        } catch (Exception e) {
            // Si falla (ej: usuario duplicado), volvemos al registro con el error
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
