package br.com.desafioklok.apivendas.services;

import br.com.desafioklok.apivendas.models.Cobranca;
import br.com.desafioklok.apivendas.models.Produto;
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

    @Autowired
    CobrancaService cobrancaService;

    // Cria uma nova venda
    public Vendas create(Vendas vendas) {
        // Verifica se o cliente foi fornecido
        if (vendas.getCliente() == null) {
            throw new IllegalArgumentException("O cliente é obrigatório para criar uma venda.");
        }

        // Verifica se pelo menos um produto foi incluído na venda
        if (vendas.getProdutos() == null || vendas.getProdutos().isEmpty()) {
            throw new IllegalArgumentException("É necessário incluir pelo menos um produto na venda.");
        }

        // Calcula o valor total da venda com base nos produtos incluídos
        double valorTotal = vendas.getProdutos().stream()
                .mapToDouble(Produto::getPreco)
                .sum();

        vendas.setValor(valorTotal);

        // Salva a venda no banco de dados
        Vendas vendaCriada = repository.save(vendas);

        // Gera a cobrança para a venda
        Cobranca cobranca = cobrancaService.gerarCobranca(vendaCriada);

        return vendaCriada;
    }

    // Retorna todas as vendas cadastradas
    public List<Vendas> findAll() {
        return repository.findAll();
    }

    // Busca uma venda pelo ID
    public Optional<Vendas> findById(Long id) {
        return repository.findById(id);
    }

    // Exclui uma venda pelo ID
    public void delete(Long id) {
        // Verifica se a venda com o ID fornecido existe
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("A venda com o ID fornecido não existe.");
        }

        // Exclui a venda do banco de dados
        repository.deleteById(id);
    }

    // Atualiza uma venda existente
    public Vendas atualizarVenda(Vendas vendas) {
        // Verifica se a venda com o ID fornecido existe
        if (!repository.existsById(vendas.getId())) {
            throw new IllegalArgumentException("A venda com o ID fornecido não existe.");
        }

        // Salva a venda atualizada no banco de dados
        return repository.save(vendas);
    }
}
