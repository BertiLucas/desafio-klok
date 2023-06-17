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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProdutoServiceTest {

    @Mock
    private ProdutoRepository repository;

    @InjectMocks
    private ProdutoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduto() {
        // Criação de um produto de teste
        Produto produto = new Produto();
        produto.setNome("Produto 1");
        produto.setDescricao("Descrição do Produto 1");
        produto.setPreco(10.0);

        // Configuração do comportamento do repositório mockado
        when(repository.save(produto)).thenReturn(produto);

        // Chamada do método do serviço a ser testado
        Produto createdProduto = service.create(produto);

        // Verificação dos resultados
        assertEquals(produto, createdProduto);
        verify(repository, times(1)).save(produto); // Verifica se o método "save" do repositório foi chamado uma vez com o produto de teste
    }

    @Test
    void testFindAll() {
        // Criação de uma lista de produtos de teste
        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto(1L, "Produto 1", "Descrição do Produto 1", 10.0));
        produtos.add(new Produto(2L, "Produto 2", "Descrição do Produto 2", 20.0));

        // Configuração do comportamento do repositório mockado
        when(repository.findAll()).thenReturn(produtos);

        // Chamada do método do serviço a ser testado
        List<Produto> foundProdutos = service.findAll();

        // Verificação dos resultados
        assertEquals(produtos, foundProdutos);
        verify(repository, times(1)).findAll(); // Verifica se o método "findAll" do repositório foi chamado uma vez
    }

    @Test
    void testFindById() {
        // ID do produto de teste
        Long produtoId = 1L;

        // Criação de um produto de teste
        Produto produto = new Produto(produtoId, "Produto 1", "Descrição do Produto 1", 10.0);

        // Configuração do comportamento do repositório mockado
        when(repository.findById(produtoId)).thenReturn(Optional.of(produto));

        // Chamada do método do serviço a ser testado
        Optional<Produto> foundProduto = service.findById(produtoId);

        // Verificação dos resultados
        assertEquals(Optional.of(produto), foundProduto);
        verify(repository, times(1)).findById(produtoId); // Verifica se o método "findById" do repositório foi chamado uma vez com o ID de teste
    }

    @Test
    void testDeleteProduto() {
        // ID do produto de teste
        Long produtoId = 1L;

        // Configuração do comportamento do repositório mockado
        doNothing().when(repository).deleteById(produtoId);
        when(repository.existsById(produtoId)).thenReturn(true);

        // Chamada do método do serviço a ser testado
        service.delete(produtoId);

        // Verificação dos resultados
        verify(repository, times(1)).deleteById(produtoId); // Verifica se o método "deleteById" do repositório foi chamado uma vez com o ID de teste
    }
}


