package br.com.desafioklok.apivendas.controller;

import br.com.desafioklok.apivendas.controllers.ProdutoController;
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

public class ProdutoControllerTest {

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private ProdutoController produtoController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduto() {
        Produto produto = new Produto(1L, "Notebook", "Notebook de última geração", 2500.00);
        when(produtoService.create(produto)).thenReturn(produto);

        ResponseEntity<Produto> response = produtoController.createProduto(produto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(produto, response.getBody());

        verify(produtoService, times(1)).create(produto);
    }

    @Test
    public void testGetAllProdutos() {
        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto(1L, "Notebook", "Notebook de última geração", 2500.00));
        produtos.add(new Produto(2L, "Smartphone", "Smartphone com câmera de alta resolução", 1500.00));
        when(produtoService.findAll()).thenReturn(produtos);

        ResponseEntity<List<Produto>> response = produtoController.getAllProdutos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtos, response.getBody());

        verify(produtoService, times(1)).findAll();
    }

    @Test
    public void testGetProdutoByIdExistingProduto() {
        Long produtoId = 1L;
        Produto produto = new Produto(produtoId, "Notebook", "Notebook de última geração", 2500.00);
        when(produtoService.findById(produtoId)).thenReturn(Optional.of(produto));

        ResponseEntity<Produto> response = produtoController.getProdutoById(produtoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produto, response.getBody());

        verify(produtoService, times(1)).findById(produtoId);
    }

    @Test
    public void testGetProdutoByIdNonExistingProduto() {
        Long produtoId = 1L;
        when(produtoService.findById(produtoId)).thenReturn(Optional.empty());

        ResponseEntity<Produto> response = produtoController.getProdutoById(produtoId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(produtoService, times(1)).findById(produtoId);
    }

    @Test
    public void testDeleteProduto() {
        Long produtoId = 1L;

        ResponseEntity<Void> response = produtoController.deleteProduto(produtoId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(produtoService, times(1)).delete(produtoId);
    }

    @Test
    public void testUpdateProduto() {
        // Dados de entrada
        Long id = 1L;
        Produto produto = new Produto();
        produto.setId(id);

        // Produto atualizado
        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setId(id);
        produtoAtualizado.setNome("Novo Nome");
        produtoAtualizado.setDescricao("Nova Descrição");
        produtoAtualizado.setPreco(10.0);

        // Mock do serviço
        when(produtoService.update(produto)).thenReturn(produtoAtualizado);

        // Chamada do método no controller
        ResponseEntity<Produto> response = produtoController.updateProduto(id, produto);

        // Verificações
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtoAtualizado, response.getBody());
        verify(produtoService, times(1)).update(produto);
    }

    @Test
    public void testUpdateProdutoNotFound() {
        // Dados de entrada
        Long id = 1L;
        Produto produto = new Produto();
        produto.setId(id);

        // Mock do serviço lançando exceção
        when(produtoService.update(produto)).thenThrow(new IllegalArgumentException());

        // Chamada do método no controller
        ResponseEntity<Produto> response = produtoController.updateProduto(id, produto);

        // Verificações
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(produtoService, times(1)).update(produto);
    }
}

