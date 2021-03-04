package br.com.casamagalhaes.workshop.desafio;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.casamagalhaes.workshop.desafio.model.Pedido;
import br.com.casamagalhaes.workshop.desafio.model.Produto;
import br.com.casamagalhaes.workshop.desafio.model.enums.StatusPedido;
import br.com.casamagalhaes.workshop.desafio.repository.PedidoRepository;

@Configuration
public class CarregarDados {
    
    private static final Logger log = 
        LoggerFactory.getLogger(CarregarDados.class);

    @Bean
    CommandLineRunner initDB(PedidoRepository repository) {
        
        return args -> {
            Pedido p;
            for (int i = 0; i < 5; i++) {
                p = new Pedido( null ,"nomeCliente" + (i + 1), "(88)98888-8888", "Rua" + i * Math.random(),3.00 + i, Arrays.asList(
                    new Produto("Kg Carne", 25.00, 2),
                    new Produto("Kg Filé de Frango", 12.50, 4)), StatusPedido.PENDENTE);
                log.info("Cadastrando produtos... " + repository.save(p));
            }
        };
    }
    
}
