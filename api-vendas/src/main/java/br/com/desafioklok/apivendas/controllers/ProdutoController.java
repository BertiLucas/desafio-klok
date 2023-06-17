package br.com.desafioklok.apivendas.controllers;

import br.com.desafioklok.apivendas.models.Produto;
import br.com.desafioklok.apivendas.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    ProdutoService service;

    // Endpoint para criar um novo produto
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Produto> create(@RequestBody Produto produto){
        // Chama o método "create" do serviço para criar o produto
        Produto produtoCreated = service.create(produto);
        // Retorna uma resposta com o produto criado e o status HTTP 201 (Created)
        return ResponseEntity.status(201).body(produtoCreated);
    }

    // Endpoint para obter todos os produtos
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Produto> findAll(){
        // Chama o método "findAll" do serviço para obter todos os produtos
        return service.findAll();
    }

    // Endpoint para obter um produto pelo ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Produto> findById(@PathVariable Long id){
        // Chama o método "findById" do serviço para obter um produto pelo ID
        return service.findById(id);
    }

    // Endpoint para excluir um produto pelo ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id){
        // Chama o método "delete" do serviço para excluir um produto pelo ID
        service.delete(id);
        // Retorna uma resposta vazia com o status HTTP 204 (No Content)
        return ResponseEntity.noContent().build();
    }
}
