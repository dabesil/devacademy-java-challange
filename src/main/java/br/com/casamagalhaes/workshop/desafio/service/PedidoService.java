package br.com.casamagalhaes.workshop.desafio.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import br.com.casamagalhaes.workshop.desafio.model.Pedido;
import br.com.casamagalhaes.workshop.desafio.model.enums.StatusPedido;
import br.com.casamagalhaes.workshop.desafio.repository.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    public Page<Pedido> buscarTodos() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Pedido> pagina = repository.findAll(pageable);
        if(pagina.getTotalElements() > 0)
            return pagina;
        else{
            throw new EntityNotFoundException("Não existem Produtos cadastrados");
        }    
    }

    public Pedido buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Pedido> buscarPorNome(String nome) {
        List<Pedido> lista = repository.findByNomeClienteContainsIgnoreCase(nome);
        if(lista.size() > 0)
            return lista;
        else{
            throw new EntityNotFoundException("Não existem Produtos cadastrados com o nome informado");
        }   
    }

    public Pedido salvarPedido(Pedido pedido) {
        if(pedido.getId() != null && repository.existsById(pedido.getId()))
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"Um pedido com este id já existe");
        else
            return repository.saveAndFlush(pedido);
    }

    public Pedido alterarPedido(Long id, Pedido pedido) {
        Pedido alterado = verificarPedido(id, pedido);
        pedido.setStatus(alterado.getStatus());
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
                    && !alterado.getStatus().equals(StatusPedido.PRONTO)) {
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
            throw new EntityNotFoundException("O produto com o id " + id + " não existe.");
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
