package br.com.desafioklok.apivendas.controllers;

import br.com.desafioklok.apivendas.models.Cliente;
import br.com.desafioklok.apivendas.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    ClienteService service;

    // Endpoint para criar um novo cliente
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente){
        // Chama o método "create" do serviço para criar o cliente
        Cliente clienteCreated = service.create(cliente);
        // Retorna uma resposta com o cliente criado e o status HTTP 201 (Created)
        return ResponseEntity.status(201).body(clienteCreated);
    }

    // Endpoint para obter todos os clientes
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente> findAll(){
        // Chama o método "findAll" do serviço para obter todos os clientes
        return service.findAll();
    }

    // Endpoint para obter um cliente pelo ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Cliente> findById(@PathVariable Long id){
        // Chama o método "findById" do serviço para obter um cliente pelo ID
        return service.findById(id);
    }

    // Endpoint para excluir um cliente pelo ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id){
        // Chama o método "delete" do serviço para excluir um cliente pelo ID
        service.delete(id);
        // Retorna uma resposta vazia com o status HTTP 204 (No Content)
        return ResponseEntity.noContent().build();
    }

}
