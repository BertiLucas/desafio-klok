package br.com.desafioklok.apipagamentos.repositories;

import br.com.desafioklok.apipagamentos.models.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}