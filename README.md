# Minha jornada estudando SOLID

Este repositório é meu laboratório para estudar SOLID na prática usando Java.

A ideia não é só decorar a sigla. Quero entender o problema que cada princípio resolve, mexer no código, quebrar um pouco a cabeça e registrar o que aprendi em cada etapa.

O projeto é um checkout simples de pedidos. Conforme avanço nas aulas, vou refatorando o código e deixando os testes verdes para garantir que o comportamento continua funcionando.

## Como rodar o projeto

```bash
mvn test
```

## Sobre o projeto

O sistema simula o processamento de um pedido.

Hoje ele tem:

- `Pedido`, `Cliente` e `ItemPedido`;
- `ProcessadorDePedido`, que coordena o fluxo;
- `CalculadorDeTotal`, que calcula o valor final;
- regras de desconto no pacote `descontos`;
- recibo, repositório e serviço de e-mail no pacote `pedidos`.

## Meu progresso

| Aula | Princípio | Status |
| --- | --- | --- |
| 1 | S - Single Responsibility Principle | Concluída |
| 2 | O - Open/Closed Principle | Concluída |
| 3 | L - Liskov Substitution Principle | Concluída |
| 4 | I - Interface Segregation Principle | Pendente |
| 5 | D - Dependency Inversion Principle | Pendente |

## Aula 1: S - Single Responsibility Principle

O primeiro princípio que estudei foi o da responsabilidade única.

O que eu entendi: uma classe não deveria ter vários motivos diferentes para mudar. Se uma mesma classe calcula valor, gera recibo, salva pedido e envia e-mail, ela está misturando responsabilidades demais.

No começo, o `ProcessadorDePedido` fazia tudo isso junto. Ele até funcionava, mas o código ficava mais difícil de ler e qualquer mudança pequena poderia afetar várias partes.

O que eu fiz:

- tirei o cálculo de dentro do processador e criei o `CalculadorDeTotal`;
- tirei a montagem do recibo e criei o `GeradorDeRecibo`;
- tirei o salvamento do pedido e criei o `RepositorioDePedido`;
- tirei o envio de e-mail e criei o `ServicoDeEmail`;
- deixei o `ProcessadorDePedido` apenas coordenando o fluxo.

Meu aprendizado aqui foi que coordenar outras classes também é uma responsabilidade. O problema era o processador fazer tudo diretamente.

## Aula 2: O - Open/Closed Principle

Depois estudei o princípio aberto/fechado.

O que eu entendi: o código deve permitir adicionar comportamentos novos sem ficar alterando toda hora uma classe central que já existe.

O problema estava no `CalculadorDeTotal`. Cada nova regra de desconto viraria mais um `if`.

Antes, a lógica era parecida com isso:

```java
if ("BOASVINDAS10".equals(pedido.codigoCupom())) {
    total = total * 0.90;
}

if (subtotal >= 200) {
    total = total - 15;
}
```

Isso funciona no começo, mas conforme aparecem novas regras, a classe começa a crescer e fica mais arriscado mexer nela.

O que eu fiz:

- criei a interface `RegraDeDesconto`;
- criei `DescontoCupomBoasVindas`;
- criei `DescontoPedidoGrande`;
- alterei o `CalculadorDeTotal` para aplicar uma lista de regras.

Meu aprendizado aqui foi que o problema não é usar `if`. O problema é concentrar todas as variações no mesmo lugar. Agora, se eu quiser criar um desconto novo, posso criar uma nova classe de regra.

## Aula 3: L - Liskov Substitution Principle

Na terceira aula estudei o princípio da substituição de Liskov.

O que eu entendi: se uma classe implementa uma interface, ela precisa conseguir ocupar o lugar dessa interface sem quebrar o sistema. Não basta "compilar"; ela precisa respeitar a promessa que a abstração representa.

No meu projeto, o exemplo apareceu em `RegraDeDesconto`.

Toda regra de desconto promete:

- receber um `Pedido`;
- receber o `totalAtual`;
- devolver um total válido;
- não quebrar o fluxo do cálculo;
- não deixar o pedido em um estado estranho.

Para praticar, criei uma regra perigosa chamada `DescontoMaiorQueTotal`. Ela simula uma regra que aplica um desconto maior do que o valor do pedido.

Isso poderia gerar um total negativo, o que não faz sentido no domínio. Pedido com total negativo seria uma substituição perigosa, porque o `CalculadorDeTotal` espera trabalhar com totais válidos.

O que eu fiz:

- criei `DescontoMaiorQueTotal`;
- alterei `CalculadorDeTotal` para receber uma lista de `RegraDeDesconto`;
- mantive um construtor padrão com as regras reais;
- protegi o cálculo para o total nunca ficar abaixo de zero;
- criei `CalculadorDeTotalTest` para garantir esse comportamento.

Meu aprendizado aqui foi que uma implementação precisa respeitar o contrato esperado por quem usa a interface. Se cada implementação faz qualquer coisa, a abstração perde confiança.

## Coisas que quero lembrar

- Teste é minha rede de segurança durante refatoração.
- Antes de melhorar design, preciso garantir que o comportamento continua igual.
- SOLID fica mais fácil quando eu olho para motivos de mudança, acoplamento e responsabilidades.
- Não preciso aplicar tudo de uma vez. Uma letra por vez já muda bastante a forma de pensar.
