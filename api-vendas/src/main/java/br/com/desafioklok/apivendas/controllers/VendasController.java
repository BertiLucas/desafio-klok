package br.com.desafioklok.apivendas.controllers;

import br.com.desafioklok.apivendas.dtos.VendasDTO;
import br.com.desafioklok.apivendas.models.Vendas;
import br.com.desafioklok.apivendas.services.VendasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vendas")
public class VendasController {

    @Autowired
    private VendasService vendasService;

    @PostMapping
    public ResponseEntity<Vendas> createVenda(@RequestBody VendasDTO vendasDTO) {
        Vendas createdVenda = vendasService.create(vendasDTO);
        return new ResponseEntity<>(createdVenda, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Vendas>> getAllVendas() {
        List<Vendas> vendas = vendasService.findAll();
        return new ResponseEntity<>(vendas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendas> getVendaById(@PathVariable Long id) {
        Optional<Vendas> venda = vendasService.findById(id);
        if (venda.isPresent()) {
            return new ResponseEntity<>(venda.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenda(@PathVariable Long id) {
        vendasService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
