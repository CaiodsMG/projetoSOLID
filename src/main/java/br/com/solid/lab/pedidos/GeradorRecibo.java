package br.com.solid.lab.pedidos;

public interface GeradorRecibo {

    String gerarRecibo(Pedido pedido, double total);
}
