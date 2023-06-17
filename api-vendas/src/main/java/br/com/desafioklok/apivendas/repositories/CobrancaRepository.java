package br.com.desafioklok.apivendas.repositories;

import br.com.desafioklok.apivendas.models.Cobranca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CobrancaRepository extends JpaRepository<Cobranca, Long> {
}
