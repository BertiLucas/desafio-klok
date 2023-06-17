package br.com.desafioklok.apivendas.services;

import br.com.desafioklok.apivendas.models.Cliente;
import br.com.desafioklok.apivendas.models.Cobranca;
import br.com.desafioklok.apivendas.models.Produto;
import br.com.desafioklok.apivendas.models.Vendas;
import br.com.desafioklok.apivendas.repositories.CobrancaRepository;
import br.com.desafioklok.apivendas.repositories.VendasRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VendasServiceTest {

    @Mock
    private VendasRepository vendasRepository;

    @Mock
    CobrancaRepository cobrancaRepository;

    @Mock
    private CobrancaService cobrancaService;

    @InjectMocks
    private VendasService vendasService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateVendas() {
        // Criação dos dados de teste
        Vendas vendas = new Vendas();
        vendas.setCliente(new Cliente()); // Defina os valores adequados para o cliente
        List<Produto> produtos = new ArrayList<>();
        Produto produto1 = new Produto(1L, "Produto 1", "Descrição do Produto 1", 10.0);
        Produto produto2 = new Produto(2L, "Produto 2", "Descrição do Produto 2", 20.0);
        produtos.add(produto1);
        produtos.add(produto2);
        vendas.setProdutos(produtos);

        // Mock do comportamento do repositório e serviço de cobrança
        Mockito.when(vendasRepository.save(Mockito.any(Vendas.class))).thenReturn(vendas);
        Cobranca cobrancaMock = new Cobranca(); // Crie uma instância de cobrança para simular o retorno do serviço
        Mockito.when(cobrancaService.gerarCobranca(Mockito.any(Vendas.class))).thenReturn(cobrancaMock);

        // Execução do método a ser testado
        Vendas vendaCriada = vendasService.create(vendas);

        // Verificação dos resultados
        assertNotNull(vendaCriada);
        assertEquals(vendas, vendaCriada);
        Mockito.verify(vendasRepository, Mockito.times(1)).save(vendas);
        Mockito.verify(cobrancaService, Mockito.times(1)).gerarCobranca(vendas);
        Mockito.verify(cobrancaRepository, Mockito.times(1)).save(cobrancaMock); // Verifica se a cobrança foi salva no repositório
    }

    @Test
    public void testFindAllVendas() {
        // Criação dos dados de teste
        List<Vendas> vendasList = new ArrayList<>();
        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        cliente1.setNome("Cliente 1");

        Vendas vendas1 = new Vendas();
        vendas1.setCliente(cliente1);
        vendas1.setValor(100.0);

        Cliente cliente2 = new Cliente();
        cliente2.setId(2L);
        cliente2.setNome("Cliente 2");

        Vendas vendas2 = new Vendas();
        vendas2.setCliente(cliente2);
        vendas2.setValor(200.0);

        vendasList.add(vendas1);
        vendasList.add(vendas2);

        // Mock do comportamento do repositório
        Mockito.when(vendasRepository.findAll()).thenReturn(vendasList);

        // Execução do método a ser testado
        List<Vendas> foundVendasList = vendasService.findAll();

        // Verificação dos resultados
        assertEquals(2, foundVendasList.size());
        assertEquals(vendasList, foundVendasList);
    }

    @Test
    public void testFindByIdExistingVendas() {
        // Criação dos dados de teste
        Long vendasId = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente 1");

        Vendas vendas = new Vendas();
        vendas.setId(vendasId);
        vendas.setCliente(cliente);
        vendas.setValor(100.0);

        // Mock do comportamento do repositório
        Mockito.when(vendasRepository.findById(vendasId)).thenReturn(Optional.of(vendas));

        // Execução do método a ser testado
        Optional<Vendas> foundVendas = vendasService.findById(vendasId);

        // Verificação dos resultados
        Assertions.assertTrue(foundVendas.isPresent());
        assertEquals(vendasId, foundVendas.get().getId());
        assertEquals(cliente, foundVendas.get().getCliente());
        assertEquals(100.0, foundVendas.get().getValor());
    }

    @Test
    public void testDeleteExistingVendas() {
        // Criação dos dados de teste
        Long vendasId = 1L;

        // Mock do comportamento do repositório
        Mockito.when(vendasRepository.existsById(vendasId)).thenReturn(true);

        // Execução do método a ser testado
        Assertions.assertDoesNotThrow(() -> vendasService.delete(vendasId));

        // Verificação dos resultados
        Mockito.verify(vendasRepository, Mockito.times(1)).deleteById(vendasId);
    }

    @Test
    public void testDeleteNonExistingVendas() {
        // Criação dos dados de teste
        Long vendasId = 1L;

        // Mock do comportamento do repositório
        Mockito.when(vendasRepository.existsById(vendasId)).thenReturn(false);

        // Execução do método a ser testado
        Assertions.assertThrows(IllegalArgumentException.class, () -> vendasService.delete(vendasId));

        // Verificação dos resultados
        Mockito.verify(vendasRepository, Mockito.times(0)).deleteById(vendasId);
    }

    @Test
    public void testAtualizarVendaExistingVendas() {
        // Criação dos dados de teste
        Long vendasId = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente 1");

        Vendas vendas = new Vendas();
        vendas.setId(vendasId);
        vendas.setCliente(cliente);
        vendas.setValor(100.0);

        // Mock do comportamento do repositório
        Mockito.when(vendasRepository.existsById(vendasId)).thenReturn(true);
        Mockito.when(vendasRepository.save(Mockito.any(Vendas.class))).thenReturn(vendas);

        // Execução do método a ser testado
        Vendas updatedVendas = vendasService.atualizarVenda(vendas);

        // Verificação dos resultados
        assertNotNull(updatedVendas);
        assertEquals(vendasId, updatedVendas.getId());
        assertEquals(cliente, updatedVendas.getCliente());
        assertEquals(100.0, updatedVendas.getValor());
    }

    @Test
    public void testAtualizarVendaNonExistingVendas() {
        // Criação dos dados de teste
        Long vendasId = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente 1");

        Vendas vendas = new Vendas();
        vendas.setId(vendasId);
        vendas.setCliente(cliente);
        vendas.setValor(100.0);

        // Mock do comportamento do repositório
        Mockito.when(vendasRepository.existsById(vendasId)).thenReturn(false);

        // Execução do método a ser testado
        Assertions.assertThrows(IllegalArgumentException.class, () -> vendasService.atualizarVenda(vendas));

        // Verificação dos resultados
        Mockito.verify(vendasRepository, Mockito.times(0)).save(Mockito.any(Vendas.class));
    }
}
