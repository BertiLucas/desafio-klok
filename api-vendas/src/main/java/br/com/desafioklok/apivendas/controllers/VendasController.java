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


    @PutMapping("/{id}")
    public ResponseEntity<Vendas> updateVendas(@PathVariable Long id, @RequestBody Vendas vendas) {
        Optional<Vendas> optionalVenda = vendasService.findById(id);
        if (optionalVenda.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Vendas venda = optionalVenda.get();

        if (vendas.getCliente() != null) {
            venda.setCliente(vendas.getCliente());
        }
        if (vendas.getProdutos() != null) {
            venda.setProdutos(vendas.getProdutos());
        }
        if (vendas.getCobranca() != null) {
            venda.setCobranca(vendas.getCobranca());
        }

        Vendas vendaAtualizada = vendasService.updateVendas(venda);
        return ResponseEntity.ok(vendaAtualizada);
    }


}
