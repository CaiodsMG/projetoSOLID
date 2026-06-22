package br.com.solid.lab.descontos;

import br.com.solid.lab.pedidos.Pedido;

public class DescontoCupomBoasVindas implements RegraDeDesconto{

    @Override
    public double aplicar(Pedido pedido, double totalAtual) {

        if ("BOASVINDAS10".equals(pedido.codigoCupom())){
            return totalAtual * 0.90;
        }

        return totalAtual;
    }
}
