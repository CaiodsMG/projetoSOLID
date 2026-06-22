package br.com.solid.lab.pedidos;

import java.util.ArrayList;
import java.util.List;

public class RepositorioDePedido implements RepositorioPedidos {

    private final List<String> pedidosSalvos = new ArrayList<>();

    @Override
    public void salvar(Pedido pedido, double total){
        pedidosSalvos.add(pedido.cliente().email() + "|" + String.format(java.util.Locale.US, "%.2f", total));
    }

    public List<String> pedidosSalvos(){
        return pedidosSalvos;
    }


}
