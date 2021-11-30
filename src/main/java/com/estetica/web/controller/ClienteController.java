package com.estetica.web.controller;

import com.estetica.web.entity.Cliente;
import com.estetica.web.repository.ClienteRepository;
import com.estetica.web.service.ClienteService;
import com.estetica.web.service.ErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/lista")
    public ModelAndView listaClientes(@RequestParam(required = false) String nombre, @RequestParam(required = false) String mail,
            @RequestParam(required = false) Long telefono) throws ErrorService {
        ModelAndView mav = new ModelAndView("lista-clientes");
        if (nombre != null) {
            mav.addObject("clientes", clienteService.buscarPorNombre(nombre));
        } else if (mail != null) {
            mav.addObject("clientes", clienteService.buscarPorMail(mail));
        } else if (telefono != null) {
            mav.addObject("clientes", clienteService.buscarPorTelefono(telefono));
        } else {
            mav.addObject("clientes", clienteService.mostrarTodos());
        }
        mav.addObject("title", "Lista de Clientes");
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearCliente() {
        ModelAndView mav = new ModelAndView("formulario-cliente");
        mav.addObject("cliente", new Cliente());
        mav.addObject("title", "Crear Cliente");
        mav.addObject("action", "guardar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardarCliente(RedirectAttributes attributes, @RequestParam String nombre, @RequestParam String mail,
            @RequestParam Long telefono) throws ErrorService {
        try {
            clienteService.crear(nombre, mail, telefono);
            attributes.addFlashAttribute("mensaje", "El cliente fue creado con exito");
        } catch (ErrorService e) {
            attributes.addFlashAttribute("error", e.getMessage());
            attributes.addFlashAttribute("nombre", nombre);
            attributes.addFlashAttribute("mail", mail);
            attributes.addFlashAttribute("telefono", telefono);
            return new RedirectView("/cliente/crear");
        }
        return new RedirectView("/cliente/lista");
    }

    @GetMapping("/modificar/{id}")
    public ModelAndView modificarCliente(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("formulario-cliente");
        mav.addObject("cliente", clienteRepository.findById(id));
        mav.addObject("title", "Modificar Cliente");
        mav.addObject("action", "editar");
        return mav;
    }

    @PostMapping("/editar")
    public RedirectView editarCliente(RedirectAttributes attributes, @RequestParam String id, @RequestParam String nombre, @RequestParam String mail,
            @RequestParam Long telefono) throws ErrorService {
        try {
            clienteService.modificar(id, nombre, mail, telefono);
            attributes.addFlashAttribute("mensaje", "El cliente fue modificado con éxito");
        } catch (ErrorService e) {
            attributes.addFlashAttribute("error", e.getMessage());
            attributes.addFlashAttribute("nombre", nombre);
            attributes.addFlashAttribute("mail", mail);
            attributes.addFlashAttribute("telefono", telefono);
            return new RedirectView("/cliente/modificar/{id}");
        }
        return new RedirectView("/cliente/lista");
    }

    @PostMapping("/dar-baja/{id}")
    public RedirectView darBajaCliente(RedirectAttributes attributes, @PathVariable String id) throws ErrorService {
        try {
            clienteService.deshabilitar(id);
            attributes.addFlashAttribute("mensaje", "El cliente se dió de baja con éxito");
        } catch (ErrorService e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return new RedirectView("/cliente/crear");
        }
        return new RedirectView("/cliente/lista");
    }

    @GetMapping("/lista/bajas")
    public ModelAndView listaClientesEliminados() throws ErrorService {
        ModelAndView mav = new ModelAndView("lista-clientes");
        mav.addObject("clientes", clienteService.mostrarEliminados());
        mav.addObject("title", "Clientes Eliminados");
        mav.addObject("action", "eliminar");
        return mav;
    }

    @PostMapping("/dar-alta/{id}")
    public RedirectView darAltaCliente(RedirectAttributes attributes, @PathVariable String id) throws ErrorService {
        try {
            clienteService.habilitar(id);
            attributes.addFlashAttribute("mensaje", "El cliente se dió de alta con éxito");
        } catch (ErrorService e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return new RedirectView("/cliente/crear");
        }
        return new RedirectView("/cliente/lista");
    }

}
