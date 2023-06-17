package br.com.desafioklok.apivendas.services;

import br.com.desafioklok.apivendas.models.Cobranca;
import br.com.desafioklok.apivendas.models.Vendas;
import br.com.desafioklok.apivendas.repositories.CobrancaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CobrancaService {

    @Autowired
    CobrancaRepository cobrancaRepository;

    // Gera uma cobrança com base em uma venda
    public Cobranca gerarCobranca(Vendas venda) {
        // Verifica se a venda foi fornecida
        if (venda == null) {
            throw new IllegalArgumentException("A venda é obrigatória para gerar a cobrança.");
        }

        double valorCobranca = venda.getValor();

        // Lógica adicional para gerar a cobrança completa
        Cobranca cobranca = new Cobranca();
        cobranca.setVenda(venda);
        cobranca.setValor(valorCobranca);

        // Persistir a cobrança no banco de dados
        return cobrancaRepository.save(cobranca);
    }

    // Retorna todas as cobranças
    public List<Cobranca> findAll() {
        return cobrancaRepository.findAll();
    }

    // Busca uma cobrança pelo ID
    public Optional<Cobranca> findById(Long id) {
        return cobrancaRepository.findById(id);
    }

    // Exclui uma cobrança pelo ID
    public void delete(Long id) {
        // Verifica se a cobrança com o ID fornecido existe
        if (!cobrancaRepository.existsById(id)) {
            throw new IllegalArgumentException("A cobrança com o ID fornecido não existe.");
        }

        // Exclui a cobrança do banco de dados
        cobrancaRepository.deleteById(id);
    }

}
