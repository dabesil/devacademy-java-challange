package br.com.casamagalhaes.workshop.desafio.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.casamagalhaes.workshop.desafio.model.Pedido;
import br.com.casamagalhaes.workshop.desafio.model.enums.StatusPedido;
import br.com.casamagalhaes.workshop.desafio.repository.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    public Page<Pedido> buscarTodos(Integer numPag, Integer tamPag) {
        Pageable pageable = PageRequest.of(numPag, tamPag);
        return repository.findAll(pageable);
    }

    public Pedido buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Pedido> buscarPorNome(String nome) {
        return repository.findByNomeClienteContainsIgnoreCase(nome);
    }

    public Pedido salvarPedido(Pedido pedido) {
        return repository.saveAndFlush(pedido);
    }

    public Pedido alterarPedido(Long id, Pedido pedido) {
        Pedido alterado = verificarPedido(id, pedido);
        if (alterado.getStatus().equals(pedido.getStatus())) {
            return repository.saveAndFlush(pedido);
        } else {
            throw new UnsupportedOperationException("O Status não pode ser alterado por este método.");
        }
    }

    public Pedido alterarStatus(Long id, Pedido pedido) {
        Pedido alterado = verificarPedido(id, pedido);
        try {
            if (pedido.getStatus().equals(StatusPedido.CANCELADO) && (alterado.getStatus().equals(StatusPedido.EM_ROTA)
                    || alterado.getStatus().equals(StatusPedido.ENTREGUE)
                    || alterado.getStatus().equals(StatusPedido.CANCELADO))) {
                throw new UnsupportedOperationException("O Status não pode ser alterado.");
            } else if (pedido.getStatus().equals(StatusPedido.EM_ROTA)
                    && !pedido.getStatus().equals(StatusPedido.PRONTO)) {
                throw new UnsupportedOperationException("O Status EM_ROTA só pode ser alterado se o atual for PRONTO.");
            } else if (pedido.getStatus().equals(StatusPedido.ENTREGUE)
                    && !alterado.getStatus().equals(StatusPedido.EM_ROTA)) {
                throw new UnsupportedOperationException("O Status ENTREGUE só pode ser alterado se o atual for EM_ROTA.");
            } else {
                return salvarAlteracao(pedido, alterado);
            }
        } catch (Exception e) {
            throw new UnsupportedOperationException("O Status não pode ser alterado.");

        }
    }

    public void removerPedido(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException("O produto com o id " + id + "não existe.");
        }
    }

    private Pedido verificarPedido(Long id, Pedido pedido) {
        if (repository.existsById(id)) {
            if (id.equals(pedido.getId())) {
                return buscarPorId(id);
            } else
                throw new UnsupportedOperationException("Id informado diferente do Produto.");
        } else
            throw new EntityNotFoundException("Produto id: " + pedido.getId());
    }

    private Pedido salvarAlteracao(Pedido pedido, Pedido alterado) {
        alterado.setStatus(pedido.getStatus());
        return repository.saveAndFlush(alterado);
    }
}
