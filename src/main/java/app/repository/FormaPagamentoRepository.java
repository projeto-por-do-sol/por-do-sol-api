package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.FormaPagamento;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long>{

}
