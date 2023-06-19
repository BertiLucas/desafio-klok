package br.com.desafioklok.apivendas.controller;

import br.com.desafioklok.apivendas.controllers.CobrancaController;
import br.com.desafioklok.apivendas.models.Cobranca;
import br.com.desafioklok.apivendas.services.CobrancaService;
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

public class CobrancaControllerTest {

    @Mock
    private CobrancaService cobrancaService;

    @InjectMocks
    private CobrancaController cobrancaController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCobrancas() {
        List<Cobranca> cobrancas = new ArrayList<>();
        when(cobrancaService.findAll()).thenReturn(cobrancas);

        ResponseEntity<List<Cobranca>> response = cobrancaController.getAllCobrancas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cobrancas, response.getBody());

        verify(cobrancaService, times(1)).findAll();
    }

    @Test
    public void testGetCobrancaByIdExistingCobranca() {
        Long cobrancaId = 1L;
        Cobranca cobranca = new Cobranca();
        when(cobrancaService.findById(cobrancaId)).thenReturn(Optional.of(cobranca));

        ResponseEntity<Cobranca> response = cobrancaController.getCobrancaById(cobrancaId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cobranca, response.getBody());

        verify(cobrancaService, times(1)).findById(cobrancaId);
    }

    @Test
    public void testGetCobrancaByIdNonExistingCobranca() {
        Long cobrancaId = 1L;
        when(cobrancaService.findById(cobrancaId)).thenReturn(Optional.empty());

        ResponseEntity<Cobranca> response = cobrancaController.getCobrancaById(cobrancaId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(cobrancaService, times(1)).findById(cobrancaId);
    }

    @Test
    public void testDeleteExistingCobranca() {
        Long cobrancaId = 1L;
        doNothing().when(cobrancaService).delete(cobrancaId);

        ResponseEntity<Void> response = cobrancaController.deleteCobranca(cobrancaId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

        verify(cobrancaService, times(1)).delete(cobrancaId);
    }

    @Test
    public void testDeleteNonExistingCobranca() {
        Long cobrancaId = 1L;
        doThrow(IllegalArgumentException.class).when(cobrancaService).delete(cobrancaId);

        assertThrows(IllegalArgumentException.class, () -> cobrancaController.deleteCobranca(cobrancaId));

        verify(cobrancaService, times(1)).delete(cobrancaId);
    }
}
