package br.com.desafioklok.apipagamentos.services;

import br.com.desafioklok.apipagamentos.models.Pagamento;
import br.com.desafioklok.apipagamentos.repositories.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PagamentoServiceTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    private PagamentoService pagamentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        pagamentoService = new PagamentoService(pagamentoRepository);
    }

    @Test
    void testCreate() {
        Pagamento pagamento = new Pagamento();
        pagamento.setValor(BigDecimal.valueOf(100.00));

        when(pagamentoRepository.save(pagamento)).thenReturn(pagamento);

        Pagamento result = pagamentoService.create(pagamento);

        assertEquals(pagamento, result);

        verify(pagamentoRepository, times(1)).save(pagamento);
    }

    @Test
    void testFindAll() {
        List<Pagamento> pagamentos = Arrays.asList(new Pagamento(), new Pagamento());

        when(pagamentoRepository.findAll()).thenReturn(pagamentos);

        List<Pagamento> result = pagamentoService.findAll();

        assertEquals(pagamentos, result);

        verify(pagamentoRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Long id = 1L;
        Pagamento pagamento = new Pagamento();
        pagamento.setId(id);
        Optional<Pagamento> optionalPagamento = Optional.of(pagamento);

        when(pagamentoRepository.findById(id)).thenReturn(optionalPagamento);

        Optional<Pagamento> result = pagamentoService.findById(id);

        assertEquals(optionalPagamento, result);

        verify(pagamentoRepository, times(1)).findById(id);
    }

    @Test
    void testDelete() {
        Long id = 1L;

        pagamentoService.delete(id);

        verify(pagamentoRepository, times(1)).deleteById(id);
    }
}
