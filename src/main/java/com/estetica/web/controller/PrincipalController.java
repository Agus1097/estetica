package com.estetica.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class PrincipalController {

    @GetMapping
    public ModelAndView inicio(@RequestParam(required = false) String error) {
        ModelAndView mav = new ModelAndView("index");
        if (error != null) {
            mav.addObject("error", "Usuario o Contraseña incorrecta");
        }
        return mav;
    }

    @GetMapping("/menu")
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    public ModelAndView login() {
        return new ModelAndView("menu");
    }

}
