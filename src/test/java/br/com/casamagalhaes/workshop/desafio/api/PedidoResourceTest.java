package br.com.casamagalhaes.workshop.desafio.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.casamagalhaes.workshop.desafio.dto.PedidoDTO;
import br.com.casamagalhaes.workshop.desafio.model.Produto;
import br.com.casamagalhaes.workshop.desafio.model.enums.StatusPedido;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

import java.util.Arrays;

@SpringBootTest
public class PedidoResourceTest {

    @Value("${server.port}")
    private Integer porta;

    private RequestSpecification requisicao;
    
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    private void prepararRequisicao(){
        requisicao = new RequestSpecBuilder()
            .setBasePath("/api/v1/pedidos")
            .setPort(porta)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    }

    @Test
    public void testarRespostaGETPaginada(){
        given()
            .spec(requisicao)
        .expect()
            .statusCode(HttpStatus.SC_OK)
        .when()
            .get("/paginada?numPag=1&tamPag=10");          
    }

    @Test
    public void testarRespostaGETNome(){
        given()
            .spec(requisicao)
        .expect()
            .statusCode(HttpStatus.SC_OK)
        .when()
            .get("/nome?nome=client");          
    }

    @Test
    public void testarRespostaGETId(){
        given()
            .spec(requisicao)
        .expect()
            .statusCode(HttpStatus.SC_OK)
        .when()
            .get("/id?id=1");          
    }

    @Test
    public void testarRespostaPOST() throws JsonProcessingException{
        given()
            .spec(requisicao)
            .body(objectMapper.writeValueAsString(pedidoTestePOST()))
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.SC_CREATED);    
    }   
    
    @Test
    public void testarRespostaPOSTStatus() throws JsonProcessingException{
        given()
            .spec(requisicao)
            .body(objectMapper.writeValueAsString(pedidoTesteStatus()))
        .expect()
            .statusCode(HttpStatus.SC_OK)    
        .when()
            .post("/1/status");
    }    
    
    @Test
    public void testarRespostaPUT() throws JsonProcessingException{
        given()
            .spec(requisicao)
            .body(objectMapper.writeValueAsString(pedidoTestePUT()))
        .expect()
            .statusCode(HttpStatus.SC_OK)
        .when()
            .put("/1");
    } 

    @Test
    public void testarRespostaDELETE(){
        given()
            .spec(requisicao)
        .expect()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        .when()
            .delete("/5");
    } 

    private PedidoDTO pedidoTestePOST(){
        PedidoDTO pedido = instanciarPedidoDTO();     
        return pedido;    
    }

    private PedidoDTO pedidoTesteStatus(){
        PedidoDTO pedido = new PedidoDTO();
        pedido.setStatus(StatusPedido.PREPARANDO); 
        return pedido;    
    }

    private PedidoDTO pedidoTestePUT(){
        PedidoDTO pedido = instanciarPedidoDTO();
        pedido.setId((long) 1);      
        return pedido;    
    }

    private PedidoDTO instanciarPedidoDTO(){
        PedidoDTO pedido =  new PedidoDTO();
        pedido.setNomeCliente("Vitoria dos Santos");
        pedido.setTelefone("(00)90000-0000");
        pedido.setEndereco("Avenida F, 123b");
        pedido.setTaxa(7.20);
        pedido.setItens(Arrays.asList(
            new Produto("Arroz 1Kg", 4.57, 3),
            new Produto("Açucar 1Kg", 2.75, 4),
            new Produto("Óleo de Soja 1L", 7.99, 2),
            new Produto("Coxão Mole 1Kg", 43.25, 2)));  
        return pedido;     
    }

}
