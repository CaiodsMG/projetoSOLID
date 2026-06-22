package br.com.solid.lab.pedidos;

import java.time.LocalDateTime;

public class GeradorDeRecibo implements GeradorRecibo {

    @Override
    public String gerarRecibo(Pedido pedido, double total){

        String recibo = "Recibo\n" +
                "Cliente: " + pedido.cliente().nome() + "\n" +
                "Total: " + String.format(java.util.Locale.US, "%.2f", total) + "\n" +
                "Data: " + LocalDateTime.now();

        return recibo;
    }
}

