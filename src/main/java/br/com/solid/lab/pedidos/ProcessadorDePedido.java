package br.com.solid.lab.pedidos;

import java.util.List;

public class ProcessadorDePedido {
    private final CalculadorDeTotal calculadorDeTotal;
    private final GeradorRecibo geradorDeRecibo;
    private final RepositorioPedidos repositorioDePedidos;
    private final NotificacaoEmail notificacaoDeEmail;

    public ProcessadorDePedido(
            CalculadorDeTotal calculadorDeTotal,
            GeradorRecibo geradorDeRecibo,
            RepositorioPedidos repositorioDePedidos,
            NotificacaoEmail notificacaoDeEmail
    ) {
        this.calculadorDeTotal = calculadorDeTotal;
        this.geradorDeRecibo = geradorDeRecibo;
        this.repositorioDePedidos = repositorioDePedidos;
        this.notificacaoDeEmail = notificacaoDeEmail;
    }

    public ProcessadorDePedido() {
        this(
                new CalculadorDeTotal(),
                new GeradorDeRecibo(),
                new RepositorioDePedido(),
                new ServicoDeEmail()
        );
    }

    public String processar(Pedido pedido) {
        double total = calculadorDeTotal.calcular(pedido);

        String recibo = geradorDeRecibo.gerarRecibo(pedido, total);

        repositorioDePedidos.salvar(pedido, total);
        notificacaoDeEmail.enviarConfirmacao(pedido, total);

        return recibo;
    }

    public List<String> pedidosSalvos() {
        return ((RepositorioDePedido) repositorioDePedidos).pedidosSalvos();
    }

    public List<String> emailsEnviados() {
        return ((ServicoDeEmail) notificacaoDeEmail).emailsEnviados();
    }
}
