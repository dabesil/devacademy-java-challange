package br.com.casamagalhaes.workshop.desafio.dto;

import br.com.casamagalhaes.workshop.desafio.model.Pedido;

public abstract class DTO {
    
    public static Pedido fromDTO(PedidoDTO pedidoDTO){
        return new Pedido(pedidoDTO.getId(), pedidoDTO.getNomeCliente(), pedidoDTO.getTelefone(), 
        pedidoDTO.getEndereco(), pedidoDTO.getTaxa(), pedidoDTO.getItens());
    }

    public static Pedido fromDTO2(PedidoDTO pedidoDTO){
        Pedido pedido = new Pedido();
        pedido.setId(pedidoDTO.getId());
        pedido.setStatus(pedidoDTO.getStatus());
        return pedido;
    }

}
