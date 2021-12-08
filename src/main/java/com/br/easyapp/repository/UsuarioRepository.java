package com.br.easyapp.repository;

import com.br.easyapp.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    @Query(value ="SELECT * from usuario u where u.login = :login and u.senha = :senha", nativeQuery = true)
    Optional<Usuario> buscaUsuario (@Param("login") String login, @Param("senha") String senha);
}
