package br.com.solid.lab;

public class CalculadorDeTotal {
    
    double calcular(Pedido pedido){
        double subtotal = 0;
        for (ItemPedido item: pedido.itens()){
            subtotal += item.subtotal();
        }

        double total = subtotal;

        if("BOASVINDAS10".equals(pedido.codigoCupom())){
            total = total * 0.90;
        }
        if(subtotal >= 200){
            total = total - 15;
        }

        return total;
    }
}
