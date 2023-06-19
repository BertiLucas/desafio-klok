package br.com.desafioklok.apivendas.controller;

import br.com.desafioklok.apivendas.controllers.ClienteController;
import br.com.desafioklok.apivendas.models.Cliente;
import br.com.desafioklok.apivendas.services.ClienteService;
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

public class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCliente() {
        Cliente cliente = new Cliente(1L, "João", "123456789", "joao@example.com", "Rua A");
        when(clienteService.create(cliente)).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.createCliente(cliente);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cliente, response.getBody());

        verify(clienteService, times(1)).create(cliente);
    }

    @Test
    public void testGetAllClientes() {
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente(1L, "João", "123456789", "joao@example.com", "Rua A"));
        clientes.add(new Cliente(2L, "Maria", "987654321", "maria@example.com", "Rua B"));
        when(clienteService.findAll()).thenReturn(clientes);

        ResponseEntity<List<Cliente>> response = clienteController.getAllClientes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clientes, response.getBody());

        verify(clienteService, times(1)).findAll();
    }

    @Test
    public void testGetClienteByIdExistingCliente() {
        Long clienteId = 1L;
        Cliente cliente = new Cliente(clienteId, "João", "123456789", "joao@example.com", "Rua A");
        when(clienteService.findById(clienteId)).thenReturn(Optional.of(cliente));

        ResponseEntity<Cliente> response = clienteController.getClienteById(clienteId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente, response.getBody());

        verify(clienteService, times(1)).findById(clienteId);
    }

    @Test
    public void testGetClienteByIdNonExistingCliente() {
        Long clienteId = 1L;
        when(clienteService.findById(clienteId)).thenReturn(Optional.empty());

        ResponseEntity<Cliente> response = clienteController.getClienteById(clienteId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(clienteService, times(1)).findById(clienteId);
    }

    @Test
    public void testDeleteCliente() {
        Long clienteId = 1L;

        ResponseEntity<Void> response = clienteController.deleteCliente(clienteId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(clienteService, times(1)).delete(clienteId);
    }
    @Test
    public void testUpdateCliente() {
        // Dados de entrada
        Long id = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(id);

        // Cliente atualizado
        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setId(id);
        clienteAtualizado.setNome("Novo Nome");
        clienteAtualizado.setEmail("novoemail@example.com");

        // Mock do serviço
        when(clienteService.update(cliente)).thenReturn(clienteAtualizado);

        // Chamada do método no controller
        ResponseEntity<Cliente> response = clienteController.updateCliente(id, cliente);

        // Verificações
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clienteAtualizado, response.getBody());
        verify(clienteService, times(1)).update(cliente);
    }

    @Test
    public void testUpdateClienteNotFound() {
        // Dados de entrada
        Long id = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(id);

        // Mock do serviço lançando exceção
        when(clienteService.update(cliente)).thenThrow(new IllegalArgumentException());

        // Chamada do método no controller
        ResponseEntity<Cliente> response = clienteController.updateCliente(id, cliente);

        // Verificações
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(clienteService, times(1)).update(cliente);
    }
}

