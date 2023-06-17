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

    // Cria um novo cliente
    public Cliente create(Cliente cliente){
        // Validação dos campos obrigatórios
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

        // Salva o cliente no banco de dados
        return repository.save(cliente);
    }

    // Retorna todos os clientes
    public List<Cliente> findAll(){
        return repository.findAll();
    }

    // Busca um cliente pelo ID
    public Optional<Cliente> findById(Long id){
        return repository.findById(id);
    }

    // Exclui um cliente pelo ID
    public void delete(Long id) {
        // Verifica se o cliente com o ID fornecido existe
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("O cliente com o ID fornecido não existe.");
        }

        // Exclui o cliente do banco de dados
        repository.deleteById(id);
    }

}
