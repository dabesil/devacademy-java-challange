package br.com.casamagalhaes.workshop.desafio.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import br.com.casamagalhaes.workshop.desafio.model.enums.StatusPedido;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_pedido")
    private Long id;

    @NotEmpty(message = "O nome do cliente deve ser informado")
    @Size(min = 4, max = 100, message = "O nome deve ter no mínimo 4 caracteres")
    @Column(name = "nome_cliente")
    private String nomeCliente;

    @NotEmpty(message = "O telefone do cliente deve ser informado")
    @Size(min = 9, max=20, message = "Um telefone válido deve ter no mínimo 9 números")
    private String telefone;

    @NotEmpty(message = "O endereço do cliente deve ser informado")
    @Size(min = 4,max = 70, message = "Um endereço válido deve ser informado")
    private String endereco;
    
    @ElementCollection
    @NotEmpty(message = "A lista de produtos deve ser informada")
    private List<@Valid Produto> itens;

    @Column(name = "valor_total_produtos")
    @Positive
    private Double valorTotalProdutos;

    @PositiveOrZero 
    private Double taxa;

    @Column(name = "valor_total")
    @Positive
    private Double valorTotal;

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.PENDENTE;

    public Pedido(){
    }

    public Pedido(Long id, String nomeCliente, String telefone, String endereco, Double taxa,
                     List<Produto> itens, StatusPedido status) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.telefone = telefone;
        this.endereco = endereco;
        this.taxa = taxa;
        this.itens = itens;
        setValorTotalProdutos();
        this.valorTotal = this.valorTotalProdutos + this.taxa;
        this.status = status;
    }

    public void setValorTotalProdutos(){
        double total = 0.0;
        if(!(itens == null)){   
            for(int i = 0; i < itens.size(); i++){
                total += itens.get(i).getPrecoUnitario() * itens.get(i).getQuantidade();
            }
        }     
        this.valorTotalProdutos = total;
    }

}
