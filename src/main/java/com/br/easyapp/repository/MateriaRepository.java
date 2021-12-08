package com.br.easyapp.repository;

import com.br.easyapp.model.Atividade;
import com.br.easyapp.model.Materia;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MateriaRepository extends CrudRepository<Materia, Long> {

    @Query(value ="select * from materia a where a.id_usuario = :id_usuario order by a.data_materia desc", nativeQuery = true)
    List<Materia> buscarMateriaPorUsuario(@Param("id_usuario") Long idUsuario);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM materia m WHERE m.id  = :idMateria", nativeQuery = true)
    Object deletarMateria(@Param("idMateria") Long idMateria);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM atividade a WHERE a.id_materia = :idMateria", nativeQuery = true)
    Object deletarAtividade(@Param("idMateria") Long idMateria);
}
