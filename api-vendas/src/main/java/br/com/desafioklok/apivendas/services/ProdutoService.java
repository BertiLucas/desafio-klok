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

    public Produto create(Produto produto) {
        if (produto.getNome() == null || produto.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }
        if (produto.getDescricao() == null || produto.getDescricao().isEmpty()) {
            throw new IllegalArgumentException("A descrição do produto é obrigatória.");
        }
        if (produto.getPreco() <= 0 ){
            throw new IllegalArgumentException("O preço do produto é obrigatório e deve ser maior que zero.");
        }
        return repository.save(produto);
    }

    public List<Produto> findAll(){
        return repository.findAll();
    }

    public Optional<Produto> findById(Long id){
        return repository.findById(id);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("O produto com o ID fornecido não existe.");
        }
        repository.deleteById(id);
    }

    public Produto update(Produto produto) {
        if (!repository.existsById(produto.getId())) {
            throw new IllegalArgumentException("O produto com o ID fornecido não existe.");
        }
        return repository.save(produto);
    }

}
