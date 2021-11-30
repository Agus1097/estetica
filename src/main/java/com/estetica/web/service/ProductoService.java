package com.estetica.web.service;

import com.estetica.web.entity.Producto;
import com.estetica.web.repository.ProductoRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional
    public void crear(Integer codigo, String nombre, String descripcion, Integer stock, Double costo, Double precio) throws ErrorService {

        validar(codigo, nombre, stock, costo, precio);

        Producto producto = new Producto();

        producto.setCodigo(codigo);
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setStock(stock);
        producto.setCosto(costo);
        producto.setPrecio(precio);
        producto.setFechaAlta(new Date());
        producto.setFechaBaja(null);

        productoRepository.save(producto);
    }

    @Transactional
    public void modificar(String id, Integer codigo, String nombre, String descripcion, Integer stock, Double costo, Double precio) throws ErrorService {

        validar(codigo, nombre, stock, costo, precio);

        Optional<Producto> respuesta = productoRepository.findById(id);
        if (respuesta.isPresent()) {
            productoRepository.modificar(id, codigo, nombre, descripcion, stock, costo, precio);
        } else {
            throw new ErrorService("No se encontró el producto solicitado");
        }
    }

    @Transactional
    public void deshabilitar(String id) throws ErrorService {
        Optional<Producto> respuesta = productoRepository.findById(id);
        if (respuesta.isPresent()) {

            Producto producto = respuesta.get();
            producto.setFechaBaja(new Date());
            producto.setFechaAlta(null);

            productoRepository.save(producto);
        } else {
            throw new ErrorService("No se encontró el producto solicitado");
        }
    }

    @Transactional
    public void habilitar(String id) throws ErrorService {
        Optional<Producto> respuesta = productoRepository.findById(id);
        if (respuesta.isPresent()) {

            Producto producto = respuesta.get();
            producto.setFechaAlta(new Date());
            producto.setFechaBaja(null);

            productoRepository.save(producto);
        } else {
            throw new ErrorService("No se encontró el producto solicitado");
        }
    }

    @Transactional(readOnly = true)
    public List<Producto> mostrarTodos() throws ErrorService {
        List<Producto> respuesta = productoRepository.mostrarTodos();
        if (respuesta != null) {
            return productoRepository.mostrarTodos();
        } else {
            throw new ErrorService("No se encontraron los productos solicitados");
        }
    }

    @Transactional(readOnly = true)
    public List<Producto> mostrarEliminados() throws ErrorService {
        List<Producto> respuesta = productoRepository.mostrarEliminados();
        if (respuesta != null) {
            return productoRepository.mostrarEliminados();
        } else {
            throw new ErrorService("No se encontraron los productos solicitados");
        }
    }

    @Transactional(readOnly = true)
    public List<Producto> buscarPorNombre(String nombre) throws ErrorService {
        List<Producto> respuesta = productoRepository.buscarPorNombre(nombre);
        if (respuesta != null) {
            return productoRepository.buscarPorNombre(nombre);
        } else {
            throw new ErrorService("No se encontró el nombre del producto solicitado");
        }
    }

    @Transactional(readOnly = true)
    public List<Producto> buscarPorDescripcion(String descripcion) throws ErrorService {
        List<Producto> respuesta = productoRepository.buscarPorDescripcion(descripcion);
        if (respuesta != null) {
            return productoRepository.buscarPorDescripcion(descripcion);
        } else {
            throw new ErrorService("No se encontró el nombre del producto solicitado");
        }
    }

    private void validar(Integer codigo, String nombre, Integer stock, Double costo, Double precio) throws ErrorService {

        if (codigo == null) {
            throw new ErrorService("El codigo no puede estar vacio");
        }

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorService("El nombre no puede estar vacio");
        }

        if (stock == null) {
            throw new ErrorService("El stock no puede estar vacio");
        }

        if (costo == null) {
            throw new ErrorService("El costo no puede estar vacio");
        }

        if (precio == null) {
            throw new ErrorService("El precio no puede estar vacio");
        }
    }
}
