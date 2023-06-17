package br.com.desafioklok.apivendas.controllers;

import br.com.desafioklok.apivendas.models.Cliente;
import br.com.desafioklok.apivendas.services.ClienteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ClienteControllerTest {

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