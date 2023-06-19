package br.com.desafioklok.apivendas.services;

import br.com.desafioklok.apivendas.models.Cobranca;
import br.com.desafioklok.apivendas.models.Vendas;
import br.com.desafioklok.apivendas.repositories.CobrancaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CobrancaServiceTest {

    @Mock
    private CobrancaRepository cobrancaRepository;

    @InjectMocks
    private CobrancaService cobrancaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGerarCobranca() {
        Vendas venda = new Vendas();
        venda.setId(1L);
        venda.setValor(100.0);

        Cobranca cobranca = new Cobranca();
        cobranca.setVenda(venda);
        cobranca.setValor(venda.getValor());

        when(cobrancaRepository.save(cobranca)).thenReturn(cobranca);

        Cobranca geradaCobranca = cobrancaService.gerarCobranca(venda);

        assertNotNull(geradaCobranca);
        assertEquals(cobranca, geradaCobranca);

        verify(cobrancaRepository, times(1)).save(cobranca);
    }

    @Test
    public void testFindAll() {
        List<Cobranca> cobrancas = new ArrayList<>();
        when(cobrancaRepository.findAll()).thenReturn(cobrancas);

        List<Cobranca> foundCobrancas = cobrancaService.findAll();

        assertEquals(cobrancas, foundCobrancas);

        verify(cobrancaRepository, times(1)).findAll();
    }

    @Test
    public void testFindByIdExistingCobranca() {
        Long cobrancaId = 1L;
        Cobranca cobranca = new Cobranca();
        when(cobrancaRepository.findById(cobrancaId)).thenReturn(Optional.of(cobranca));

        Optional<Cobranca> foundCobranca = cobrancaService.findById(cobrancaId);

        assertTrue(foundCobranca.isPresent());
        assertEquals(cobranca, foundCobranca.get());

        verify(cobrancaRepository, times(1)).findById(cobrancaId);
    }

    @Test
    public void testFindByIdNonExistingCobranca() {
        Long cobrancaId = 1L;
        when(cobrancaRepository.findById(cobrancaId)).thenReturn(Optional.empty());

        Optional<Cobranca> foundCobranca = cobrancaService.findById(cobrancaId);

        assertFalse(foundCobranca.isPresent());

        verify(cobrancaRepository, times(1)).findById(cobrancaId);
    }

    @Test
    public void testDeleteExistingCobranca() {
        Long cobrancaId = 1L;
        when(cobrancaRepository.existsById(cobrancaId)).thenReturn(true);

        cobrancaService.delete(cobrancaId);

        verify(cobrancaRepository, times(1)).existsById(cobrancaId);
        verify(cobrancaRepository, times(1)).deleteById(cobrancaId);
    }

    @Test
    public void testDeleteNonExistingCobranca() {
        Long cobrancaId = 1L;
        when(cobrancaRepository.existsById(cobrancaId)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> cobrancaService.delete(cobrancaId));

        verify(cobrancaRepository, times(1)).existsById(cobrancaId);
        verify(cobrancaRepository, never()).deleteById(cobrancaId);
    }
}
