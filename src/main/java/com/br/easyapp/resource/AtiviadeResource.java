package com.br.easyapp.resource;

import com.br.easyapp.model.Atividade;
import com.br.easyapp.model.dto.AtividadeDTO;
import com.br.easyapp.repository.AtividadeRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.metadata.HsqlTableMetaDataProvider;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/atividades")
public class AtiviadeResource {


    @Autowired
    private AtividadeRepository repository;

    @GetMapping
    public ResponseEntity<List<Atividade>> consultaTodos(
            @RequestParam(value = "id_usuario", required = false) Long idUsuario) {

        final List<Atividade> atividades = repository.buscarAtividadesPorUsuario(idUsuario);

        return ResponseEntity.ok().body(atividades);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity consultarPorCodigo(@PathVariable("id") Long id) {

        var response = repository.findById(id);

        return response.isEmpty()
                ? ResponseEntity.ok().body(Strings.EMPTY)
                : ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/materia")
    public ResponseEntity consultarPorIdMateria(
            @RequestParam(value = "id_materia", required = false) Long idMateria,
            @RequestParam(value = "id_usuario", required = false) Long idUsuario) {

        var response = repository.buscaAtividadePorIdMateria(idMateria, idUsuario);

        return response.isEmpty()
                ? ResponseEntity.ok().body(Strings.EMPTY)
                : ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/atividades_do_dia")
    public ResponseEntity consultarAtividadesDoDia(@RequestParam(value = "id_usuario", required = false) Long idUsuario) {

        var response = repository.buscarAtividadesDoDia(idUsuario);

        return response.isEmpty()
                ? ResponseEntity.ok().body(Strings.EMPTY)
                : ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity criar(@RequestBody AtividadeDTO dto) {

        var atividade = toAtividade(dto);

        return ResponseEntity.ok(repository.save(atividade));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity marcar(@PathVariable("id") Long id) {

        final Optional<Atividade> atividadeSalvo = repository.findById(id);

        if (atividadeSalvo.isEmpty()) {
            throw new NoSuchElementException(String.format("Não foi encontrado nenhum registro com o id: %s", id));
        }

        final boolean marcador = !atividadeSalvo.get().getMarcador();

        repository.marcarAtividade(id, marcador);

        atividadeSalvo.get().setMarcador(marcador);

        return ResponseEntity.ok(atividadeSalvo);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id) {

        repository.deleteById(id);

        return ResponseEntity.ok().body(HttpStatus.OK.toString());
    }

    private Atividade toAtividade(AtividadeDTO dto) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        return Atividade
                .builder()
                .nome(dto.getNome())
                .materia(dto.getMateria())
                .usuario(dto.getUsuario())
                .marcador(dto.getMarcador())
                .dataAtividade(LocalDateTime.parse(dto.getDataAtividade(), formatter))
                .build();
    }
}
