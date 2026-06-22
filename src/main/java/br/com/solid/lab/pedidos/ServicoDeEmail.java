package br.com.solid.lab.pedidos;

import java.util.ArrayList;
import java.util.List;

public class ServicoDeEmail implements NotificacaoEmail {

    private final List<String> emailsEnviados = new ArrayList<>();

    @Override
    public void enviarConfirmacao(Pedido pedido, double total){
        emailsEnviados.add("Para: " + pedido.cliente().email() + " | O total do seu pedido é " + String.format(java.util.Locale.US, "%.2f", total));
    }

    public List<String> emailsEnviados(){
        return emailsEnviados;
    }
}
