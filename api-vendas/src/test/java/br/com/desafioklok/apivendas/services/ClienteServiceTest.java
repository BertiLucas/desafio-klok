package br.com.desafioklok.apivendas.services;

import br.com.desafioklok.apivendas.models.Cliente;
import br.com.desafioklok.apivendas.repositories.ClienteRepository;
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

public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateValidCliente() {
        Cliente cliente = new Cliente(1L, "João", "123456789", "joao@example.com", "Rua A");
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente result = clienteService.create(cliente);

        assertEquals(cliente, result);

        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    public void testCreateInvalidCliente() {
        Cliente cliente = new Cliente(1L, "", "123456789", "joao@example.com", "Rua A");

        assertThrows(IllegalArgumentException.class, () -> clienteService.create(cliente));

        verify(clienteRepository, never()).save(cliente);
    }

    @Test
    public void testFindAllClientes() {
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente(1L, "João", "123456789", "joao@example.com", "Rua A"));
        clientes.add(new Cliente(2L, "Maria", "987654321", "maria@example.com", "Rua B"));
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> result = clienteService.findAll();

        assertEquals(clientes, result);

        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    public void testFindClienteByIdExistingCliente() {
        Long clienteId = 1L;
        Cliente cliente = new Cliente(clienteId, "João", "123456789", "joao@example.com", "Rua A");
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

        Optional<Cliente> result = clienteService.findById(clienteId);

        assertTrue(result.isPresent());
        assertEquals(cliente, result.get());

        verify(clienteRepository, times(1)).findById(clienteId);
    }

    @Test
    public void testFindClienteByIdNonExistingCliente() {
        Long clienteId = 1L;
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        Optional<Cliente> result = clienteService.findById(clienteId);

        assertFalse(result.isPresent());

        verify(clienteRepository, times(1)).findById(clienteId);
    }

    @Test
    public void testDeleteExistingCliente() {
        Long clienteId = 1L;
        when(clienteRepository.existsById(clienteId)).thenReturn(true);

        clienteService.delete(clienteId);

        verify(clienteRepository, times(1)).existsById(clienteId);
        verify(clienteRepository, times(1)).deleteById(clienteId);
    }

    @Test
    public void testDeleteNonExistingCliente() {
        Long clienteId = 1L;
        when(clienteRepository.existsById(clienteId)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> clienteService.delete(clienteId));

        verify(clienteRepository, times(1)).existsById(clienteId);
        verify(clienteRepository, never()).deleteById(clienteId);
    }

    @Test
    public void testUpdateCliente() {
        // Dados de entrada
        Long id = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome("Novo Nome");
        cliente.setCpf("123456789");

        // Mock do repositório
        when(clienteRepository.existsById(id)).thenReturn(true);
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        // Chamada do método no serviço
        Cliente resultado = clienteService.update(cliente);

        // Verificações
        assertEquals(cliente, resultado);
        verify(clienteRepository, times(1)).existsById(id);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    public void testUpdateClienteIdInvalido() {
        // Dados de entrada
        Long id = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome("Novo Nome");
        cliente.setCpf("123456789");

        // Mock do repositório
        when(clienteRepository.existsById(id)).thenReturn(false);

        // Chamada do método no serviço e verificação de exceção
        assertThrows(IllegalArgumentException.class, () -> clienteService.update(cliente));

        // Verificações
        verify(clienteRepository, times(1)).existsById(id);
        verify(clienteRepository, never()).save(cliente);
    }



}
