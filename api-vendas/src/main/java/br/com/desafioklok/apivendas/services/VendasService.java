package br.com.desafioklok.apivendas.services;

import br.com.desafioklok.apivendas.models.Vendas;
import br.com.desafioklok.apivendas.repositories.VendasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendasService {

    @Autowired
    VendasRepository repository;

    public Vendas create(Vendas vendas) {
        if (vendas.getCliente() == null) {
            throw new IllegalArgumentException("O cliente é obrigatório para criar uma venda.");
        }
        if (vendas.getValor() <= 0) {
            throw new IllegalArgumentException("O valor da venda é obrigatório e deve ser maior que zero.");
        }

        return repository.save(vendas);
    }

    public List<Vendas> findAll() {
        return repository.findAll();
    }

    public Optional<Vendas> findById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("A venda com o ID fornecido não existe.");
        }
        repository.deleteById(id);
    }

    public Vendas atualizarVenda(Vendas vendas) {
        if (!repository.existsById(vendas.getId())) {
            throw new IllegalArgumentException("A venda com o ID fornecido não existe.");
        }
        return repository.save(vendas);
    }
}
