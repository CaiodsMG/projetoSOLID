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
| 4 | I - Interface Segregation Principle | Concluída |
| 5 | D - Dependency Inversion Principle | Concluída |

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

## Aula 4: I - Interface Segregation Principle

Na quarta aula estudei o princípio da segregação de interfaces.

O que eu entendi: uma classe não deveria ser obrigada a depender de métodos que ela não usa. Interfaces grandes demais acabam empurrando responsabilidades erradas para classes que não precisam delas.

No meu projeto, o exemplo ficou claro olhando para as partes do pedido:

- `RepositorioDePedido` só precisa salvar pedido;
- `ServicoDeEmail` só precisa enviar confirmação;
- `GeradorDeRecibo` só precisa gerar recibo.

Seria ruim criar uma interface gigante obrigando todas essas classes a implementar tudo, porque cada uma delas tem um papel diferente.

O que eu fiz:

- criei `RepositorioPedidos`;
- criei `NotificacaoEmail`;
- criei `GeradorRecibo`;
- fiz cada classe implementar apenas a interface que combina com sua responsabilidade.

Meu aprendizado aqui foi que interface também precisa ter foco. Uma interface pequena e bem nomeada ajuda o código a dizer melhor o que cada parte realmente faz.

## Aula 5: D - Dependency Inversion Principle

Na quinta aula estudei o princípio da inversão de dependência.

O que eu entendi: uma classe de alto nível não deveria ficar presa diretamente a classes concretas. Ela deve depender de abstrações sempre que isso fizer sentido.

No meu projeto, o exemplo principal foi o `ProcessadorDePedido`.

Antes, ele mesmo criava as dependências:

```java
private final GeradorDeRecibo geradorDeRecibo = new GeradorDeRecibo();
private final RepositorioDePedido repositorioDePedidos = new RepositorioDePedido();
private final ServicoDeEmail servicoDeEmail = new ServicoDeEmail();
```

Isso funcionava, mas deixava o processador acoplado às implementações concretas. Se eu quisesse trocar o repositório, o e-mail ou o gerador de recibo, teria que mexer dentro dele.

O que eu fiz:

- criei um construtor principal recebendo as dependências;
- passei a usar as interfaces `GeradorRecibo`, `RepositorioPedidos` e `NotificacaoEmail`;
- mantive um construtor padrão para montar a versão real do sistema;
- deixei o `ProcessadorDePedido` coordenando o fluxo sem decidir todos os detalhes concretos.

Meu aprendizado aqui foi que em algum lugar o sistema ainda precisa criar objetos concretos. O ponto é não espalhar essa decisão dentro das classes principais. Receber dependências pelo construtor deixa o código mais flexível e mais fácil de testar.

## Fechamento

Com esse laboratório, consegui passar pelas cinco letras do SOLID mexendo em um projeto pequeno, mas com problemas reais de design.

## Coisas que quero lembrar

- Teste é minha rede de segurança durante refatoração.
- Antes de melhorar design, preciso garantir que o comportamento continua igual.
- SOLID fica mais fácil quando eu olho para motivos de mudança, acoplamento e responsabilidades.
- Não preciso aplicar tudo de uma vez. Uma letra por vez já muda bastante a forma de pensar.
