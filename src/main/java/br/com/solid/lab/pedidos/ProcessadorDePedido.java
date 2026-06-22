package br.com.solid.lab.pedidos;
import java.util.List;

public class ProcessadorDePedido {
    private final CalculadorDeTotal calculadorDeTotal = new CalculadorDeTotal();
    private final GeradorDeRecibo geradorDeRecibo = new GeradorDeRecibo();
    private final RepositorioDePedido repositorioDePedidos = new RepositorioDePedido();
    private final ServicoDeEmail servicoDeEmail = new ServicoDeEmail();
    

    public String processar(Pedido pedido) {
       double total = calculadorDeTotal.calcular(pedido);

       String recibo = geradorDeRecibo.gerarRecibo(pedido, total);

       repositorioDePedidos.salvar(pedido, total);
       servicoDeEmail.enviarConfirmacao(pedido, total);

       return recibo;
    }

    public List<String> pedidosSalvos() {
    return repositorioDePedidos.pedidosSalvos();
}

public List<String> emailsEnviados() {
    return servicoDeEmail.emailsEnviados();
}

}
