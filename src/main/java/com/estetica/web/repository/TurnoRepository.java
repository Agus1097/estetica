package com.estetica.web.repository;

import com.estetica.web.entity.Cliente;
import com.estetica.web.entity.Turno;
import com.estetica.web.entity.Usuario;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, String> {

    @Modifying
    @Query("UPDATE Turno t SET  t.fechaTurno = :fechaTurno, t.cliente = :cliente, t.usuario = :usuario")
    void modificar(@Param("fechaTurno") Date fechaTurno, @Param("cliente") Cliente cliente, @Param("usuario") Usuario usuario);

    @Query("SELECT t FROM Turno t WHERE t.fechaTurno = :fechaTurno")
    List<Turno> buscarPorTurno(@Param("fechaTurno") Date fechaTurno);

    @Query("SELECT t FROM Turno t ORDER BY t.fechaTurno DESC")
    List<Turno> ordenarPorTurno(@Param("fechaTurno") Date fechaTurno);

}
