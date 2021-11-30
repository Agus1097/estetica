package com.estetica.web.controller;

import com.estetica.web.entity.Producto;
import com.estetica.web.repository.ProductoRepository;
import com.estetica.web.service.ErrorService;
import com.estetica.web.service.ProductoService;
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
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/lista")
    public ModelAndView listaProductos(@RequestParam(required = false) String descripcion, @RequestParam(required = false) String nombre) throws ErrorService {
        ModelAndView mav = new ModelAndView("lista-productos");
        if (nombre != null) {
            mav.addObject("productos", productoService.buscarPorNombre(nombre));
        } else if (descripcion != null) {
            mav.addObject("productos", productoService.buscarPorDescripcion(descripcion));
        } else {
            mav.addObject("productos", productoService.mostrarTodos());
        }
        mav.addObject("title", "Lista de Productos");
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearProducto() throws ErrorService {
        ModelAndView mav = new ModelAndView("formulario-producto");
        mav.addObject("producto", new Producto());
        mav.addObject("title", "Crear Producto");
        mav.addObject("action", "guardar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardarProducto(RedirectAttributes attributes, @RequestParam Integer codigo, @RequestParam String nombre, @RequestParam String descripcion,
            @RequestParam Integer stock, @RequestParam Double costo, @RequestParam Double precio) throws ErrorService {
        try {
            productoService.crear(codigo, nombre, descripcion, stock, costo, precio);
            attributes.addFlashAttribute("mensaje", "El producto fue creado con exito");
        } catch (ErrorService e) {
            attributes.addFlashAttribute("error", e.getMessage());
            attributes.addFlashAttribute("nombre", nombre);
            attributes.addFlashAttribute("descripcion", descripcion);
            attributes.addFlashAttribute("stock", stock);
            attributes.addFlashAttribute("costo", costo);
            attributes.addFlashAttribute("precio", precio);
            return new RedirectView("/producto/crear");
        }
        return new RedirectView("/producto/lista");
    }

    @GetMapping("/modificar/{id}")
    public ModelAndView modificarProducto(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("formulario-producto");
        mav.addObject("cliente", productoRepository.findById(id));
        mav.addObject("title", "Modificar Producto");
        mav.addObject("action", "editar");
        return mav;
    }

    @PostMapping("/editar")
    public RedirectView editarProducto(RedirectAttributes attributes, @RequestParam String id, @RequestParam Integer codigo, @RequestParam String nombre,
            @RequestParam String descripcion, @RequestParam Integer stock, @RequestParam Double costo, @RequestParam Double precio) throws ErrorService {
        try {
            productoService.modificar(id, codigo, nombre, descripcion, stock, costo, precio);
            attributes.addFlashAttribute("mensaje", "El producto fue modificado con exito");
        } catch (ErrorService e) {
            attributes.addFlashAttribute("error", e.getMessage());
            attributes.addFlashAttribute("nombre", nombre);
            attributes.addFlashAttribute("descripcion", descripcion);
            attributes.addFlashAttribute("stock", stock);
            attributes.addFlashAttribute("costo", costo);
            attributes.addFlashAttribute("precio", precio);
            return new RedirectView("/producto/crear");
        }
        return new RedirectView("/producto/lista");
    }

    @GetMapping("/lista/bajas")
    public ModelAndView listaProductosEliminados() throws ErrorService {
        ModelAndView mav = new ModelAndView("lista-productos");
        mav.addObject("productos", productoService.mostrarEliminados());
        mav.addObject("title", "Productos Eliminados");
        mav.addObject("action", "eliminar");
        return mav;
    }

    @PostMapping("/dar-baja/{id}")
    public RedirectView darBajaProducto(RedirectAttributes attributes, @PathVariable String id) throws ErrorService {
        try {
            productoService.deshabilitar(id);
            attributes.addFlashAttribute("mensaje", "El producto se dió de baja con éxito");
        } catch (ErrorService e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return new RedirectView("/producto/crear");
        }
        return new RedirectView("/producto/lista");
    }

    @PostMapping("/dar-alta/{id}")
    public RedirectView darAltaProducto(RedirectAttributes attributes, @PathVariable String id) throws ErrorService {
        try {
            productoService.habilitar(id);
            attributes.addFlashAttribute("mensaje", "El producto se dió de alta con éxito");
        } catch (ErrorService e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return new RedirectView("/producto/crear");
        }
        return new RedirectView("/producto/lista/bajas");
    }

}
