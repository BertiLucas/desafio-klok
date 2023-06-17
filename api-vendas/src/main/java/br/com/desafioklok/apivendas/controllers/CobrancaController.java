package br.com.desafioklok.apivendas.controllers;

import br.com.desafioklok.apivendas.models.Cobranca;
import br.com.desafioklok.apivendas.models.Vendas;
import br.com.desafioklok.apivendas.services.CobrancaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cobranca")
public class CobrancaController {

    @Autowired
    CobrancaService cobrancaService;

    // Endpoint para gerar uma nova cobrança com base em uma venda
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Cobranca> gerarCobranca(@RequestBody Vendas venda) {
        // Chama o método "gerarCobranca" do serviço para gerar a cobrança
        Cobranca cobranca = cobrancaService.gerarCobranca(venda);
        // Retorna uma resposta com a cobrança gerada e o status HTTP 201 (Created)
        return ResponseEntity.status(HttpStatus.CREATED).body(cobranca);
    }

    // Endpoint para obter todas as cobranças
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Cobranca> findAll() {
        // Chama o método "findAll" do serviço para obter todas as cobranças
        return cobrancaService.findAll();
    }

    // Endpoint para obter uma cobrança pelo ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Cobranca> findById(@PathVariable Long id) {
        // Chama o método "findById" do serviço para obter uma cobrança pelo ID
        return cobrancaService.findById(id);
    }

    // Endpoint para excluir uma cobrança pelo ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Chama o método "delete" do serviço para excluir uma cobrança pelo ID
        cobrancaService.delete(id);
        // Retorna uma resposta vazia com o status HTTP 204 (No Content)
        return ResponseEntity.noContent().build();
    }
}
