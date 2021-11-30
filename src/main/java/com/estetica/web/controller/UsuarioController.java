package com.estetica.web.controller;

import com.estetica.web.entity.Usuario;
import com.estetica.web.service.ErrorService;
import com.estetica.web.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registrarse")
    public ModelAndView registrarUsuario() {
        ModelAndView mav = new ModelAndView("formulario-usuario");
        mav.addObject("usuario", new Usuario());
        mav.addObject("action", "guardar");
        return mav;

    }

    @PostMapping("/guardar")
    public RedirectView guardarUsuario(RedirectAttributes attributes, @RequestParam String mail, @RequestParam String clave, @RequestParam String clave2,
            @RequestParam String nombre, @RequestParam Long telefono) throws ErrorService {
        try {
            usuarioService.registrar(mail, clave, clave2, nombre, telefono);
            attributes.addFlashAttribute("mensaje", "El usuario fue creado con éxito");
        } catch (ErrorService e) {
            attributes.addFlashAttribute("error", e.getMessage());
            attributes.addFlashAttribute("mail", mail);
            attributes.addFlashAttribute("clave", clave);
            attributes.addFlashAttribute("clave", clave2);
            attributes.addFlashAttribute("nombre", nombre);
            attributes.addFlashAttribute("telefono", telefono);
            return new RedirectView("/usuario/registrarse");
        }
        return new RedirectView("/");
    }
}
