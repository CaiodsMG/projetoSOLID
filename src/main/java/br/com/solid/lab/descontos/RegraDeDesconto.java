package br.com.solid.lab.descontos;

import br.com.solid.lab.pedidos.Pedido;

public interface RegraDeDesconto {

    double aplicar(Pedido pedido, double totalAtual);

}
