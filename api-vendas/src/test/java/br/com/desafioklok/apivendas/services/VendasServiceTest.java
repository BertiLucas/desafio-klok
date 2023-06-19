package br.com.desafioklok.apivendas.services;

import br.com.desafioklok.apivendas.dtos.VendasDTO;
import br.com.desafioklok.apivendas.models.Cliente;
import br.com.desafioklok.apivendas.models.Produto;
import br.com.desafioklok.apivendas.models.Vendas;
import br.com.desafioklok.apivendas.repositories.VendasRepository;
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

public class VendasServiceTest {

    @Mock
    private VendasRepository vendasRepository;

    @Mock
    private CobrancaService cobrancaService;

    @InjectMocks
    private VendasService vendasService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateVenda() {
        VendasDTO vendasDTO = new VendasDTO();
        vendasDTO.setCliente(new Cliente());
        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto());
        vendasDTO.setProdutos(produtos);

        Vendas venda = new Vendas();
        venda.setCliente(vendasDTO.getCliente());
        venda.setProdutos(vendasDTO.getProdutos());
        venda.setValor(0.0);

        when(vendasRepository.save(venda)).thenReturn(venda);

        Vendas createdVenda = vendasService.create(vendasDTO);

        assertNotNull(createdVenda);
        assertEquals(venda, createdVenda);

        verify(vendasRepository, times(1)).save(venda);
        verify(cobrancaService, times(1)).gerarCobranca(venda);
    }

    @Test
    public void testFindAll() {
        List<Vendas> vendas = new ArrayList<>();
        when(vendasRepository.findAll()).thenReturn(vendas);

        List<Vendas> foundVendas = vendasService.findAll();

        assertEquals(vendas, foundVendas);

        verify(vendasRepository, times(1)).findAll();
    }

    @Test
    public void testFindByIdExistingVenda() {
        Long vendaId = 1L;
        Vendas venda = new Vendas();
        when(vendasRepository.findById(vendaId)).thenReturn(Optional.of(venda));

        Optional<Vendas> foundVenda = vendasService.findById(vendaId);

        assertTrue(foundVenda.isPresent());
        assertEquals(venda, foundVenda.get());

        verify(vendasRepository, times(1)).findById(vendaId);
    }

    @Test
    public void testFindByIdNonExistingVenda() {
        Long vendaId = 1L;
        when(vendasRepository.findById(vendaId)).thenReturn(Optional.empty());

        Optional<Vendas> foundVenda = vendasService.findById(vendaId);

        assertFalse(foundVenda.isPresent());

        verify(vendasRepository, times(1)).findById(vendaId);
    }

    @Test
    public void testDeleteExistingVenda() {
        Long vendaId = 1L;
        when(vendasRepository.existsById(vendaId)).thenReturn(true);

        vendasService.delete(vendaId);

        verify(vendasRepository, times(1)).existsById(vendaId);
        verify(vendasRepository, times(1)).deleteById(vendaId);
    }

    @Test
    public void testDeleteNonExistingVenda() {
        Long vendaId = 1L;
        when(vendasRepository.existsById(vendaId)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> vendasService.delete(vendaId));

        verify(vendasRepository, times(1)).existsById(vendaId);
        verify(vendasRepository, never()).deleteById(vendaId);
    }

}
