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

    public Cobranca gerarCobranca(Vendas venda) {
        if (venda == null) {
            throw new IllegalArgumentException("A venda é obrigatória para gerar a cobrança.");
        }
        double valorCobranca = venda.getValor();
        Cobranca cobranca = new Cobranca();
        cobranca.setVenda(venda);
        cobranca.setValor(valorCobranca);
        return cobrancaRepository.save(cobranca);
    }

    public List<Cobranca> findAll() {
        return cobrancaRepository.findAll();
    }

    public Optional<Cobranca> findById(Long id) {
        return cobrancaRepository.findById(id);
    }

    public void delete(Long id) {
        if (!cobrancaRepository.existsById(id)) {
            throw new IllegalArgumentException("A cobrança com o ID fornecido não existe.");
        }
        cobrancaRepository.deleteById(id);
    }

}
