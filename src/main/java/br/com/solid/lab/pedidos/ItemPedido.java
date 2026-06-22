package br.com.solid.lab.pedidos;

public record ItemPedido(String nomeProduto, int quantidade, double precoUnitario) {
    public double subtotal() {
        return quantidade * precoUnitario;
    }
}
