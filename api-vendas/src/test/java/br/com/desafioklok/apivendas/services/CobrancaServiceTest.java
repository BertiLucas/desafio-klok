package br.com.desafioklok.apivendas.services;

import br.com.desafioklok.apivendas.models.Cobranca;
import br.com.desafioklok.apivendas.models.Vendas;
import br.com.desafioklok.apivendas.repositories.CobrancaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CobrancaServiceTest {

    @Mock
    private CobrancaRepository cobrancaRepository;

    @InjectMocks
    private CobrancaService cobrancaService;

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

        // Mock do repositório
        when(cobrancaRepository.save(any(Cobranca.class))).thenReturn(cobranca);

        // Chamada ao serviço
        Cobranca result = cobrancaService.gerarCobranca(venda);

        // Verificação do resultado
        assertNotNull(result);
        assertEquals(cobranca, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGerarCobrancaWithNullVenda() {
        // Chamada ao serviço com venda nula
        cobrancaService.gerarCobranca(null);
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

        // Mock do repositório
        when(cobrancaRepository.findAll()).thenReturn(cobrancas);

        // Chamada ao serviço
        List<Cobranca> result = cobrancaService.findAll();

        // Verificação do resultado
        assertNotNull(result);
        assertEquals(cobrancas.size(), result.size());
        assertEquals(cobrancas, result);
    }

    @Test
    public void testFindById() {
        // Dados de teste
        Long cobrancaId = 1L;

        Cobranca cobranca = new Cobranca();
        cobranca.setId(cobrancaId);
        cobranca.setValor(100.0);

        // Mock do repositório
        when(cobrancaRepository.findById(cobrancaId)).thenReturn(Optional.of(cobranca));

        // Chamada ao serviço
        Optional<Cobranca> result = cobrancaService.findById(cobrancaId);

        // Verificação do resultado
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(cobranca, result.get());
    }

    @Test
    public void testFindByIdWithNonexistentCobranca() {
        // Dados de teste
        Long cobrancaId = 1L;

        // Mock do repositório
        when(cobrancaRepository.findById(cobrancaId)).thenReturn(Optional.empty());

        // Chamada ao serviço
        Optional<Cobranca> result = cobrancaService.findById(cobrancaId);

        // Verificação do resultado
        assertNotNull(result);
        assertFalse(result.isPresent());
    }

    @Test
    public void testDelete() {
        // Dados de teste
        Long cobrancaId = 1L;

        // Mock do repositório
        when(cobrancaRepository.existsById(cobrancaId)).thenReturn(true);

        // Chamada ao serviço
        cobrancaService.delete(cobrancaId);

        // Verificação do repositório
        verify(cobrancaRepository, times(1)).deleteById(cobrancaId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteWithNonexistentCobranca() {
        // Dados de teste
        Long cobrancaId = 1L;

        // Mock do repositório
        when(cobrancaRepository.existsById(cobrancaId)).thenReturn(false);

        // Chamada ao serviço
        cobrancaService.delete(cobrancaId);
    }
}


