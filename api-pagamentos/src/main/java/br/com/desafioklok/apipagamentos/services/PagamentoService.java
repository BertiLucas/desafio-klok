package br.com.desafioklok.apipagamentos.services;

import br.com.desafioklok.apipagamentos.models.Pagamento;
import br.com.desafioklok.apipagamentos.repositories.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    public Pagamento create(Pagamento pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    public List<Pagamento> findAll() {
        return pagamentoRepository.findAll();
    }

    public Optional<Pagamento> findById(Long id) {
        return pagamentoRepository.findById(id);
    }

    public void delete(Long id) {
        pagamentoRepository.deleteById(id);
    }
}
