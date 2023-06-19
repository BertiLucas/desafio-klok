package br.com.desafioklok.apivendas.controller;

import br.com.desafioklok.apivendas.controllers.VendasController;
import br.com.desafioklok.apivendas.dtos.VendasDTO;
import br.com.desafioklok.apivendas.models.Vendas;
import br.com.desafioklok.apivendas.services.VendasService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VendasControllerTest {

    @Mock
    private VendasService vendasService;

    @InjectMocks
    private VendasController vendasController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateVenda() {
        VendasDTO vendasDTO = new VendasDTO();
        Vendas venda = new Vendas();
        when(vendasService.create(vendasDTO)).thenReturn(venda);

        ResponseEntity<Vendas> response = vendasController.createVenda(vendasDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(venda, response.getBody());

        verify(vendasService, times(1)).create(vendasDTO);
    }

    @Test
    public void testGetAllVendas() {
        List<Vendas> vendas = new ArrayList<>();
        when(vendasService.findAll()).thenReturn(vendas);

        ResponseEntity<List<Vendas>> response = vendasController.getAllVendas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(vendas, response.getBody());

        verify(vendasService, times(1)).findAll();
    }

    @Test
    public void testGetVendaByIdExistingVenda() {
        Long vendaId = 1L;
        Vendas venda = new Vendas();
        when(vendasService.findById(vendaId)).thenReturn(Optional.of(venda));

        ResponseEntity<Vendas> response = vendasController.getVendaById(vendaId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(venda, response.getBody());

        verify(vendasService, times(1)).findById(vendaId);
    }

    @Test
    public void testGetVendaByIdNonExistingVenda() {
        Long vendaId = 1L;
        when(vendasService.findById(vendaId)).thenReturn(Optional.empty());

        ResponseEntity<Vendas> response = vendasController.getVendaById(vendaId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(vendasService, times(1)).findById(vendaId);
    }

    @Test
    public void testDeleteVenda() {
        Long vendaId = 1L;

        ResponseEntity<Void> response = vendasController.deleteVenda(vendaId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

        verify(vendasService, times(1)).delete(vendaId);
    }
}

