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
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
            .get("/all");          
    }

    @Test
    public void testarRespostaGETNome(){
        given()
            .spec(requisicao)
        .expect()
            .statusCode(HttpStatus.SC_OK)
        .when()
            .get("?nome=client");          
    }

    @Test
    public void testarRespostaGETId(){
        given()
            .spec(requisicao)
        .expect()
            .statusCode(HttpStatus.SC_OK)
        .when()
            .get("/1");          
    }

    @Test
    public void testarRespostaPOST() throws JsonProcessingException{
        PedidoDTO pedidoDTO = given()
            .spec(requisicao)
            .body(objectMapper.writeValueAsString(pedidoTestePOST()))
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
            .as(PedidoDTO.class); 
        assertNotNull(pedidoDTO, "O pedido está nulo");       
    }   
    
    @Test
    public void testarRespostaPOSTStatus() throws JsonProcessingException{
        PedidoDTO pedidoDTO = given()
            .spec(requisicao)
            .body(objectMapper.writeValueAsString(pedidoTesteStatus()))
        .expect()
            .statusCode(HttpStatus.SC_OK)    
        .when()
            .post("/1/status")
        .then()
            .extract()
            .as(PedidoDTO.class);
        
        assertNotNull(pedidoDTO, "O pedido está nulo");    
    }    
    
    @Test
    public void testarRespostaPUT() throws JsonProcessingException{
        PedidoDTO pedidoDTO = given()
            .spec(requisicao)
            .body(objectMapper.writeValueAsString(pedidoTestePUT()))
        .expect()
            .statusCode(HttpStatus.SC_OK)
        .when()
            .put("/1")
        .then()
            .extract()
            .as(PedidoDTO.class);
        
        assertNotNull(pedidoDTO, "O pedido está nulo");       
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

    @Test
    public void testarErroGETPaginada(){
        given()
            .spec(requisicao)
        .expect()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
        .when()
            .get();
    } 

    @Test
    public void testarErroGETNome(){
        given()
            .spec(requisicao)
        .expect()
            .statusCode(HttpStatus.SC_NOT_FOUND)
        .when()
            .get("?nome=kxwyz");
    } 

    @Test
    public void testarErroGETId(){
        given()
            .spec(requisicao)
        .expect()
            .statusCode(HttpStatus.SC_NOT_FOUND)
        .when()
            .get("/99999999");  
    } 

    @Test
    public void testarErroPOST() throws JsonProcessingException{
        given()
            .spec(requisicao)
            .body(objectMapper.writeValueAsString(pedidoTestePOSTErro()))
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY); 
    } 

    @Test
    public void testarErroPOST2() throws JsonProcessingException{
        given()
            .spec(requisicao)
            .body(objectMapper.writeValueAsString(pedidoTestePUT()))
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST); 
    }

    @Test
    public void testarErroPOSTStatus() throws JsonProcessingException{
        given()
            .spec(requisicao)
            .body(objectMapper.writeValueAsString(pedidoTesteStatus()))
        .expect()
            .statusCode(HttpStatus.SC_NOT_FOUND)    
        .when()
            .post("/99/status");
    }

    @Test
    public void testarErroPOSTStatus2() throws JsonProcessingException{
        given()
            .spec(requisicao)
            .body(objectMapper.writeValueAsString(pedidoTesteStatusErro()))
        .expect()
            .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)    
        .when()
            .post("/1/status");
    }

    @Test
    public void testarErroPUT() throws JsonProcessingException{
        given()
            .spec(requisicao)
            .body(objectMapper.writeValueAsString(pedidoTestePUT()))
        .expect()
            .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        .when()
            .put("/2");
    }

    @Test
    public void testarErroDELETE(){
        given()
            .spec(requisicao)
        .expect()
            .statusCode(HttpStatus.SC_NOT_FOUND)
        .when()
            .delete("/99999");
    }

    private PedidoDTO pedidoTestePOST(){
        PedidoDTO pedido = instanciarPedidoDTO();     
        return pedido;    
    }

    private PedidoDTO pedidoTestePOSTErro(){
        PedidoDTO pedido = instanciarPedidoDTO();
        pedido.setNomeCliente("M");     
        return pedido;    
    }

    private PedidoDTO pedidoTesteStatus(){
        PedidoDTO pedido = new PedidoDTO();
        pedido.setStatus(StatusPedido.PREPARANDO); 
        return pedido;    
    }

    private PedidoDTO pedidoTesteStatusErro(){
        PedidoDTO pedido = new PedidoDTO();
        pedido.setStatus(StatusPedido.ENTREGUE); 
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
