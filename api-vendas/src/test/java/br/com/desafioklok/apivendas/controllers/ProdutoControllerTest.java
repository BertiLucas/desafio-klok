package br.com.desafioklok.apivendas.controllers;

import br.com.desafioklok.apivendas.models.Produto;
import br.com.desafioklok.apivendas.services.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProdutoControllerTest {

    @Mock
    private ProdutoService service;

    @InjectMocks
    private ProdutoController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduto() {
        // Criação do objeto Produto de teste
        Produto produto = new Produto();
        produto.setNome("Produto 1");
        produto.setDescricao("Descrição do Produto 1");
        produto.setPreco(10.0);

        // Configuração do comportamento do serviço mockado
        when(service.create(produto)).thenReturn(produto);

        // Chamada do método do controlador a ser testado
        ResponseEntity<Produto> response = controller.create(produto);

        // Verificação dos resultados
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(produto, response.getBody());
        verify(service, times(1)).create(produto); // Verifica se o método "create" do serviço foi chamado uma vez com o produto de teste
    }

    @Test
    void testFindAll() {
        // Criação de uma lista de produtos de teste
        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto(1L, "Produto 1", "Descrição do Produto 1", 10.0));
        produtos.add(new Produto(2L, "Produto 2", "Descrição do Produto 2", 20.0));

        // Configuração do comportamento do serviço mockado
        when(service.findAll()).thenReturn(produtos);

        // Chamada do método do controlador a ser testado
        List<Produto> foundProdutos = controller.findAll();

        // Verificação dos resultados
        assertEquals(produtos, foundProdutos);
        verify(service, times(1)).findAll(); // Verifica se o método "findAll" do serviço foi chamado uma vez
    }

    @Test
    void testFindById() {
        // ID do produto de teste
        Long produtoId = 1L;

        // Criação do objeto Produto de teste
        Produto produto = new Produto(produtoId, "Produto 1", "Descrição do Produto 1", 10.0);

        // Configuração do comportamento do serviço mockado
        when(service.findById(produtoId)).thenReturn(Optional.of(produto));

        // Chamada do método do controlador a ser testado
        Optional<Produto> foundProduto = controller.findById(produtoId);

        // Verificação dos resultados
        assertEquals(Optional.of(produto), foundProduto);
        verify(service, times(1)).findById(produtoId); // Verifica se o método "findById" do serviço foi chamado uma vez com o ID de teste
    }

    @Test
    void testDeleteProduto() {
        // ID do produto a ser excluído
        Long produtoId = 1L;

        // Configuração do comportamento do serviço mockado
        doNothing().when(service).delete(produtoId);

        // Chamada do método do controlador a ser testado
        controller.delete(produtoId);

        verify(service, times(1)).delete(produtoId); // Verifica se o método "delete" do serviço foi chamado uma vez com o ID de teste
    }
}
