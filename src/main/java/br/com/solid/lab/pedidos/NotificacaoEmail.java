package br.com.solid.lab.pedidos;

public interface NotificacaoEmail {

    void enviarConfirmacao(Pedido pedido, double total);
}
