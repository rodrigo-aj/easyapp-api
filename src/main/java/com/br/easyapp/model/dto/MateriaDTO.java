package com.br.easyapp.model.dto;

import com.br.easyapp.model.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class MateriaDTO {

    private String nome;

    @JsonProperty("data")
    private String data;

    @JsonProperty("user")
    private Usuario usuario;
}
