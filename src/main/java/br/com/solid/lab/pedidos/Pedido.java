package br.com.solid.lab.pedidos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {
    private final Cliente cliente;
    private final List<ItemPedido> itens = new ArrayList<>();
    private String codigoCupom;

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente cliente() {
        return cliente;
    }

    public List<ItemPedido> itens() {
        return Collections.unmodifiableList(itens);
    }

    public String codigoCupom() {
        return codigoCupom;
    }

    public void adicionarItem(String nomeProduto, int quantidade, double precoUnitario) {
        itens.add(new ItemPedido(nomeProduto, quantidade, precoUnitario));
    }

    public void aplicarCupom(String codigoCupom) {
        this.codigoCupom = codigoCupom;
    }
}
