package br.com.desafioklok.apivendas.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cliente implements Serializable {

    // Identificador único do cliente gerado automaticamente pelo banco de dados
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome do cliente
    private String nome;

    // CPF do cliente
    private String cpf;

    // E-mail do cliente
    private String email;

    // Endereço do cliente
    private String endereco;
}
