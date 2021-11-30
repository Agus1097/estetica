package com.estetica.web.service;

import com.estetica.web.entity.Cliente;
import com.estetica.web.repository.ClienteRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public void crear(String nombre, String mail, Long telefono) throws ErrorService {

        validar(nombre, mail, telefono);

        Cliente cliente = new Cliente();

        cliente.setNombre(nombre);
        cliente.setMail(mail);
        cliente.setTelefono(telefono);
        cliente.setFechaAlta(new Date());
        cliente.setFechaBaja(null);

        clienteRepository.save(cliente);
    }

    @Transactional
    public void modificar(String id, String nombre, String mail, Long telefono) throws ErrorService {

        validar(nombre, mail, telefono);
        Optional<Cliente> respuesta = clienteRepository.findById(id);
        if (respuesta.isPresent()) {
            clienteRepository.modificar(id, nombre, mail, telefono);
        } else {
            throw new ErrorService("No se encontró el cliente solicitado");
        }
    }

    @Transactional
    public void deshabilitar(String id) throws ErrorService {
        Optional<Cliente> respuesta = clienteRepository.findById(id);
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            cliente.setFechaBaja(new Date());
            cliente.setFechaAlta(null);
            clienteRepository.save(cliente);
        } else {
            throw new ErrorService("No se encontró el cliente solicitado");
        }
    }

    @Transactional
    public void habilitar(String id) throws ErrorService {
        Optional<Cliente> respuesta = clienteRepository.findById(id);
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            cliente.setFechaAlta(new Date());
            cliente.setFechaBaja(null);
            clienteRepository.save(cliente);
        } else {
            throw new ErrorService("No se encontró el cliente solicitado");
        }
    }

    @Transactional(readOnly = true)
    public List<Cliente> mostrarTodos() throws ErrorService {
        List<Cliente> respuesta = clienteRepository.mostrarTodos();
        if (respuesta != null) {
            return clienteRepository.mostrarTodos();
        } else {
            throw new ErrorService("No se encontraron los clientes solicitados");
        }
    }

    @Transactional(readOnly = true)
    public List<Cliente> mostrarEliminados() throws ErrorService {
        List<Cliente> respuesta = clienteRepository.mostrarEliminados();
        if (respuesta != null) {
            return clienteRepository.mostrarEliminados();
        } else {
            throw new ErrorService("No se encontraron los productos solicitados");
        }
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNombre(String nombre) throws ErrorService {
        List<Cliente> respuesta = clienteRepository.buscarPorNombre(nombre);
        if (respuesta != null) {
            return clienteRepository.buscarPorNombre(nombre);
        } else {
            throw new ErrorService("No se encontró el nombre del cliente solicitado");
        }
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarPorMail(String mail) throws ErrorService {
        List<Cliente> respuesta = clienteRepository.buscarPorMail(mail);
        if (respuesta != null) {
            return clienteRepository.buscarPorMail(mail);
        } else {
            throw new ErrorService("No se encontró el nombre del cliente solicitado");
        }
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarPorTelefono(Long telefono) throws ErrorService {
        List<Cliente> respuesta = clienteRepository.buscarPorTelefeno(telefono);
        if (respuesta != null) {
            return clienteRepository.buscarPorTelefeno(telefono);
        } else {
            throw new ErrorService("No se encontró el nombre del cliente solicitado");
        }
    }

    private void validar(String nombre, String mail, Long telefono) throws ErrorService {

        if (mail == null || mail.isEmpty()) {
            throw new ErrorService("El mail no puede estar vacio");
        }

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorService("El nombre no puede estar vacio");
        }

        if (telefono == null || telefono == 0) {
            throw new ErrorService("El telefono no puede estar vacio");
        }
    }

}
