package br.com.desafioklok.apivendas.controllers;

import br.com.desafioklok.apivendas.models.Cobranca;
import br.com.desafioklok.apivendas.models.Vendas;
import br.com.desafioklok.apivendas.services.CobrancaService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

public class CobrancaControllerTest {

    @Mock
    private CobrancaService cobrancaService;

    @InjectMocks
    private CobrancaController cobrancaController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGerarCobranca() {
        // Dados de teste
        Vendas venda = new Vendas();
        venda.setId(1L);
        venda.setValor(100.0);

        Cobranca cobranca = new Cobranca();
        cobranca.setId(1L);
        cobranca.setVenda(venda);
        cobranca.setValor(100.0);

        // Mock do serviço
        when(cobrancaService.gerarCobranca(venda)).thenReturn(cobranca);

        // Chamada ao endpoint
        ResponseEntity<Cobranca> response = cobrancaController.gerarCobranca(venda);

        // Verificação do resultado
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cobranca, response.getBody());
    }

    @Test
    public void testFindAll() {
        // Dados de teste
        Cobranca cobranca1 = new Cobranca();
        cobranca1.setId(1L);
        cobranca1.setValor(100.0);

        Cobranca cobranca2 = new Cobranca();
        cobranca2.setId(2L);
        cobranca2.setValor(200.0);

        List<Cobranca> cobrancas = Arrays.asList(cobranca1, cobranca2);

        // Mock do serviço
        when(cobrancaService.findAll()).thenReturn(cobrancas);

        // Chamada ao endpoint
        List<Cobranca> response = cobrancaController.findAll();

        // Verificação do resultado
        assertEquals(cobrancas, response);
    }

    @Test
    public void testFindById() {
        // Dados de teste
        Long cobrancaId = 1L;

        Cobranca cobranca = new Cobranca();
        cobranca.setId(cobrancaId);
        cobranca.setValor(100.0);

        // Mock do serviço
        when(cobrancaService.findById(cobrancaId)).thenReturn(Optional.of(cobranca));

        // Chamada ao endpoint
        Optional<Cobranca> response = cobrancaController.findById(cobrancaId);

        // Verificação do resultado
        assertEquals(Optional.of(cobranca), response);
    }

    @Test
    public void testFindByIdWithNonexistentCobranca() {
        // Dados de teste
        Long cobrancaId = 1L;

        // Mock do serviço
        when(cobrancaService.findById(cobrancaId)).thenReturn(Optional.empty());

        // Chamada ao endpoint
        Optional<Cobranca> response = cobrancaController.findById(cobrancaId);

        // Verificação do resultado
        assertEquals(Optional.empty(), response);
    }

    @Test
    public void testDelete() {
        // Dados de teste
        Long cobrancaId = 1L;

        // Chamada ao endpoint
        cobrancaController.delete(cobrancaId);

        // Verificação do serviço
        verify(cobrancaService).delete(cobrancaId);
    }
}
