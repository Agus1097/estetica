package com.estetica.web.repository;

import com.estetica.web.entity.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, String> {

    @Modifying
    @Query("UPDATE Producto p SET  p.codigo = :codigo, p.nombre = :nombre, p.descripcion = :descripcion, p.stock = :stock, p.costo = :costo, p.precio = :precio WHERE p.id = :id")
    void modificar(@Param("id") String id, @Param("codigo") Integer codigo, @Param("nombre") String nombre, @Param("descripcion") String descripcion, @Param("stock") Integer stock, @Param("costo") Double costo, @Param("precio") Double precio);

    @Query("SELECT p FROM Producto p WHERE fechaBaja is NULL ORDER BY nombre ASC")
    List<Producto> mostrarTodos();

    @Query("SELECT p FROM Producto p WHERE fechaAlta is NULL")
    List<Producto> mostrarEliminados();

    @Query("SELECT p FROM Producto p WHERE p.codigo = :codigo AND fechaBaja is NULL ORDER BY codigo ASC")
    Producto buscarPorCodigo(@Param("codigo") Integer codigo);

    @Query("SELECT p FROM Producto p WHERE p.nombre LIKE %:nombre% AND fechaBaja is NULL ORDER BY nombre ASC")
    List<Producto> buscarPorNombre(@Param("nombre") String nombre);

    @Query("SELECT p FROM Producto p WHERE p.descripcion LIKE %:descripcion% AND fechaBaja is NULL ORDER BY nombre ASC")
    List<Producto> buscarPorDescripcion(@Param("descripcion") String descripcion);

    @Query("SELECT p FROM Producto p WHERE fechaBaja is NULL ORDER BY costo ASC")
    List<Producto> ordenarPorCostoAsc(@Param("costo") Double costo);

    @Query("SELECT p FROM Producto p WHERE fechaBaja is NULL ORDER BY costo DESC")
    List<Producto> ordenarPorCostoDesc(@Param("costo") Double costo);

    @Query("SELECT p FROM Producto p WHERE fechaBaja is NULL ORDER BY precio ASC")
    List<Producto> ordenarPorPrecioAsc(@Param("precio") Double precio);

    @Query("SELECT p FROM Producto p WHERE fechaBaja is NULL ORDER BY precio DESC")
    List<Producto> ordenarPorPrecioDesc(@Param("precio") Double precio);

}
