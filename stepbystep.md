# Dev Academy Challange - Java

## Incializando a Aplicação

- Em um pasta faça a clonagem do repositorio com o comando:
```bash
git clone https://github.com/dabesil/devacademy-java-challange
```
- Na pasta raiz do projeto execute o comando: 
```bash
gradle wrapper
```
- Abra na IDE de preferencia.

- Para inicializar a aplicação execute o comando:
```bash
.\gradlew bootRun
```

- Caso não queira que a aplicação inicie com a database povoada, desabilite a classe "CarregarDados.java" comentando as anotações "@Configuration" e "@Bean"

- Inicie a aplicação executando a classe principal "DesafioAplicattion.java".

- A aplicação será iniciada em "http://localhost:8080" que é a porta padrão, altere o campo "server.port" no documento "application.properties" modificar a porta da aplicação.

## Requisiçoes

#### A documentação pode ser acessada na path "/swagger-ui.html"

#### Todas as requisições são feitas no endpoint "api/v1/pedidos/"

### GET

#### Buscar todos os pedidos ("/paginada?numPag=X&tamPag=Y")

- Retorna uma lista paginada com todos os pedidos.

#### Buscar por nome ("/nome?nome=X")

- Retorna uma lista de todos os pedidos com o nome.

#### Buscar por id ("/id?id=X")

- Retorna o pedido com o id correspondente.

### POST

#### Salvar Pedido ("/", requer body)
Exemplo:
```json
{
"nomeCliente":"JOSE FRANCISCO",
"endereco":"Rua A, 500",
"telefone":"8532795578",
"taxa":2.50,
"itens": 
    [{
        "descricao": "Refri",
        "precoUnitario": 5.5,
        "quantidade": 1
   },
   {
        "descricao": "Coxinha",
        "precoUnitario": 3.00,
        "quantidade": 1
   },
   {
        "descricao": "Batatinha",
        "precoUnitario": 5.00,
        "quantidade": 1
   }]
}
```
- Não é necessário informar o id do pedido.
- O pedido é salvo por padrão com o status PENDENTE.
- O valor total dos produtos e o valor total do pedido são calculados automaticamente.

#### Alterar Status ("/{id}/status", requer body)
Exemplo:
```json
{
"status":"ENTREGUE"
}
```
- Os status que um pedido pode ter são PENDENTE, PREPARANDO, PRONTO, EM_ROTA, ENTREGUE, CANCELADO.

- Não é possivel:
    * alterar o status para CANCELADO caso o status atual seja EM_ROTA, ENTREGUE ou CANCELADO 
    * alterar o status para EM_ROTA caso o status atual não seja PRONTO 
    * alterar o status para ENTREGUE caso o status atual não seja EM_ROTA

### PUT

#### Alterar Pedido ("/{id}", requer body)
Exemplo:
```json
{
"id" : 23,
"nomeCliente":"JOSE FRANCISCO",
"endereco":"Rua A, 500",
"telefone":"8532795578",
"taxa":2.50,
"itens": 
    [{
        "descricao": "Refri",
        "precoUnitario": 5.5,
        "quantidade": 1
   },
   {
        "descricao": "Coxinha",
        "precoUnitario": 3.00,
        "quantidade": 1
   },
   {
        "descricao": "Batatinha",
        "precoUnitario": 5.00,
        "quantidade": 1
   }]
}
```
- O status só pode ser alterado pelo médoto Alterar Status.
- O id informado precisa ser o mesmo do body.

### DELETE 

#### Deletar Pedido ("/{id}")

- O id informado precisa ser válido.


## Testes

##### A classe "PedidoResourceTeste.java" possui os métodos para testar a aplicação.