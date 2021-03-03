package br.com.casamagalhaes.workshop.desafio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.casamagalhaes.workshop.desafio.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    
    public List<Pedido> findByNomeClienteContainsIgnoreCase(String nome);

}
