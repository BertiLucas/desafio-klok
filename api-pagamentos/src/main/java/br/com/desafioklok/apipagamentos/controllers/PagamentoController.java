package br.com.desafioklok.apipagamentos.controllers;

import br.com.desafioklok.apipagamentos.models.Pagamento;
import br.com.desafioklok.apipagamentos.services.PagamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Pagamento> create(@RequestBody Pagamento pagamento) {
        Pagamento pagamentoCreated = pagamentoService.create(pagamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoCreated);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Pagamento> findAll() {
        return pagamentoService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Pagamento> findById(@PathVariable Long id) {
        return pagamentoService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pagamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
