package br.com.casamagalhaes.workshop.desafio.dto;

import java.util.List;

import br.com.casamagalhaes.workshop.desafio.model.Pedido;
import br.com.casamagalhaes.workshop.desafio.model.Produto;
import br.com.casamagalhaes.workshop.desafio.model.enums.StatusPedido;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PedidoDTO {
    
    private Long id;
    private String nomeCliente;
    private String endereco;
    private String telefone;
    private Double taxa = 0.0;
    private List<Produto> itens;
    private Double valorTotalProdutos = 0.0;
    private Double valorTotal = 0.0;
    private StatusPedido status = StatusPedido.PENDENTE;

    public PedidoDTO() {
    }

    public PedidoDTO(Pedido pedido) {
        id = pedido.getId();
        nomeCliente = pedido.getNomeCliente();
        telefone = pedido.getTelefone();
        endereco = pedido.getEndereco();
        valorTotalProdutos = pedido.getValorTotalProdutos();
        taxa = pedido.getTaxa();
        valorTotal = pedido.getValorTotal();
        itens = pedido.getItens();
        status = pedido.getStatus();
    }

}
