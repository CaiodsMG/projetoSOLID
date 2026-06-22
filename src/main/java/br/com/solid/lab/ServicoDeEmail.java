package br.com.solid.lab;

import java.util.ArrayList;
import java.util.List;

public class ServicoDeEmail {

    private final List<String> emailsEnviados = new ArrayList<>();

    void enviarConfirmacao(Pedido pedido, double total){
        emailsEnviados.add("Para: " + pedido.cliente().email() + " | O total do seu pedido é " + String.format(java.util.Locale.US, "%.2f", total));
    }

    public List<String> emailsEnviados(){
        return emailsEnviados;
    }
}
