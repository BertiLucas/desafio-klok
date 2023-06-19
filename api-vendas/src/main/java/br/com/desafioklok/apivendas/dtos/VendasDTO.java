package br.com.desafioklok.apivendas.dtos;

import br.com.desafioklok.apivendas.models.Cliente;
import br.com.desafioklok.apivendas.models.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendasDTO {
    private Long id;
    private Cliente cliente;
    private List<Produto> produtos;
}

