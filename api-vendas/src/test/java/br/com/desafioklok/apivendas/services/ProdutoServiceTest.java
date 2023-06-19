package br.com.desafioklok.apivendas.services;

import br.com.desafioklok.apivendas.models.Produto;
import br.com.desafioklok.apivendas.repositories.ProdutoRepository;
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

public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateValidProduto() {
        Produto produto = new Produto(1L, "Notebook", "Notebook de última geração", 2500.00);
        when(produtoRepository.save(produto)).thenReturn(produto);

        Produto result = produtoService.create(produto);

        assertEquals(produto, result);

        verify(produtoRepository, times(1)).save(produto);
    }

    @Test
    public void testCreateInvalidProduto() {
        Produto produto = new Produto(1L, "", "Notebook de última geração", 2500.00);

        assertThrows(IllegalArgumentException.class, () -> produtoService.create(produto));

        verify(produtoRepository, never()).save(produto);
    }

    @Test
    public void testFindAllProdutos() {
        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto(1L, "Notebook", "Notebook de última geração", 2500.00));
        produtos.add(new Produto(2L, "Smartphone", "Smartphone com câmera de alta resolução", 1500.00));
        when(produtoRepository.findAll()).thenReturn(produtos);

        List<Produto> result = produtoService.findAll();

        assertEquals(produtos, result);

        verify(produtoRepository, times(1)).findAll();
    }

    @Test
    public void testFindProdutoByIdExistingProduto() {
        Long produtoId = 1L;
        Produto produto = new Produto(produtoId, "Notebook", "Notebook de última geração", 2500.00);
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));

        Optional<Produto> result = produtoService.findById(produtoId);

        assertTrue(result.isPresent());
        assertEquals(produto, result.get());

        verify(produtoRepository, times(1)).findById(produtoId);
    }

    @Test
    public void testFindProdutoByIdNonExistingProduto() {
        Long produtoId = 1L;
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.empty());

        Optional<Produto> result = produtoService.findById(produtoId);

        assertFalse(result.isPresent());

        verify(produtoRepository, times(1)).findById(produtoId);
    }

    @Test
    public void testDeleteExistingProduto() {
        Long produtoId = 1L;
        when(produtoRepository.existsById(produtoId)).thenReturn(true);

        produtoService.delete(produtoId);

        verify(produtoRepository, times(1)).existsById(produtoId);
        verify(produtoRepository, times(1)).deleteById(produtoId);
    }

    @Test
    public void testDeleteNonExistingProduto() {
        Long produtoId = 1L;
        when(produtoRepository.existsById(produtoId)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> produtoService.delete(produtoId));

        verify(produtoRepository, times(1)).existsById(produtoId);
        verify(produtoRepository, never()).deleteById(produtoId);
    }
}

