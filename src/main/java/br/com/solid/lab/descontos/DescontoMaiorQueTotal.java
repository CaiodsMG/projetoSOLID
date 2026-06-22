package br.com.solid.lab.descontos;

import br.com.solid.lab.pedidos.Pedido;

public class DescontoMaiorQueTotal implements RegraDeDesconto{
    @Override
    public double aplicar(Pedido pedido, double totalAtual) {
        return totalAtual - 999;
    }
}
