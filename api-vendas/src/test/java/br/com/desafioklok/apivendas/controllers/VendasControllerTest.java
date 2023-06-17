package br.com.desafioklok.apivendas.controllers;

import br.com.desafioklok.apivendas.models.Cliente;
import br.com.desafioklok.apivendas.models.Produto;
import br.com.desafioklok.apivendas.models.Vendas;
import br.com.desafioklok.apivendas.services.ClienteService;
import br.com.desafioklok.apivendas.services.VendasService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VendasControllerTest {

    @Mock
    private VendasService vendasService;

    @InjectMocks
    private VendasController vendasController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateVendas() {
        // Criação de um objeto Vendas para enviar no corpo da requisição
        Vendas vendas = new Vendas();
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente 1");
        vendas.setCliente(cliente);
        List<Produto> produtos = new ArrayList<>();
        Produto produto1 = new Produto();
        produto1.setId(1L);
        produto1.setNome("Produto 1");
        produto1.setPreco(10.0);
        produtos.add(produto1);
        vendas.setProdutos(produtos);

        // Configuração do valor total esperado
        double valorTotal = produtos.stream().mapToDouble(Produto::getPreco).sum();
        vendas.setValor(valorTotal);

        // Configuração do comportamento esperado do serviço de vendas
        Mockito.when(vendasService.create(Mockito.any(Vendas.class))).thenReturn(vendas);

        // Chamada do método create do controlador
        ResponseEntity<Vendas> response = vendasController.create(vendas);

        // Verificação dos resultados
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(vendas, response.getBody());

        // Verificação adicional: valor total calculado corretamente
        Assertions.assertEquals(valorTotal, response.getBody().getValor());
    }

    @Test
    public void testFindAllVendas() {
        // Criação de uma lista de vendas para simular o retorno do serviço
        List<Vendas> vendasList = new ArrayList<>();

        // Configuração do comportamento esperado do serviço de vendas
        Mockito.when(vendasService.findAll()).thenReturn(vendasList);

        // Chamada do método findAll do controlador
        List<Vendas> response = vendasController.findAll();

        // Verificação dos resultados
        Assertions.assertNotNull(response);
        Assertions.assertEquals(vendasList, response);
    }

    @Test
    public void testFindByIdExistingVendas() {
        // Configuração do ID da venda a ser buscada
        Long vendasId = 1L;

        // Criação de um objeto Vendas para simular a venda encontrada
        Vendas vendas = new Vendas();
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente 1");
        vendas.setId(vendasId);
        vendas.setCliente(cliente);
        List<Produto> produtos = new ArrayList<>();
        Produto produto1 = new Produto();
        produto1.setId(1L);
        produto1.setNome("Produto 1");
        produto1.setPreco(10.0);
        produtos.add(produto1);
        vendas.setProdutos(produtos);
        vendas.setValor(100.0);

        // Configuração do comportamento esperado do serviço de vendas
        Mockito.when(vendasService.findById(vendasId)).thenReturn(Optional.of(vendas));

        // Chamada do método findById do controlador
        Optional<Vendas> foundVendas = vendasController.findById(vendasId);

        // Verificação dos resultados
        Assertions.assertTrue(foundVendas.isPresent());
        Assertions.assertEquals(vendasId, foundVendas.get().getId());
        Assertions.assertEquals(cliente, foundVendas.get().getCliente());
        Assertions.assertEquals(produtos, foundVendas.get().getProdutos());
        Assertions.assertEquals(100.0, foundVendas.get().getValor());
    }

    @Test
    public void testDeleteVendas() {
        // Configuração do ID da venda a ser deletada
        Long vendaId = 1L;

        // Chamada do método delete do controlador
        vendasController.delete(vendaId);

        // Verificação adicional: serviço de vendas foi chamado corretamente
        Mockito.verify(vendasService, Mockito.times(1)).delete(vendaId);
    }

    @Test
    public void testAtualizarVenda_Success() {

        Long vendaId = 1L;

        // Cria uma venda existente com o ID fornecido
        Vendas vendaExistente = new Vendas();
        vendaExistente.setId(vendaId);

        // Cria uma venda atualizada com o mesmo ID
        Vendas vendaAtualizada = new Vendas();
        vendaAtualizada.setId(vendaId);

        // Configura o comportamento do serviço para retornar a venda atualizada
        Mockito.when(vendasService.atualizarVenda(Mockito.any(Vendas.class))).thenReturn(vendaAtualizada);

        // Act
        ResponseEntity<Vendas> response = vendasController.atualizarVenda(vendaId, vendaAtualizada);

        // Assert
        // Verifica se a resposta não é nula
        Assertions.assertNotNull(response);
        // Verifica se o status HTTP é 200 (OK)
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        // Verifica se o corpo da resposta contém a venda atualizada
        Assertions.assertEquals(vendaAtualizada, response.getBody());
    }

    @RunWith(MockitoJUnitRunner.class)
    public static class ClienteControllerTest {

        @Mock
        private ClienteService clienteService;

        @InjectMocks
        private ClienteController clienteController;

        @Test
        public void testCreateCliente() {
            // Mock do cliente a ser criado
            Cliente cliente = new Cliente();
            cliente.setId(1L);
            cliente.setNome("João");
            cliente.setCpf("123456789");
            cliente.setEmail("joao@example.com");
            cliente.setEndereco("Rua A");

            Mockito.when(clienteService.create(Mockito.any(Cliente.class))).thenReturn(cliente);

            ResponseEntity<Cliente> responseEntity = clienteController.create(cliente);

            // Verificar se o status code retornado é 201 - Created
            assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

            // Verificar se o cliente retornado no corpo da resposta é o mesmo que foi criado
            assertEquals(cliente, responseEntity.getBody());
        }

        @Test
        public void testFindAllClientes() {
            // Mock da lista de clientes
            List<Cliente> clientes = Arrays.asList(
                    new Cliente(1L, "João", "123456789", "joao@example.com", "Rua A"),
                    new Cliente(2L, "Maria", "987654321", "maria@example.com", "Rua B")
            );

            Mockito.when(clienteService.findAll()).thenReturn(clientes);

            List<Cliente> result = clienteController.findAll();

            // Verificar se a lista retornada é a mesma que foi mockada
            assertEquals(clientes, result);
        }

        @Test
        public void testFindClienteById() {
            // Mock do cliente a ser retornado
            Cliente cliente = new Cliente(1L, "João", "123456789", "joao@example.com", "Rua A");

            Mockito.when(clienteService.findById(Mockito.eq(1L))).thenReturn(Optional.of(cliente));

            Optional<Cliente> result = clienteController.findById(1L);

            // Verificar se o cliente retornado é o mesmo que foi mockado
            assertEquals(Optional.of(cliente), result);
        }

        @Test
        public void testDeleteCliente() {
            ResponseEntity<Void> responseEntity = clienteController.delete(1L);

            // Verificar se o status code retornado é 204 - No Content
            assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

            // Verificar se o serviço de cliente foi chamado uma vez para excluir o cliente com o ID fornecido
            Mockito.verify(clienteService, Mockito.times(1)).delete(1L);
        }
    }
}
