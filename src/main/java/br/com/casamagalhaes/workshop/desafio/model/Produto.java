package br.com.casamagalhaes.workshop.desafio.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class Produto {
    
    @NotEmpty(message = "O produto deve ter descrição")
    @Size(min = 2, max = 50, message = "Informe uma descrição com no mínimo 2 caracteres")
    private String descricao;
    @Positive(message = "O produto deve ter preço")
    private Double precoUnitario;
    @Positive(message = "A quantidade de cada produto deve ser informada")
    private Integer quantidade;

    public Produto() {
    }

    public Produto(String descricao, Double precoUnitario, Integer quantidade) {
        this.descricao = descricao;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
    }

    
    
}
