package br.com.desafioklok.apipagamentos.controllers;

import br.com.desafioklok.apipagamentos.models.Pagamento;
import br.com.desafioklok.apipagamentos.services.PagamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PagamentoControllerTest {

    @Mock
    private PagamentoService pagamentoService;

    private PagamentoController pagamentoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        pagamentoController = new PagamentoController(pagamentoService);
    }

    @Test
    void testCreate() {
        Pagamento pagamento = new Pagamento();
        pagamento.setValor(BigDecimal.valueOf(100.00));

        when(pagamentoService.create(pagamento)).thenReturn(pagamento);

        ResponseEntity<Pagamento> responseEntity = pagamentoController.create(pagamento);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(pagamento, responseEntity.getBody());

        verify(pagamentoService, times(1)).create(pagamento);
    }

    @Test
    void testFindAll() {
        List<Pagamento> pagamentos = new ArrayList<>();

        when(pagamentoService.findAll()).thenReturn(pagamentos);

        List<Pagamento> result = pagamentoController.findAll();

        assertEquals(pagamentos, result);

        verify(pagamentoService, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Long id = 1L;
        Pagamento pagamento = new Pagamento();
        pagamento.setId(id);
        Optional<Pagamento> optionalPagamento = Optional.of(pagamento);

        when(pagamentoService.findById(id)).thenReturn(optionalPagamento);

        Optional<Pagamento> result = pagamentoController.findById(id);

        assertEquals(optionalPagamento, result);

        verify(pagamentoService, times(1)).findById(id);
    }

    @Test
    void testDelete() {
        Long id = 1L;

        ResponseEntity<Void> responseEntity = pagamentoController.delete(id);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        verify(pagamentoService, times(1)).delete(id);
    }
}
