package br.com.desafioklok.apivendas.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vendas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToMany
    @JoinTable(name = "vendas_produtos",
            joinColumns = @JoinColumn(name = "vendas_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id" ))
    private List<Produto> produtos;

    @ManyToOne
    @JoinColumn(name= "cobranca_id")
    private Cobranca cobranca;

    private double valor;
}

