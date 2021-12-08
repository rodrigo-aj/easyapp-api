package com.br.easyapp.repository;

import com.br.easyapp.model.Atividade;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;

public interface AtividadeRepository extends CrudRepository<Atividade, Long> {

    @Query(value = "SELECT * from atividade a " +
            "where a.id_materia = :id_materia and a.id_usuario = :id_usuario " +
            "order by a.data_atividade desc;", nativeQuery = true)
    List<Atividade> buscaAtividadePorIdMateria(@Param("id_materia") Long idMateria,
                                               @Param("id_usuario") Long idUsuario);

    @Query(value = "select * from atividade a " +
            "where a.id_usuario = :id_usuario " +
            "order by a.data_atividade desc;", nativeQuery = true)
    List<Atividade> buscarAtividadesPorUsuario(@Param("id_usuario") Long idUsuario);

    @Query(value = "SELECT * FROM atividade a " +
            "WHERE a.data_atividade = CURDATE() and a.id_usuario = :id_usuario and a.marcador = 0 " +
            "order by a.data_atividade desc;", nativeQuery = true)
    List<Atividade> buscarAtividadesDoDia(@Param("id_usuario") Long idUsuario);

    @Modifying
    @Transactional
    @Query(value = "UPDATE atividade " +
            "SET marcador = :status_marcador " +
            "WHERE id_atividade = :id_atv", nativeQuery = true)
    Object marcarAtividade(@Param("id_atv") Long idAtividade,
                           @Param("status_marcador") Boolean status);

}
