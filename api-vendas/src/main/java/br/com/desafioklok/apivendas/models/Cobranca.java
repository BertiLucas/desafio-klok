package br.com.desafioklok.apivendas.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cobranca implements Serializable {

    // Identificador único da cobrança gerado automaticamente pelo banco de dados
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Venda associada à cobrança
    @OneToOne
    @JoinColumn(name = "venda_id")
    private Vendas venda;

    // Valor da cobrança
    private double valor;
}

