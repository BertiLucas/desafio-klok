package br.com.desafioklok.apivendas.services;

import br.com.desafioklok.apivendas.models.Produto;
import br.com.desafioklok.apivendas.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository repository;

    // Cria um novo produto
    public Produto create(Produto produto) {
        // Verifica se o nome do produto foi fornecido
        if (produto.getNome() == null || produto.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }

        // Verifica se a descrição do produto foi fornecida
        if (produto.getDescricao() == null || produto.getDescricao().isEmpty()) {
            throw new IllegalArgumentException("A descrição do produto é obrigatória.");
        }

        // Verifica se o preço do produto é válido
        if (produto.getPreco() <= 0 ){
            throw new IllegalArgumentException("O preço do produto é obrigatório e deve ser maior que zero.");
        }

        // Persiste o produto no banco de dados
        return repository.save(produto);
    }

    // Retorna todos os produtos cadastrados
    public List<Produto> findAll(){
        return repository.findAll();
    }

    // Busca um produto pelo ID
    public Optional<Produto> findById(Long id){
        return repository.findById(id);
    }

    // Exclui um produto pelo ID
    public void delete(Long id) {
        // Verifica se o produto com o ID fornecido existe
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("O produto com o ID fornecido não existe.");
        }

        // Exclui o produto do banco de dados
        repository.deleteById(id);
    }
}
