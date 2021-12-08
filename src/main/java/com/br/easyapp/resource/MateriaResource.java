package com.br.easyapp.resource;

import com.br.easyapp.model.Materia;
import com.br.easyapp.model.dto.MateriaDTO;
import com.br.easyapp.repository.MateriaRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/materias")
public class MateriaResource {

    @Autowired
    private MateriaRepository repository;

    @GetMapping
    public ResponseEntity<List<Materia>> consultaTodos(
            @RequestParam(value = "id_usuario", required = false) Long idUsuario) {

        final List<Materia> response = repository.buscarMateriaPorUsuario(idUsuario);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity consultarPorCodigo(@PathVariable("id") Long id) {

        var response = repository.findById(id);

        return response.isEmpty()
                ? ResponseEntity.ok().body(Strings.EMPTY)
                : ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity criar(@RequestBody MateriaDTO dto) {

        var materia = toMateria(dto);

        return ResponseEntity.ok(repository.save(materia));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity alterar(@RequestBody Materia materia, @PathVariable("id") Long id) {

        final Optional<Materia> materiaSalvo = repository.findById(id);

        if (materiaSalvo.isEmpty()) {
            throw new NoSuchElementException(String.format("Não foi encontrado nenhum registro com o id: %s", id));
        }

        BeanUtils.copyProperties(materia, materiaSalvo, "id");

        return ResponseEntity.ok(repository.save(materiaSalvo.get()));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id) {

        repository.deletarAtividade(id);
        repository.deletarMateria(id);

        return ResponseEntity.ok().body(Strings.EMPTY);
    }

    private Materia toMateria(MateriaDTO dto) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        return Materia
                .builder()
                .usuario(dto.getUsuario())
                .dataMateria(LocalDateTime.parse(dto.getData(), formatter))
                .nome(dto.getNome())
                .build();
    }
}
