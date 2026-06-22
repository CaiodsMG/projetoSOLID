package br.com.solid.lab.pedidos;

import br.com.solid.lab.descontos.DescontoCupomBoasVindas;
import br.com.solid.lab.descontos.DescontoPedidoGrande;
import br.com.solid.lab.descontos.RegraDeDesconto;

import java.util.List;

public class CalculadorDeTotal {

    private final List<RegraDeDesconto> regraDeDescontos;

    public CalculadorDeTotal(List<RegraDeDesconto> regraDeDescontos) {
        this.regraDeDescontos = regraDeDescontos;
    }

    public CalculadorDeTotal() {
        this(List.of(
                new DescontoCupomBoasVindas(),
                new DescontoPedidoGrande()
        ));
    }


    public double calcular(Pedido pedido) {
        double total = calcularSubtotal(pedido);

        for (RegraDeDesconto regra : regraDeDescontos) {
            total = regra.aplicar(pedido, total);

            if (total < 0){
                total = 0;
            }
        }



        return total;
    }

    private double calcularSubtotal(Pedido pedido) {
        double subtotal = 0;

        for (ItemPedido item : pedido.itens()) {
            subtotal += item.subtotal();
        }

        return subtotal;
    }

}


