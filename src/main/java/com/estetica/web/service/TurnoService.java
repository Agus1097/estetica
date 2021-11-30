package com.estetica.web.service;

import com.estetica.web.entity.Cliente;
import com.estetica.web.entity.Turno;
import com.estetica.web.entity.Usuario;
import com.estetica.web.repository.TurnoRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TurnoService {

    @Autowired
    private TurnoRepository turnoRepository;

    @Transactional
    public void crear(String id, Date fechaTurno, Cliente cliente, Usuario usuario) throws ErrorService {

        validar(fechaTurno, cliente, usuario);

        Turno turno = new Turno();

        turno.setFechaTurno(fechaTurno);
        turno.setCliente(cliente);
        turno.setUsuario(usuario);

        turnoRepository.save(turno);
    }

    @Transactional
    public void modificar(String id, Date fechaTurno, Cliente cliente, Usuario usuario) throws ErrorService {

        validar(fechaTurno, cliente, usuario);
        Optional<Turno> respuesta = turnoRepository.findById(id);
        if (respuesta.isPresent()) {
            turnoRepository.modificar(fechaTurno, cliente, usuario);
        } else {
            throw new ErrorService("No se encontró el turno solicitado");
        }
    }

    @Transactional
    public void eliminar(String id) throws ErrorService {
        Optional<Turno> respuesta = turnoRepository.findById(id);
        if (respuesta.isPresent()) {
            turnoRepository.deleteById(id);
        } else {
            throw new ErrorService("No se encontró el turno solicitado");
        }
    }

    @Transactional(readOnly = true)
    public List<Turno> buscarPorTurno(Date fechaTurno) throws ErrorService {
        List<Turno> respuesta = turnoRepository.buscarPorTurno(fechaTurno);
        if (respuesta != null) {
            return turnoRepository.buscarPorTurno(fechaTurno);
        } else {
            throw new ErrorService("No se encontró la fecha del turno solicitado");
        }
    }

    private void validar(Date fechaTurno, Cliente cliente, Usuario usuario) throws ErrorService {

        if (fechaTurno == null) {
            throw new ErrorService("El turno no puede estar vacio");
        }

        if (cliente == null) {
            throw new ErrorService("El cliente no puede estar vacio");
        }

        if (usuario == null) {
            throw new ErrorService("El usuario no puede estar vacio");
        }
    }
}
