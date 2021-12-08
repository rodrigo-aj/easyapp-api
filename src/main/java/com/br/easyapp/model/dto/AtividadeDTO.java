package com.br.easyapp.model.dto;

import com.br.easyapp.model.Materia;
import com.br.easyapp.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AtividadeDTO {

    @Column(name = "name")
    private String nome;

    @JsonProperty("data")
    private String dataAtividade;

    @JsonProperty("marcador")
    private Boolean marcador;

    @JsonProperty("materia")
    private Materia materia;

    @JsonProperty("user")
    private Usuario usuario;
}
