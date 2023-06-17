package br.com.desafioklok.apivendas.services;

import br.com.desafioklok.apivendas.models.Cliente;
import br.com.desafioklok.apivendas.repositories.ClienteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Before
    public void setUp() {
    }

    @Test
    public void testCreateCliente() {
        // Criação do objeto Cliente de teste
        Cliente cliente = new Cliente();
        cliente.setNome("João");
        cliente.setCpf("123456789");
        cliente.setEmail("joao@example.com");
        cliente.setEndereco("Rua A");

        // Configuração do comportamento do repositório mockado
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        // Chamada do método do serviço a ser testado
        Cliente resultado = clienteService.create(cliente);

        // Verificação dos resultados
        assertEquals(cliente, resultado);
        verify(clienteRepository, times(1)).save(cliente); // Verifica se o método "save" do repositório foi chamado uma vez com o cliente de teste
    }

    @Test
    public void testCreateClienteWithMissingInformation() {
        // Criação do objeto Cliente de teste com dados incompletos
        Cliente cliente = new Cliente();

        // Verificação se o serviço lança uma exceção ao criar um cliente com informações ausentes
        assertThrows(IllegalArgumentException.class, () -> clienteService.create(cliente));
        verify(clienteRepository, never()).save(cliente); // Verifica se o método "save" do repositório nunca foi chamado com o cliente de teste incompleto
    }

    @Test
    public void testFindAllClientes() {
        // Criação de dois objetos Cliente de teste
        Cliente cliente1 = new Cliente();
        Cliente cliente2 = new Cliente();

        // Criação da lista de clientes esperada
        List<Cliente> expectedClientes = Arrays.asList(cliente1, cliente2);

        // Configuração do comportamento do repositório mockado
        when(clienteRepository.findAll()).thenReturn(expectedClientes);

        // Chamada do método do serviço a ser testado
        List<Cliente> resultado = clienteService.findAll();

        // Verificação dos resultados
        assertEquals(expectedClientes, resultado);
        verify(clienteRepository, times(1)).findAll(); // Verifica se o método "findAll" do repositório foi chamado uma vez
    }

    @Test
    public void testFindClienteById() {
        // ID do cliente de teste
        Long clienteId = 1L;

        // Criação do objeto Cliente de teste
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);

        // Configuração do comportamento do repositório mockado
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

        // Chamada do método do serviço a ser testado
        Optional<Cliente> resultado = clienteService.findById(clienteId);

        // Verificação dos resultados
        assertEquals(cliente, resultado.orElse(null));
        verify(clienteRepository, times(1)).findById(clienteId); // Verifica se o método "findById" do repositório foi chamado uma vez com o ID de teste
    }

    @Test
    public void testDeleteExistingCliente() {
        // ID do cliente de teste
        Long clienteId = 1L;

        // Configuração do comportamento do repositório mockado
        when(clienteRepository.existsById(clienteId)).thenReturn(true);

        // Chamada do método do serviço a ser testado
        clienteService.delete(clienteId);

        // Verificação dos resultados
        verify(clienteRepository, times(1)).existsById(clienteId); // Verifica se o método "existsById" do repositório foi chamado uma vez com o ID de teste
        verify(clienteRepository, times(1)).deleteById(clienteId); // Verifica se o método "deleteById" do repositório foi chamado uma vez com o ID de teste
    }

    @Test
    public void testDeleteNonExistingCliente() {
        // ID do cliente de teste
        Long clienteId = 1L;

        // Configuração do comportamento do repositório mockado
        when(clienteRepository.existsById(clienteId)).thenReturn(false);

        // Verificação se o serviço lança uma exceção ao tentar excluir um cliente que não existe
        assertThrows(IllegalArgumentException.class, () -> clienteService.delete(clienteId));
        verify(clienteRepository, times(1)).existsById(clienteId); // Verifica se o método "existsById" do repositório foi chamado uma vez com o ID de teste
        verify(clienteRepository, never()).deleteById(clienteId); // Verifica se o método "deleteById" do repositório nunca foi chamado com o ID de teste
    }
}

