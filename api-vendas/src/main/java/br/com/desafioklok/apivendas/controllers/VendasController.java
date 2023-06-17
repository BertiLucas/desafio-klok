package br.com.desafioklok.apivendas.controllers;

import br.com.desafioklok.apivendas.models.Vendas;
import br.com.desafioklok.apivendas.services.VendasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vendas")
public class VendasController {

    @Autowired
    VendasService service;

    // Endpoint para criar uma nova venda
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Vendas> create(@RequestBody Vendas vendas){
        // Chama o método "create" do serviço para criar a venda
        Vendas vendasCreated = service.create(vendas);
        // Retorna uma resposta com a venda criada e o status HTTP 201 (Created)
        return ResponseEntity.status(201).body(vendasCreated);
    }

    // Endpoint para obter todas as vendas
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Vendas> findAll(){
        // Chama o método "findAll" do serviço para obter todas as vendas
        return service.findAll();
    }

    // Endpoint para obter uma venda pelo ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Vendas> findById(@PathVariable Long id){
        // Chama o método "findById" do serviço para obter uma venda pelo ID
        return service.findById(id);
    }

    // Endpoint para excluir uma venda pelo ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id){
        // Chama o método "delete" do serviço para excluir uma venda pelo ID
        service.delete(id);
        // Retorna uma resposta vazia com o status HTTP 204 (No Content)
        return ResponseEntity.noContent().build();
    }

    // Endpoint para atualizar uma venda pelo ID
    @PutMapping("/{id}")
    public ResponseEntity<Vendas> atualizarVenda(@PathVariable("id") Long id, @RequestBody Vendas vendas) {
        vendas.setId(id);
        // Chama o método "atualizarVenda" do serviço para atualizar a venda
        Vendas vendaAtualizada = service.atualizarVenda(vendas);
        if (vendaAtualizada != null) {
            // Se a venda foi atualizada com sucesso, retorna a venda atualizada e o status HTTP 200 (OK)
            return ResponseEntity.ok(vendaAtualizada);
        } else {
            // Se a venda não foi encontrada, retorna o status HTTP 404 (Not Found)
            return ResponseEntity.notFound().build();
        }
    }
}
