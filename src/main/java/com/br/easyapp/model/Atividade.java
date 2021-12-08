package com.br.easyapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Table(name = "atividade")
@Builder
public class Atividade {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id_atividade")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "data_atividade")
    @JsonProperty("data_atividade")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataAtividade;

    @Column(name = "marcador")
    private Boolean marcador;

    @ManyToOne
    @JoinColumn(name = "id_materia")
    @JsonProperty("materia")
    private Materia materia;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonProperty("usuario")
    @JsonIgnore
    private Usuario usuario;
}
