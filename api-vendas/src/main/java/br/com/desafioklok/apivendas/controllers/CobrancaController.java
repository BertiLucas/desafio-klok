package br.com.desafioklok.apivendas.controllers;

import br.com.desafioklok.apivendas.models.Cobranca;
import br.com.desafioklok.apivendas.services.CobrancaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cobrancas")
public class CobrancaController {

    @Autowired
    private CobrancaService cobrancaService;

    @GetMapping
    public ResponseEntity<List<Cobranca>> getAllCobrancas() {
        List<Cobranca> cobrancas = cobrancaService.findAll();
        return new ResponseEntity<>(cobrancas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cobranca> getCobrancaById(@PathVariable Long id) {
        Optional<Cobranca> cobranca = cobrancaService.findById(id);
        if (cobranca.isPresent()) {
            return new ResponseEntity<>(cobranca.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCobranca(@PathVariable Long id) {
        cobrancaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
