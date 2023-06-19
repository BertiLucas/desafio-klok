package br.com.desafioklok.apivendas.services;

import br.com.desafioklok.apivendas.models.Cliente;
import br.com.desafioklok.apivendas.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository repository;

    public Cliente create(Cliente cliente){
        if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório.");
        }
        if (cliente.getCpf() == null || cliente.getCpf().isEmpty()) {
            throw new IllegalArgumentException("O CPF do cliente é obrigatório.");
        }
        if (cliente.getEmail() == null || cliente.getEmail().isEmpty()) {
            throw new IllegalArgumentException("O e-mail do cliente é obrigatório.");
        }
        if (cliente.getEndereco() == null || cliente.getEndereco().isEmpty()) {
            throw new IllegalArgumentException("O endereço do cliente é obrigatório.");
        }
        return repository.save(cliente);
    }

    public List<Cliente> findAll(){
        return repository.findAll();
    }

    public Optional<Cliente> findById(Long id){
        return repository.findById(id);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("O cliente com o ID fornecido não existe.");
        }
        repository.deleteById(id);
    }

    public Cliente update(Cliente cliente) {
        if (cliente.getId() == null || !repository.existsById(cliente.getId())) {
            throw new IllegalArgumentException("O cliente com o ID fornecido não existe.");
        }
        return repository.save(cliente);
    }

}
