package com.estetica.web.service;

import com.estetica.web.entity.Usuario;
import com.estetica.web.repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public void registrar(String mail, String clave, String clave2, String nombre, Long telefono) throws ErrorService {

        validar(mail, clave, clave2, nombre, telefono);

        Usuario usuario = new Usuario();

        usuario.setMail(mail);
        usuario.setClave(encoder.encode(clave));
        usuario.setNombre(nombre);
        usuario.setTelefono(telefono);
        usuario.setFechaAlta(new Date());

        usuarioRepository.save(usuario);
    }

    @Transactional
    public void modificar(String id, String mail, String clave, String clave2, String nombre, Long telefono) throws ErrorService {

        validar(mail, clave, clave2, nombre, telefono);

        Optional<Usuario> respuesta = usuarioRepository.findById(id);
        if (respuesta.isPresent()) {
            usuarioRepository.modificar(mail, encoder.encode(clave), nombre, telefono);
        } else {
            throw new ErrorService("No se encontró el usuario solicitado");
        }
    }

    @Transactional
    public void eliminar(String id) throws ErrorService {
        Optional<Usuario> respuesta = usuarioRepository.findById(id);
        if (respuesta.isPresent()) {
            usuarioRepository.deleteById(id);
        } else {
            throw new ErrorService("No se encontró el usuario solicitado");
        }
    }

    private void validar(String mail, String clave, String clave2, String nombre, Long telefono) throws ErrorService {

        if (mail == null || mail.isEmpty()) {
            throw new ErrorService("El mail no puede estar vacio");
        }

        if (clave == null || clave.isEmpty()) {
            throw new ErrorService("La clave no puede estar vacio");
        }

        if (clave.length() < 7) {
            throw new ErrorService("La clave tiene que tener mas de 8 caracteres");
        }

        if (!clave.equals(clave2)) {
            throw new ErrorService("Las claves no coinciden");
        }

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorService("El nombre no puede estar vacio");
        }

        if (telefono == null || telefono == 0) {
            throw new ErrorService("El telefono no puede estar vacio");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.buscarPorMail(mail);
        if (usuario == null) {
            throw new UsernameNotFoundException("El usuario no esta registrado");
        }
        List<GrantedAuthority> permisos = new ArrayList<>();
        GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
        permisos.add(p1);
        User user = new User(usuario.getMail(), usuario.getClave(), permisos);
        return user;
    }

}
