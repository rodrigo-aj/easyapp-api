package com.br.easyapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@Data
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @JsonIgnore
    @Column(name = "login")
    private String login;

    @JsonIgnore
    @Column(name = "senha")
    private String senha;

    @Column(name = "email")
    private String email;
}
