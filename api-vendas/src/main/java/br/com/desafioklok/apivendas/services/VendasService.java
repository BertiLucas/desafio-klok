package br.com.desafioklok.apivendas.services;

import br.com.desafioklok.apivendas.dtos.VendasDTO;
import br.com.desafioklok.apivendas.models.Cliente;
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

    public Vendas create(VendasDTO vendasDTO) {
        if (vendasDTO.getCliente() == null) {
            throw new IllegalArgumentException("O cliente é obrigatório para criar uma venda.");
        }
        if (vendasDTO.getProdutos() == null || vendasDTO.getProdutos().isEmpty()) {
            throw new IllegalArgumentException("É necessário incluir pelo menos um produto na venda.");
        }
        double valorTotal = vendasDTO.getProdutos().stream()
                .mapToDouble(Produto::getPreco)
                .sum();
        Vendas vendas = new Vendas();
        vendas.setCliente(vendasDTO.getCliente());
        vendas.setProdutos(vendasDTO.getProdutos());
        vendas.setValor(valorTotal);
        Vendas vendaCriada = repository.save(vendas);
        Cobranca cobranca = cobrancaService.gerarCobranca(vendaCriada);
        return vendaCriada;
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

    public Vendas update(Vendas vendas) {
        if (!repository.existsById(vendas.getId())) {
            throw new IllegalArgumentException("A venda com o ID fornecido não existe.");
        }
        return repository.save(vendas);
    }

    public Vendas updateVendas(Vendas vendas) {
        Long vendaId = vendas.getId();
        Optional<Vendas> vendaOptional = repository.findById(vendaId);

        if (!vendaOptional.isPresent()) {
            throw new IllegalArgumentException("A venda com o ID fornecido não existe.");
        }

        Vendas venda = vendaOptional.get();

        Cliente novoCliente = vendas.getCliente();
        List<Produto> novosProdutos = vendas.getProdutos();
        Cobranca novaCobranca = vendas.getCobranca();

        if (novoCliente != null) {
            venda.setCliente(novoCliente);
        }

        if (novosProdutos != null) {
            venda.setProdutos(novosProdutos);
            double novoValorTotal = novosProdutos.stream()
                    .mapToDouble(Produto::getPreco)
                    .sum();
            venda.setValor(novoValorTotal);
        }

        if (novaCobranca != null) {
            venda.setCobranca(novaCobranca);
        }

        return repository.save(venda);
    }

}
