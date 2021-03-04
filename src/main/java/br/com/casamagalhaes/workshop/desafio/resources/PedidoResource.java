package br.com.casamagalhaes.workshop.desafio.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.casamagalhaes.workshop.desafio.dto.DTO;
import br.com.casamagalhaes.workshop.desafio.dto.PedidoDTO;
import br.com.casamagalhaes.workshop.desafio.model.Pedido;
import br.com.casamagalhaes.workshop.desafio.service.PedidoService;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pedido")
@RestController
@RequestMapping(path = "/api/v1/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService service;

    @GetMapping({"/paginada"})
    public Page<Pedido> buscarTodos(@RequestParam Integer numPag, @RequestParam Integer tamPag){
        return service.buscarTodos(numPag, tamPag);
    }

    @GetMapping("/nome")
    public List<Pedido> buscarPorNome(@RequestParam String nome){
        return service.buscarPorNome(nome);
    }

    @GetMapping("/id")
    public Pedido buscarPorId(@RequestParam Long id){
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Pedido salvarPedido(@RequestBody PedidoDTO pedidoDTO){
        return service.salvarPedido(DTO.fromDTO(pedidoDTO));
    }

    @PostMapping("{id}/status")
    @ResponseStatus(code = HttpStatus.OK)
    public PedidoDTO alterarStatus(@PathVariable Long id, @RequestBody PedidoDTO pedidoDTO){
        pedidoDTO.setId(id);
        return new PedidoDTO(service.alterarStatus(id, DTO.fromDTO2(pedidoDTO)));
    }
    
    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public PedidoDTO alterarPedido(@PathVariable Long id, @RequestBody PedidoDTO pedidoDTO){
        return new PedidoDTO(service.alterarPedido(id, DTO.fromDTO(pedidoDTO)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removerPedido(@PathVariable Long id){
        service.removerPedido(id);
    }
    
}