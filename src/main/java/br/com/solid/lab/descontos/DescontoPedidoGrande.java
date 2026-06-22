package br.com.solid.lab.descontos;

import br.com.solid.lab.pedidos.ItemPedido;
import br.com.solid.lab.pedidos.Pedido;

public class DescontoPedidoGrande implements RegraDeDesconto{

    @Override
    public double aplicar(Pedido pedido, double totalAtual) {

        double subtotal = 0;

        for (ItemPedido item : pedido.itens()) {
            subtotal += item.subtotal();
        }

        if (subtotal >= 200) {
            return totalAtual - 15;
        }

        return totalAtual;
    }
}
