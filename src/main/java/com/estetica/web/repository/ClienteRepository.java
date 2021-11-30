package com.estetica.web.repository;

import com.estetica.web.entity.Cliente;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {

    @Modifying
    @Query("UPDATE Cliente c SET c.nombre = :nombre, c.mail = :mail, c.telefono = :telefono WHERE c.id = :id")
    void modificar(@Param("id") String id, @Param("nombre") String nombre, @Param("mail") String mail, @Param("telefono") Long telefono);

    @Query("SELECT c FROM Cliente c WHERE fechaBaja is null ORDER BY nombre ASC")
    List<Cliente> mostrarTodos();
    
    @Query("SELECT c FROM Cliente c WHERE fechaAlta is null ORDER BY nombre ASC")
    List<Cliente> mostrarEliminados();

    @Query("SELECT c FROM Cliente c WHERE c.nombre LIKE %:nombre% AND fechaBaja is null ORDER BY nombre ASC")
    List<Cliente> buscarPorNombre(@Param("nombre") String nombre);

    @Query("SELECT c FROM Cliente c WHERE c.mail LIKE %:mail% AND fechaBaja is null ORDER BY nombre ASC")
    List<Cliente> buscarPorMail(@Param("mail") String mail);

    @Query("SELECT c FROM Cliente c WHERE c.telefono LIKE :telefono AND fechaBaja is null ORDER BY nombre ASC")
    List<Cliente> buscarPorTelefeno(@Param("telefono") Long telefono);

}
