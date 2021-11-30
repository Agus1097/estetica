package com.estetica.web.repository;

import com.estetica.web.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    @Modifying
    @Query("UPDATE Usuario u SET u.mail= :mail, u.clave= :clave, u.nombre = :nombre , u.telefono = :telefono")
    void modificar(@Param("mail") String mail, @Param("clave") String clave, @Param("nombre") String nombre, @Param("telefono") Long telefono);

    @Query("SELECT u FROM Usuario u WHERE u.mail= :mail")
    Usuario buscarPorMail (@Param("mail") String mail);

}
