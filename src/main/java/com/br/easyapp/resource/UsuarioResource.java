package com.br.easyapp.resource;

import com.br.easyapp.model.Usuario;
import com.br.easyapp.repository.UsuarioRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {

    @Autowired
    private UsuarioRepository repository;

    @GetMapping
    public List<Usuario> consultaTodos() {

        List<Usuario> listaUsuarios = new ArrayList<>();

        final Iterable<Usuario> usuario = repository.findAll();

        usuario.forEach(listaUsuarios::add);

        return listaUsuarios;
    }

    @GetMapping(value = "/{id}")
    public Optional<Usuario> consultarPorCodigo(@PathVariable("id") Long id) {

        return repository.findById(id);
    }

    @PostMapping
    public Usuario criar(@RequestBody Usuario usuario) {

        return repository.save(usuario);
    }

    @PutMapping(value = "/{id}")
    public Usuario alterar(@RequestBody Usuario usuario, @PathVariable("id") Long id) {

        final Optional<Usuario> usuarioSalvo = repository.findById(id);

        if (usuarioSalvo.isEmpty()) {
            throw new NoSuchElementException(String.format("Não foi encontrado nenhum registro com o id: %s", id));
        }

        BeanUtils.copyProperties(usuario, usuarioSalvo, "id");

        return repository.save(usuarioSalvo.get());
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestParam(value = "login", required = false) String login,
                                @RequestParam(value = "senha", required = false) String senha) {

        final Optional<Usuario> usuarioSalvo = repository.buscaUsuario(login, senha);

        if (usuarioSalvo.isEmpty()) {
            return ResponseEntity.ok().body(Strings.EMPTY);
        }

        return ResponseEntity.ok().body(usuarioSalvo);
    }
}